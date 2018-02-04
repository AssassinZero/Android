package com.rd.hnlf.module.user.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.rd.hnlf.module.user.viewControl.SecurityCenterCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/18 18:10
 * <p/>
 * Description: {@link SecurityCenterCtrl}
 */
public class SecurityCenterVM extends BaseObservable {
    /** 手机号 */
    private String  phone;
    /** 我的银行卡账户是否可见 */
    private boolean bankAccountVisible;
    /** 银行卡张数 */
    private String  bankcardCount;

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public boolean getBankAccountVisible() {
        return bankAccountVisible;
    }

    public void setBankAccountVisible(boolean bankAccountVisible) {
        this.bankAccountVisible = bankAccountVisible;
        notifyPropertyChanged(BR.bankAccountVisible);
    }

    @Bindable
    public String getBankcardCount() {
        return bankcardCount;
    }

    public void setBankcardCount(String bankcardCount) {
        this.bankcardCount = bankcardCount;
        notifyPropertyChanged(BR.bankcardCount);
    }
}