package com.rd.hnlf.module.user.dataModel.submit;

import com.rd.network.annotation.EncryptionType;
import com.rd.network.annotation.SerializedEncryption;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/26 15:18
 * <p/>
 * Description:
 */
public class LoginSub {
    /** 手机号 */
    private String mobile;
    /** 密码 */
    @SerializedEncryption(type = EncryptionType.MD5_2)
    private String password;
    /** 短信验证码 */
    private String code;
    /**
     * 登录方式
     * 10 - 账号密码登录
     * 20 - 短信验证码登录
     */
    private String type;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * false - 账号密码登录
     * true - 短信验证码登录
     */
    public void setType(boolean type) {
        if (type) {
            this.type = "20";
        } else {
            this.type = "10";
        }
    }
}