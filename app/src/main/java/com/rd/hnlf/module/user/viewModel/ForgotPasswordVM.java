package com.rd.hnlf.module.user.viewModel;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.android.databinding.library.baseAdapters.BR;
import com.rd.hnlf.common.ui.BaseTimeButtonVM;
import com.rd.hnlf.module.user.viewControl.ForgotPasswordCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/25 14:09
 * <p/>
 * Description: {@link ForgotPasswordCtrl}
 */
public class ForgotPasswordVM extends BaseTimeButtonVM {
    /** 手机号 */
    private String phone;

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

    @Override
    protected void inputCheck() {
        if (TextUtils.isEmpty(phone)) {
            setEnable(false);
        } else {
            setEnable(true);
        }
    }

    @Override
    protected boolean codeEnableCondition() {
        return !TextUtils.isEmpty(phone);
    }
}
