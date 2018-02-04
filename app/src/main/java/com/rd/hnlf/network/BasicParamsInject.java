package com.rd.hnlf.network;

import com.rd.hnlf.common.BaseParams;
import com.rd.hnlf.common.Constant;
import com.rd.network.interceptor.BasicParamsInterceptor;
import com.rd.network.interceptor.IBasicDynamic;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.DeviceInfoUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/5 17:59
 * <p/>
 * Description: 拦截器 - 用于添加签名参数
 */
class BasicParamsInject {
    private BasicParamsInterceptor interceptor;

    BasicParamsInject() {
        // 设置静态参数
        interceptor = new BasicParamsInterceptor.Builder()
                .addHeaderParam("User-Agent", "android")
                .addPostParam(Constant.APP_KEY, BaseParams.APP_KEY)
                .addPostParam(Constant.MOBILE_TYPE, BaseParams.MOBILE_TYPE)
                .addPostParam(Constant.VERSION_NUMBER, DeviceInfoUtil.getVersionName(ContextHolder.getContext()))
                .build();
        // 设置动态参数
        interceptor.setIBasicDynamic(new IBasicDynamic() {
            @Override
            public Map<String, String> addParams() {
                // 往 POST BODY 中添加动态参数
                return SignUtil.getInstance().addParams();
            }

            @Override
            public Map<String, String> signParams(Map<String, String> paramsMap) {
                // 往 header 中添加动态参数（可以做全参数验签）
                // return SignUtil.getInstance().signParams(paramsMap);
                return new HashMap<>();
            }
        });
    }

    Interceptor getInterceptor() {
        return interceptor;
    }
}