package com.rd.hnlf.module.user.viewModel;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.rd.hnlf.BR;
import com.rd.hnlf.common.ui.BaseTimeButtonVM;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.module.user.viewControl.ModifyPhoneCtrl;
import com.rd.logic.info.SharedInfo;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 10:22
 * <p/>
 * Description: {@link ModifyPhoneCtrl}
 */
public class ModifyPhoneVM extends BaseTimeButtonVM {
    /** 原手机号 */
    private String oldPhone;
    /** 登录密码 */
    private String password;
    /** 新手机号 */
    private String newPhone;

    public String getOldPhone() {
        return oldPhone;
    }

    public void setOldPhone(String oldPhone) {
        this.oldPhone = oldPhone;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bindable
    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
        codeEnableCheck();
        inputCheck();
        notifyPropertyChanged(BR.newPhone);
    }

    @Override
    protected boolean codeEnableCondition() {
        return !TextUtils.isEmpty(newPhone);
    }

    @Override
    protected void inputCheck() {
        if (TextUtils.isEmpty(newPhone) || TextUtils.isEmpty(password) || TextUtils.isEmpty(getCode())) {
            setEnable(false);
        } else {
            setEnable(true);
        }
    }
}