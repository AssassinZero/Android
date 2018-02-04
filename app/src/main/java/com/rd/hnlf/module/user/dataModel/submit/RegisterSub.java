package com.rd.hnlf.module.user.dataModel.submit;

import com.rd.hnlf.common.BaseParams;
import com.rd.network.annotation.SerializedEncryption;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/25 15:55
 * <p/>
 * Description:
 */
public class RegisterSub {
    /** 手机号 */
    private String mobile;
    /** 密码 */
    @SerializedEncryption(type = BaseParams.ENCRYPTION_MODE)
    private String password;
    /** 手机验证码 */
    private String code;
    /**
     * 用户类型
     * 10 - 普通用户
     * 20 - 代理商
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

    public void setType(String type) {
        this.type = type;
    }
}