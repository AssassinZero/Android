package com.rd.hnlf.module.pure.dataModel.submit;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/21 16:40
 * <p/>
 * Description:
 */
public class PureTransactionSub {
    /** 票据信息 */
    private List<NoteSub> bills;
    /** 报价方式 10年利率 20每十万 */
    private String        quotationMethod;
    /** 年利率（%）（报价方式为10时传年利率 非必填） */
    private String        yearRate;
    /** 每十万贴现（元）（报价方式为20时传值 非必填） */
    private String        discount;
    /** 手续费（元） */
    private String        serviceFee;
    /** 收票方 - 账户名称 */
    private String        analogueAccountName;
    /** 收票方 - 银行账户 */
    private String        analogueBankAccount;
    /** 收票方 - 银行code */
    private String        analogueBankCode;
    /** 收票方 - 开户行名称 */
    private String        analogueOpeningBank;
    /** 收票方 - 开户行行号 */
    private String        analogueBankNumber;

    public void setBills(List<NoteSub> bills) {
        this.bills = bills;
    }

    public void setQuotationMethod(String quotationMethod) {
        this.quotationMethod = quotationMethod;
    }

    public void setYearRate(String yearRate) {
        this.yearRate = yearRate;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public void setAnalogueAccountName(String analogueAccountName) {
        this.analogueAccountName = analogueAccountName;
    }

    public void setAnalogueBankAccount(String analogueBankAccount) {
        this.analogueBankAccount = analogueBankAccount;
    }

    public void setAnalogueBankCode(String analogueBankCode) {
        this.analogueBankCode = analogueBankCode;
    }

    public void setAnalogueOpeningBank(String analogueOpeningBank) {
        this.analogueOpeningBank = analogueOpeningBank;
    }

    public void setAnalogueBankNumber(String analogueBankNumber) {
        this.analogueBankNumber = analogueBankNumber;
    }
}