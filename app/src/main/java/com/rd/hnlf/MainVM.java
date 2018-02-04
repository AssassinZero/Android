package com.rd.hnlf;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.tools.utils.StringFormat;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/16 14:51
 * <p/>
 * Description: {@link MainCtrl}
 */
public class MainVM extends BaseObservable {
    /** 累计交易金额 */
    private String amount;
    /** 累计交易笔数 */
    private String times;

    @Bindable
    public String getAmount() {
        return StringFormat.doubleFormat(amount);
    }

    public void setAmount(String amount) {
        this.amount = amount;
        notifyPropertyChanged(BR.amount);
    }

    @Bindable
    public String getTimes() {
        return StringFormat.doubleFormat(times);
    }

    public void setTimes(String times) {
        this.times = times;
        notifyPropertyChanged(BR.times);
    }
}