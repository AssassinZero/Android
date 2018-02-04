package com.rd.hnlf.common.ui;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.hnlf.BR;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 11:37
 * <p/>
 * Description:
 */
public abstract class BaseButtonVM extends BaseObservable {
    /** 按钮是否可用 */
    private boolean enable;

    /** 输入校验 */
    protected abstract void inputCheck();

    @Bindable
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        notifyPropertyChanged(BR.enable);
    }
}