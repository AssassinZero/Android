package com.rd.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/5 15:23
 * <p/>
 * Description: 网络返回消息，最外层解析实体
 */
public class HttpResult<T> {
    /** 错误码 */
    @SerializedName(Params.RES_CODE)
    private int    code;
    /** 错误信息 */
    @SerializedName(Params.RES_MSG)
    private String msg;
    /** 消息响应的主体 */
    @SerializedName(Params.RES_DATA)
    private T      data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}