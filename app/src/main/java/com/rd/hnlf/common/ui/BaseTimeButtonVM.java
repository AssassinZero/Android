package com.rd.hnlf.common.ui;

import android.databinding.Bindable;

import com.rd.hnlf.BR;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 11:25
 * <p/>
 * Description:
 */
public abstract class BaseTimeButtonVM extends BaseButtonVM {
    /** 短信验证码 */
    private String  code;
    /** 获取验证码按钮是否可用 */
    private boolean codeEnable;

    /** TimeButton 是否可用的条件，一般是手机号是否不为空 */
    protected abstract boolean codeEnableCondition();

    @Bindable
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        inputCheck();
        notifyPropertyChanged(BR.code);
    }

    @Bindable
    public boolean isCodeEnable() {
        return codeEnable;
    }

    public void setCodeEnable(boolean codeEnable) {
        this.codeEnable = codeEnable;
        notifyPropertyChanged(BR.codeEnable);
    }

    /**
     * TimeButton是否可用
     */
    protected void codeEnableCheck() {
        if (codeEnableCondition()) {
            setCodeEnable(true);
        } else {
            setCodeEnable(false);
        }
    }

    /**
     * 倒计时后刷新页面
     */
    public void refresh() {
        inputCheck();
        codeEnableCheck();
    }
}