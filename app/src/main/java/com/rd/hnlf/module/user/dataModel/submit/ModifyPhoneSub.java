package com.rd.hnlf.module.user.dataModel.submit;

import com.rd.hnlf.common.BaseParams;
import com.rd.network.annotation.SerializedEncryption;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/25 11:25
 * <p/>
 * Description:
 */
public class ModifyPhoneSub {
    /** 原手机号 */
    private String mobile;
    /** 登录密码 */
    @SerializedEncryption(type = BaseParams.ENCRYPTION_MODE)
    private String password;
    /** 新手机号 */
    private String newMobile;
    /** 短信验证码 */
    private String code;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile;
    }

    public void setCode(String code) {
        this.code = code;
    }
}