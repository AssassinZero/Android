package com.rd.hnlf.module.eCommerce.dataModel.submit;

import com.google.gson.Gson;
import com.rd.tools.utils.ConverterUtil;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/11 14:58
 * <p/>
 * Description:
 */
public class TradeNoteInfoSub {
    /** 账户余额 */
    private String             accountBalance;
    /** 持票方 - 银行账号 */
    private String             bankAccount;
    /** 票据属性 */
    private String             billsAttribute;
    /** 票据类别 */
    private String             billsType;
    /**
     * 交易类型
     * 0100 - 会员发起收票交易
     * 0101 - 会员发起卖票交易
     * 0102 - 普通用户发起卖票交易
     * 0200 - 代理商发起撮合交易
     */
    private String             businessType;
    /** 收票方 - 银行账号 */
    private String             collectingBankAccount;
    /** 收票方 - 账号 */
    private String             collectorAccountId;
    /** 每十万报价 */
    private String             discount;
    /** 持票方 - 账户 */
    private String             holdAccountId;
    /** 持票方 - 银行账户名称 */
    private String             holdAccountName;
    /** 持票方 - 总行行号 */
    private String             bankCode;
    /** 持票方 - 开户行号 */
    private String             holdAccountSubbranchCode;
    /** 持票方 - 开户行名称 */
    private String             holdAccountSubbranchName;
    /** 订单ID */
    private String             id;
    /** 票面信息 */
    private List<OrderBillSub> orderBills;
    /** 报价方式 10 年利率 20每十万 */
    private String             quotationMethod;
    /** 手续费 */
    private String             serviceFee;
    /** 总结算金额 */
    private String             settlementAmount;
    /** 收款账户是否与背书账户一致 10是 20否 */
    private String             uniformity;
    /** 年利率 */
    private String             yearRate;

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBillsAttribute() {
        return billsAttribute;
    }

    public void setBillsAttribute(String billsAttribute) {
        this.billsAttribute = billsAttribute;
    }

    public String getBillsType() {
        return billsType;
    }

    public void setBillsType(String billsType) {
        this.billsType = billsType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCollectingBankAccount() {
        return collectingBankAccount;
    }

    public void setCollectingBankAccount(String collectingBankAccount) {
        this.collectingBankAccount = collectingBankAccount;
    }

    public String getCollectorAccountId() {
        return collectorAccountId;
    }

    public void setCollectorAccountId(String collectorAccountId) {
        this.collectorAccountId = collectorAccountId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getHoldAccountId() {
        return holdAccountId;
    }

    public void setHoldAccountId(String holdAccountId) {
        this.holdAccountId = holdAccountId;
    }

    public String getHoldAccountName() {
        return holdAccountName;
    }

    public void setHoldAccountName(String holdAccountName) {
        this.holdAccountName = holdAccountName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getHoldAccountSubbranchCode() {
        return holdAccountSubbranchCode;
    }

    public void setHoldAccountSubbranchCode(String holdAccountSubbranchCode) {
        this.holdAccountSubbranchCode = holdAccountSubbranchCode;
    }

    public String getHoldAccountSubbranchName() {
        return holdAccountSubbranchName;
    }

    public void setHoldAccountSubbranchName(String holdAccountSubbranchName) {
        this.holdAccountSubbranchName = holdAccountSubbranchName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderBillSub> getOrderBills() {
        return orderBills;
    }

    public void setOrderBills(List<OrderBillSub> orderBills) {
        this.orderBills = orderBills;
    }

    public String getQuotationMethod() {
        return quotationMethod;
    }

    public void setQuotationMethod(String quotationMethod) {
        this.quotationMethod = quotationMethod;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getUniformity() {
        return uniformity;
    }

    public void setUniformity(String uniformity) {
        this.uniformity = uniformity;
    }

    public String getYearRate() {
        return ConverterUtil.getDouble(yearRate) * 100 + "";
    }

    public void setYearRate(String yearRate) {
        this.yearRate = ConverterUtil.getDouble(yearRate) / 100 + "";
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}