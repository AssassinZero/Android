package com.rd.network.interceptor;

import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/6 14:23
 * <p/>
 * Description: 公共参数拦截器
 */
public class BasicParamsInterceptor implements Interceptor {
    /** header 参数Map */
    private Map<String, String> headerParamsMap = new HashMap<>();
    /** query 参数Map */
    private Map<String, String> queryParamsMap  = new HashMap<>();
    /** post 参数Map */
    private Map<String, String> postParamsMap   = new HashMap<>();
    /** 动态参数接口 */
    private IBasicDynamic iBasicDynamic;

    private BasicParamsInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request         request        = chain.request();
        RequestBody     requestBody    = request.body();
        Request.Builder requestBuilder = request.newBuilder();

        // inject query params into url whatever it's GET or POST Request
        if (queryParamsMap.size() > 0) {
            injectParamsIntoUrl(request, requestBuilder, queryParamsMap);
        }

        // inject post params into body when it's POST Request
        if (request.method().equals("POST")) {
            // MultipartBody date
            if (null != requestBody && requestBody instanceof MultipartBody) {
                MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
                multipartBodyBuilder.setType(MultipartBody.FORM);
                // add new static params to new multipartBodyBuilder
                for (Map.Entry entry : postParamsMap.entrySet()) {
                    multipartBodyBuilder.addFormDataPart((String) entry.getKey(), getValueFromEntry(entry)).setType(MultipartBody.FORM);
                }
                // add new dynamic params to new multipartBodyBuilder
                if (null != iBasicDynamic) {
                    for (Map.Entry entry : iBasicDynamic.addParams().entrySet()) {
                        multipartBodyBuilder.addFormDataPart((String) entry.getKey(), getValueFromEntry(entry)).setType(MultipartBody.FORM);
                    }
                }
                // add old parts to new multipartBodyBuilder
                for (MultipartBody.Part part : ((MultipartBody) requestBody).parts()) {
                    multipartBodyBuilder.addPart(part);
                }
                requestBuilder.post(multipartBodyBuilder.build());
            } else {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                // add new static params to new formBodyBuilder
                for (Map.Entry entry : postParamsMap.entrySet()) {
                    formBodyBuilder.add((String) entry.getKey(), getValueFromEntry(entry));
                }
                // add new dynamic params to new formBodyBuilder
                if (null != iBasicDynamic) {
                    for (Map.Entry entry : iBasicDynamic.addParams().entrySet()) {
                        formBodyBuilder.add((String) entry.getKey(), getValueFromEntry(entry));
                    }
                }
                // add old params to new formBodyBuilder
                FormBody formBody       = formBodyBuilder.build();
                String   postBodyString = bodyToString(requestBody);
                postBodyString += (TextUtils.isEmpty(postBodyString) ? "" : "&") + bodyToString(formBody);

                // set MediaType
                MediaType mediaType = null;

                String contentType = headerParamsMap.get("Content-Type");
                if (!TextUtils.isEmpty(contentType)) {
                    mediaType = MediaType.parse(contentType);
                }

                if (null != requestBody && null != requestBody.contentType()) {
                    mediaType = requestBody.contentType();
                }

                if (null == mediaType) {
                    mediaType = formBody.contentType();
                }

                requestBuilder.post(RequestBody.create(mediaType, postBodyString));
            }
        } else if (postParamsMap.size() > 0) {
            // if can't inject into body, then inject into url
            injectParamsIntoUrl(request, requestBuilder, postParamsMap);
        }

        // inject header params into header
        Map<String, String> signParamsMap = new TreeMap<>();
        if (request.method().equals("POST")) {
            if (null != requestBody && !(requestBody instanceof MultipartBody)) {
                signParamsMap = iBasicDynamic.signParams(stringToMap(bodyToString(requestBody)));
            }
        } else {
            signParamsMap = iBasicDynamic.signParams(stringToMap(getQueryFromUrl(request.url().toString())));
        }

