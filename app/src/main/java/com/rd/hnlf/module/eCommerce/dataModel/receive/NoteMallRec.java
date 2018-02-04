package com.rd.hnlf.module.eCommerce.dataModel.receive;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/31 16:03
 * <p/>
 * Description:
 */
public class NoteMallRec {
    /** 票据票号 */
    private String billNo;
    /** 承兑人名称 */
    private String acceptorName;
    /** 调整天数 */
    private String adjustDays;
    /** 票据属性 */
    private String billAttribute;
    /** 每十万-报价 */
    private String discount;
    /** 票据到期日 */
    private String dueDate;
    /** 票面金额 */
    private String faceAmount;
    /** 报价方式 */
    private String quotationMethod;
    /** 转让状态 */
    private String transactionState;
    /** 票据类别 */
    private String type;
    /** 年利率 */
    private String yearRate;
    /** 是否显示面议 10 - 是，20 - 否 */
    private String isDiscussPersonally;

    public String getBillNo() {
        return billNo;
    }

    public String getAcceptorName() {
        return acceptorName;
    }

    public String getAdjustDays() {
        return adjustDays;
    }

    public String getBillAttribute() {
        return billAttribute;
    }

    public String getDiscount() {
        return discount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getFaceAmount() {
        return faceAmount;
    }

    public String getQuotationMethod() {
        return quotationMethod;
    }

    public String getTransactionState() {
        return transactionState;
    }

    public String getType() {
        return type;
    }

    public String getYearRate() {
        return yearRate;
    }

    public String getIsDiscussPersonally() {
        return isDiscussPersonally;
    }
}