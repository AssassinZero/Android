package com.rd.hnlf.module.pure.viewModel.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.hnlf.BR;
import com.rd.tools.utils.DateUtil;
import com.rd.tools.utils.StringFormat;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/24 11:33
 * <p/>
 * Description: 基础信息
 */
public class BasicInfo extends BaseObservable {
    /** 票据票号 */
    private String id;
    /** 票面金额 */
    private String amount;
    /** 出票日期 */
    private String date;
    /** 汇票到期日 */
    private String dueDate;

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getAmount() {
        return StringFormat.doubleFormat(amount);
    }

    public void setAmount(String amount) {
        this.amount = amount;
        notifyPropertyChanged(BR.amount);
    }

    @Bindable
    public String getDate() {
        return DateUtil.formatter(DateUtil.Format.DATE, date);
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    @Bindable
    public String getDueDate() {
        return DateUtil.formatter(DateUtil.Format.DATE, dueDate);
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
        notifyPropertyChanged(BR.dueDate);
    }
}