        Headers.Builder headerBuilder = request.headers().newBuilder();
        // add new params to new headerBuilder
        if (signParamsMap.size() > 0) {
            for (Map.Entry entry : signParamsMap.entrySet()) {
                headerBuilder.add((String) entry.getKey(), getValueFromEntry(entry));
            }
        }
        // add old params to new headerBuilder
        if (headerParamsMap.size() > 0) {
            for (Map.Entry entry : headerParamsMap.entrySet()) {
                headerBuilder.add((String) entry.getKey(), getValueFromEntry(entry));
            }
        }

        requestBuilder.headers(headerBuilder.build());
        request = requestBuilder.build();

        Response originalResponse = chain.proceed(request);
        Response priorResponse    = originalResponse.priorResponse();
        // 如果是重定向，那么就执行重定向请求后再返回数据。
        if (null != priorResponse && priorResponse.isRedirect()) {
            Request redirectRequest = request.newBuilder().url(originalResponse.request().url()).build();
            originalResponse = chain.proceed(redirectRequest);
        }
        return originalResponse;
    }

    // function to inject query params into url whatever it's GET or POST
    private void injectParamsIntoUrl(Request request, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        for (Map.Entry entry : paramsMap.entrySet()) {
            httpUrlBuilder.addQueryParameter((String) entry.getKey(), getValueFromEntry(entry));
        }
        requestBuilder.url(httpUrlBuilder.build());
    }

    // get value from entry
    private String getValueFromEntry(Map.Entry entry) {
        String result = (String) entry.getValue();
        // try {
        //     result = getValueFromEntry.encode(result, "UTF-8");
        // } catch (UnsupportedEncodingException e) {
        //     e.printStackTrace();
        // }
        return result;
    }

    // RequestBody to String
    private String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            if (request != null) {
                request.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (IOException e) {
            return "did not work";
        }
    }

    // get query params from url
    private String getQueryFromUrl(String urlStr) {
        String[] paramStr = urlStr.split("\\?");
        if (paramStr.length > 1) {
            return paramStr[1];
        } else {
            return "";
        }
    }

    // converts the & connection string to map
    private Map<String, String> stringToMap(String paramsStr) {
        Map<String, String> signMap = new HashMap<>();
        if (!TextUtils.isEmpty(paramsStr)) {
            String[]            params = paramsStr.split("&");
            Map<String, String> temp;
            for (String str : params) {
                temp = new HashMap<>();
                String[] s = str.split("=");
                if (s.length > 1) {
                    temp.put(s[0], s[1]);
                } else {
                    temp.put(s[0], "");
                }
                signMap.putAll(temp);
            }
        }
        return signMap;
    }

    public void setIBasicDynamic(IBasicDynamic iBasicDynamic) {
        this.iBasicDynamic = iBasicDynamic;
    }

    public static class Builder {
        BasicParamsInterceptor interceptor;

        public Builder() {
            interceptor = new BasicParamsInterceptor();
        }

        public Builder addHeaderParam(String key, String value) {
            interceptor.headerParamsMap.put(key, value);
            return this;
        }

        public Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {
            interceptor.headerParamsMap.putAll(headerParamsMap);
            return this;
        }

        public Builder addQueryParam(String key, String value) {
            interceptor.queryParamsMap.put(key, value);
            return this;
        }

        public Builder addQueryParamsMap(Map<String, String> queryParamsMap) {
            interceptor.queryParamsMap.putAll(queryParamsMap);
            return this;
        }

        public Builder addPostParam(String key, String value) {
            interceptor.postParamsMap.put(key, value);
            return this;
        }

        public Builder addPostParamsMap(Map<String, String> paramsMap) {
            interceptor.postParamsMap.putAll(paramsMap);
            return this;
        }

        public BasicParamsInterceptor build() {
            return interceptor;
        }
    }
}