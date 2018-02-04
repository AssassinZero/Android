package com.rd.hnlf.module.common.viewModel.bean;

import android.support.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/13 10:14
 * <p/>
 * Description:
 */
@Keep
public class KVPBean implements Serializable {
    /** 键 */
    @SerializedName(value = "code", alternate = {"id", "value", "enterpriseNameCount", "bankCode", "bankNo"})
    private String code;
    /** 值 */
    @SerializedName(value = "text", alternate = {"name", "enterpriseName", "bankName"})
    private String text;

    public KVPBean() {
    }

    public KVPBean(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}