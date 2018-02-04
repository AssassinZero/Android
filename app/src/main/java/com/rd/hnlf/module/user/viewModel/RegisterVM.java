package com.rd.hnlf.module.user.viewModel;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.rd.hnlf.BR;
import com.rd.hnlf.common.ui.BaseTimeButtonVM;
import com.rd.hnlf.module.user.viewControl.RegisterCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/17 16:23
 * <p/>
 * Description: {@link RegisterCtrl}
 */
public class RegisterVM extends BaseTimeButtonVM {
    /** 用户角色 */
    private String role;
    /** 手机号码 */
    private String phone;
    /** 登录密码 */
    private String password;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        inputCheck();
        codeEnableCheck();
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        inputCheck();
        notifyPropertyChanged(BR.password);
    }

    @Override
    protected boolean codeEnableCondition() {
        return !TextUtils.isEmpty(phone);
    }

    @Override
    protected void inputCheck() {
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password) || TextUtils.isEmpty(getCode())) {
            setEnable(false);
        } else {
            setEnable(true);
        }
    }
}