package com.rd.hnlf.module.eCommerce.dataModel.submit;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/18 16:00
 * <p/>
 * Description:
 */
public class NotePurchaseSub {
    /** 被背书 - 银行帐号 */
    private String               bankAccount;
    /** 被背书 - 账户名称 */
    @SerializedName("bccountNameOwn")
    private String               accountNameOwn;
    /** 被背书 - 总行行号 */
    private String               bankCode;
    /** 被背书 - 开户行名称 */
    private String               openingBankOwn;
    /** 被背书 - 开户行行号 */
    private String               bankNumberOwn;
    /** 票面信息 */
    private List<BillSurfaceSub> billSurfaceList;
    /** 手机号 */
    private String               mobile;
    /** 手机号 */
    private String               payMobile;
    /** 手机号 */
    private String               payBankAccount;
    /** 手续费 */
    private String               serviceCharge;
    /** 结算金额 */
    private String               settlementAmount;

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setAccountNameOwn(String accountNameOwn) {
        this.accountNameOwn = accountNameOwn;
    }

    public String getAccountNameOwn() {
        return accountNameOwn;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setOpeningBankOwn(String openingBankOwn) {
        this.openingBankOwn = openingBankOwn;
    }

    public String getOpeningBankOwn() {
        return openingBankOwn;
    }

    public void setBankNumberOwn(String bankNumberOwn) {
        this.bankNumberOwn = bankNumberOwn;
    }

    public String getBankNumberOwn() {
        return bankNumberOwn;
    }

    public void setBillSurfaceList(List<BillSurfaceSub> billSurfaceList) {
        this.billSurfaceList = billSurfaceList;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public void setPayMobile(String mobile) {
        this.payMobile = mobile;
    }
    public void setPayBankAccount(String mobile) {
        this.payBankAccount = mobile;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}