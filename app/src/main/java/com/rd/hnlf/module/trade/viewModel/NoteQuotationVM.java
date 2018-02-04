package com.rd.hnlf.module.trade.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.hnlf.BR;
import com.rd.hnlf.module.common.viewModel.bean.KVPBean;
import com.rd.hnlf.module.trade.viewControl.NoteQuotationCtrl;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/31 9:56
 * <p/>
 * Description: {@link NoteQuotationCtrl}
 */
public class NoteQuotationVM extends BaseObservable {
    /** 票据类型 */
    private List<KVPBean> typeList;
    /** 票据属性 */
    private List<KVPBean> propertyList;
    /** 每十万贴现（元） */
    private String        discount;
    /** 年利率 */
    private String        apr;
    /** 手续费（元） */
    private String        fee;
    /** 票据类型 */
    private String        type;
    /** 票据属性 */
    private String        property;
    /** 报价方式 */
    private String        quotationMethod;

    @Bindable
    public List<KVPBean> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<KVPBean> typeList) {
        this.typeList = typeList;
        notifyPropertyChanged(BR.typeList);
    }

    @Bindable
    public List<KVPBean> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<KVPBean> propertyList) {
        this.propertyList = propertyList;
        notifyPropertyChanged(BR.propertyList);
    }

    @Bindable
    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
        notifyPropertyChanged(BR.discount);
    }

    @Bindable
    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
        notifyPropertyChanged(BR.apr);
    }

    @Bindable
    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
        notifyPropertyChanged(BR.fee);
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
        notifyPropertyChanged(BR.property);
    }

    @Bindable
    public String getQuotationMethod() {
        return quotationMethod;
    }

    public void setQuotationMethod(String quotationMethod) {
        this.quotationMethod = quotationMethod;
        notifyPropertyChanged(BR.quotationMethod);
        notifyPropertyChanged(BR.aprMode);
        notifyPropertyChanged(BR.discountMode);
    }

    @Bindable
    public boolean isAprMode() {
        return "10".equals(quotationMethod);
    }

    @Bindable
    public boolean isDiscountMode() {
        return "20".equals(quotationMethod);
    }
}