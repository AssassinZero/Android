package com.rd.hnlf.module.user.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.hnlf.BR;
import com.rd.hnlf.module.user.viewControl.AccountInfoVIPCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/6 11:09
 * <p/>
 * Description: {@link AccountInfoVIPCtrl}
 */
public class AccountInfoVIPVM extends BaseObservable {
    /** 企业名称 */
    private String  companyName;
    /** 证件号码 */
    private String  socialCreditCode;
    /** 企业地址 */
    private String  companyAddress;
    /** 办公电话 */
    private String  phone;
    /** 法人姓名 */
    private String  name;
    /** 法人身份证 */
    private String  IDCard;
    /** 是否可编辑 */
    private boolean editable;

    @Bindable
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
        notifyPropertyChanged(BR.companyName);
    }

    @Bindable
    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
        notifyPropertyChanged(BR.socialCreditCode);
    }

    @Bindable
    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
        notifyPropertyChanged(BR.companyAddress);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
        notifyPropertyChanged(BR.iDCard);
    }

    @Bindable
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyPropertyChanged(BR.editable);
    }
}