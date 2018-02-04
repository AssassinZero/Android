package com.rd.hnlf.module.pure.viewModel.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.hnlf.BR;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/24 11:41
 * <p/>
 * Description: 出票人、收款人、承兑人信息
 */
public class TraderInfo extends BaseObservable {
    /** 全称 */
    private String fullName;
    /** 帐号 */
    private String accountName;
    /** 开户银行 */
    private String branchName;
    /** 开户行号 */
    private String branchNo;

    @Bindable
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        notifyPropertyChanged(BR.fullName);
    }

    @Bindable
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
        notifyPropertyChanged(BR.accountName);
    }

    @Bindable
    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
        notifyPropertyChanged(BR.branchName);
    }

    @Bindable
    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
        notifyPropertyChanged(BR.branchNo);
    }
}