package com.rd.hnlf.module.user.viewModel;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.rd.hnlf.BR;
import com.rd.hnlf.common.ui.BaseTimeButtonVM;
import com.rd.hnlf.module.user.viewControl.LoginCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/17 9:59
 * <p/>
 * Description: {@link LoginCtrl}
 */
public class LoginVM extends BaseTimeButtonVM {
    /** 手机号码 */
    private String  phone;
    /** 登录密码 */
    private String  password;
    /** false - 手机号、密码登录，true - 手机号、短信验证码登录 */
    private boolean mode;

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

    @Bindable
    public boolean getMode() {
        return mode;
    }

    public void setMode() {
        mode = !mode;
        notifyPropertyChanged(BR.mode);
    }

    @Override
    protected boolean codeEnableCondition() {
        return !TextUtils.isEmpty(phone);
    }

    @Override
    protected void inputCheck() {
        if (TextUtils.isEmpty(phone)) {
            setEnable(false);
        } else if (!mode && TextUtils.isEmpty(password)) {
            setEnable(false);
        } else if (mode && TextUtils.isEmpty(getCode())) {
            setEnable(false);
        } else {
            setEnable(true);
        }
    }
}