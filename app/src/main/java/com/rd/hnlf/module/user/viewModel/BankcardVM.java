package com.rd.hnlf.module.user.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.module.user.viewControl.BankcardEditCtrl;
import com.rd.hnlf.module.user.viewControl.BankcardListCtrl;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.StringFormat;

import java.io.Serializable;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 15:26
 * <p/>
 * Description: {@link BankcardListCtrl}{@link BankcardEditCtrl}
 */
public class BankcardVM extends BaseObservable implements Serializable, MultiItemEntity {
    /** 银行卡ID */
    private String id;
    /**
     * 账户类型:
     * 10 - 普通账户
     * 20 - 签约账户
     */
    private String accountType;
    /** 银行帐号 */
    private String bankcard;
    /** 收款账户名称 */
    private String accountName;
    /** 开户银行 */
    private String bankName;
    /** 开户银行代号 */
    private String bankNo;
    /** 开户行名称 */
    private String branchName;
    /** 开户行行号 */
    private String branchNo;
    /** 银行 ICON */
    private String icon;
    /** 布局type */
    private int    itemType;

    public BankcardVM() {
        accountType = "10";
    }

    public BankcardVM(int itemType) {
        this.itemType = itemType;
        accountType = "10";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
        notifyPropertyChanged(BR.accountType);
    }

    @Bindable
    public String getBankcard() {
        return bankcard;
    }

    public String getBankcardShort() {
        if (TextUtils.isEmpty(bankcard)) {
            return "";
        } else {
            return bankcard.substring(bankcard.length() - 3, bankcard.length());
        }
    }

    @Bindable
    public String getBankcardHidden() {
        return StringFormat.bankcardHideFormat(bankcard);
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
        notifyPropertyChanged(BR.bankcard);
        notifyPropertyChanged(BR.bankcardHidden);
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
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
        notifyPropertyChanged(BR.bankName);
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
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

    @Bindable
    public String getBranchNoStr() {
        return ContextHolder.getContext().getString(R.string.bankcard_list_branch_no, branchNo);
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
        notifyPropertyChanged(BR.branchNo);
        notifyPropertyChanged(BR.branchNoStr);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    /**
     * true - 普通账户
     * false - 签约账户
     */
    public boolean isNormal() {
        return "10".equals(accountType);
    }

    @Override
    public String toString() {
        return bankcard;
    }
}