package com.rd.hnlf.router.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;
import com.rd.hnlf.router.RouterUrl;

import java.lang.reflect.Type;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 16:56
 * <p/>
 * Description:
 */
@Route(path = RouterUrl.SERIALIZATION)
public class JsonServiceImpl implements SerializationService {
    @Override
    public void init(Context context) {

    }

    @Override
    public <T> T json2Object(String text, Class<T> clazz) {
        return new Gson().fromJson(text, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return new Gson().toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return new Gson().fromJson(input, clazz);
    }
}