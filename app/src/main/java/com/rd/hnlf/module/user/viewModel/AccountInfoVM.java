package com.rd.hnlf.module.user.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.hnlf.BR;
import com.rd.hnlf.module.user.viewControl.AccountInfoCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/18 18:07
 * <p/>
 * Description: {@link AccountInfoCtrl}
 */
public class AccountInfoVM extends BaseObservable {
    /** 企业名称 */
    private String companyName;
    /** 企业地址 */
    private String companyAddress;
    /** 联系人 */
    private String contacts;
    /** 性别 */
    private String gender;
    /** 生日 */
    private String birthday;
    /** 邮箱 */
    private String email;

    @Bindable
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
        notifyPropertyChanged(BR.companyName);
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
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
        notifyPropertyChanged(BR.contacts);
    }

    @Bindable
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

    @Bindable
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
        notifyPropertyChanged(BR.birthday);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }
}