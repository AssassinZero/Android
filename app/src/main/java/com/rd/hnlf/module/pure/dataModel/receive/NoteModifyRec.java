package com.rd.hnlf.module.pure.dataModel.receive;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/13 18:55
 * <p/>
 * Description:
 */
public class NoteModifyRec {
    /** 票据票号 */
    private String billNo;
    /** 票面金额 */
    private String faceAmount;
    /** 调整天数 */
    private String adjustDays;
    /** 到期日 */
    private String dueDate;
    /** 票据类型 */
    private String typeText;
    /** 票据属性 */
    private String billAttributeText;
    /** 原结算总金额 */
    private String originalMoney;
    /** 是否显示面议 */
    private String isDiscussPersonally;
    /** 报价方式 10 - 年利率,20 - 每十万 */
    private String quotationMethod;
    /** 每十万 */
    private String discount;
    /** 年利率 */
    private String yearRate;
    /** 票据状态 10 - 可转让，20 - 转让中 */
    private String transactionState;

    public String getBillNo() {
        return billNo;
    }

    public String getFaceAmount() {
        return faceAmount;
    }

    public String getAdjustDays() {
        return adjustDays;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getTypeText() {
        return typeText;
    }

    public String getBillAttributeText() {
        return billAttributeText;
    }

    public String getOriginalMoney() {
        return originalMoney;
    }

    public String getIsDiscussPersonally() {
        return isDiscussPersonally;
    }

    public String getQuotationMethod() {
        return quotationMethod;
    }

    public String getDiscount() {
        return discount;
    }

    public String getYearRate() {
        return yearRate;
    }

    public String getTransactionState() {
        return transactionState;
    }
}