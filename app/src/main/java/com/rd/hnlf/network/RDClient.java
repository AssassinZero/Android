package com.rd.hnlf.network;

import com.rd.hnlf.common.BaseParams;
import com.rd.network.converter.RDConverterFactory;
import com.rd.network.interceptor.HttpLoggingInterceptor;
import com.rd.tools.log.Logger;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/5 10:30
 * <p/>
 * Description: 网络请求client
 */
public class RDClient {
    // 网络请求超时时间值(s)
    private static final int    DEFAULT_TIMEOUT = 30;
    // 请求地址
    private static final String BASE_URL        = BaseParams.URI;
    // retrofit实例
    private Retrofit retrofit;

    /**
     * 私有化构造方法
     */
    private RDClient() {
        // 创建一个OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // 设置网络请求超时时间
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                // 添加签名参数
                .addInterceptor(new BasicParamsInject().getInterceptor())
                // 打印参数
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
                // 失败后尝试重新请求
                // .retryOnConnectionFailure(true);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(builder.build())
                .addConverterFactory(RDConverterFactory.create())
                .build();
    }

    /**
     * 调用单例对象
     */
    private static RDClient getInstance() {
        return RDClientInstance.instance;
    }

    /**
     * 创建单例对象
     */
    private static class RDClientInstance {
        static RDClient instance = new RDClient();
    }

    ///////////////////////////////////////////////////////////////////////////
    // service
    ///////////////////////////////////////////////////////////////////////////
    private static TreeMap<String, Object> serviceMap;

    private static TreeMap<String, Object> getServiceMap() {
        if (serviceMap == null)
            serviceMap = new TreeMap<>();
        return serviceMap;
    }

    /**
     * @return 指定service实例
     */
    public static <T> T getService(Class<T> clazz) {
        if (getServiceMap().containsKey(clazz.getSimpleName())) {
            return (T) getServiceMap().get(clazz.getSimpleName());
        }

        Logger.w("need to create a new " + clazz.getSimpleName());
        T service = RDClient.getInstance().retrofit.create(clazz);
        getServiceMap().put(clazz.getSimpleName(), service);
        return service;
    }
}