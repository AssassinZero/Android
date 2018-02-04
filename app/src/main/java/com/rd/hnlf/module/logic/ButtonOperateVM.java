package com.rd.hnlf.module.logic;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.hnlf.BR;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/8 17:21
 * <p/>
 * Description:
 */
public class ButtonOperateVM extends BaseObservable {
    /** 按钮1 */
    private String button1;
    /** 按钮1操作码 */
    private String operate1;
    /** 按钮2 */
    private String button2;
    /** 按钮1操作码 */
    private String operate2;

    @Bindable
    public String getButton1() {
        return button1;
    }

    public void setButton1(String button1) {
        this.button1 = button1;
        notifyPropertyChanged(BR.button1);
    }

    public String getOperate1() {
        return operate1;
    }

    public void setOperate1(String operate1) {
        this.operate1 = operate1;
    }

    @Bindable
    public String getButton2() {
        return button2;
    }

    public void setButton2(String button2) {
        this.button2 = button2;
        notifyPropertyChanged(BR.button2);
    }

    public String getOperate2() {
        return operate2;
    }

    public void setOperate2(String operate2) {
        this.operate2 = operate2;
    }

    public void reset() {
        setButton1(null);
        setOperate1(null);
        setButton2(null);
        setOperate2(null);
    }
}