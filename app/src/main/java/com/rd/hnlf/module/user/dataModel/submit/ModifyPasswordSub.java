package com.rd.hnlf.module.user.dataModel.submit;

import com.rd.hnlf.common.BaseParams;
import com.rd.network.annotation.SerializedEncryption;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/28 18:15
 * <p/>
 * Description:
 */
public class ModifyPasswordSub {
    /** 手机号 */
    private String mobile;
    /** 验证码 */
    private String code;
    /** 密码 */
    @SerializedEncryption(type = BaseParams.ENCRYPTION_MODE)
    private String password;
    /** 新密码 */
    @SerializedEncryption(type = BaseParams.ENCRYPTION_MODE)
    private String newPassword;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}