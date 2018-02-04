package com.rd.hnlf.module.common.viewModel.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.ConverterUtil;
import com.rd.tools.utils.StringFormat;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 17:47
 * <p/>
 * Description: 报价信息
 */
public class QuotationInfo extends BaseObservable {
    /** 票据类型 */
    private String type;
    /** 票据属性 */
    private String property;
    /** 每十万贴现（元） */
    private String discount;
    /** 年利率 */
    private String apr;
    /** 手续费（元） */
    private String fee;
    /** 是否面议 - 选项 */
    private String discussPersonally;
    /** 报价方式 - 选项 */
    private String quotationMethod;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDiscount() {
        return TextUtils.isEmpty(discount) ? discount : StringFormat.doubleMoney(discount);
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getApr() {
        return TextUtils.isEmpty(apr) ? apr : ContextHolder.getContext()
                .getString(R.string.percent, StringFormat.doubleFormat(ConverterUtil.getDouble(apr) * 100));
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getFee() {
        return TextUtils.isEmpty(fee) ? ContextHolder.getContext().getString(R.string.not_available) : StringFormat.doubleMoney(fee);
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getOriginApr() {
        return apr;
    }

    public void setOriginApr(String apr) {
        this.apr = apr;
    }

    public String getOriginDiscount() {
        return discount;
    }

    public void setOriginDiscount(String discount) {
        this.discount = discount;
    }

    public String getOriginFee() {
        return fee;
    }

    public void setOriginFee(String fee) {
        this.fee = fee;
    }

    @Bindable
    public String getDiscussPersonally() {
        return discussPersonally;
    }

    public void setDiscussPersonally(String discussPersonally) {
        this.discussPersonally = discussPersonally;
        notifyPropertyChanged(BR.discussPersonally);
        notifyPropertyChanged(BR.discuss);
    }

    @Bindable
    public String getQuotationMethod() {
        return quotationMethod;
    }

    public void setQuotationMethod(String quotationMethod) {
        this.quotationMethod = quotationMethod;
        notifyPropertyChanged(BR.quotationMethod);
        notifyPropertyChanged(BR.mode);
        notifyPropertyChanged(BR.aprMode);
        notifyPropertyChanged(BR.discountMode);
    }

    @Bindable
    public String getMode() {
        if ("10".equals(quotationMethod)) {
            return ContextHolder.getContext().getString(R.string.note_detail_apr);
        } else {
            return ContextHolder.getContext().getString(R.string.note_detail_discount);
        }
    }

    @Bindable
    public boolean isAprMode() {
        return "10".equals(quotationMethod);
    }

    @Bindable
    public boolean isDiscountMode() {
        return "20".equals(quotationMethod);
    }

    @Bindable
    public boolean isDiscuss() {
        return "10".equals(discussPersonally);
    }
}