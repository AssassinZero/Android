package com.rd.hnlf.module.eCommerce.dataModel.submit;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/14 17:38
 * <p/>
 * Description:
 */
public class NoteDetailRec {
    /** 承兑人名称 */
    private String acceptorName;
    /** 调整天数 */
    private String adjustDays;
    /** 票面金额 */
    private String billAmount;
    /** 票据属性 */
    private String billAttribute;
    /** 票号 */
    private String billNo;
    /** 每十万 */
    private String discount;
    /** 到期日 */
    private String dueDate;
    /** 持票方企业名称 */
    private String enterpriseName;
    /** 联系方式 */
    private String officePhone;
    /** 报价方式 */
    private String quotationMethod;
    /** 上架时间 */
    private String shelvesTime;
    /** 票据类型 */
    private String type;
    /** 年利率 */
    private String yearRate;
    /** 转让状态 10 - 可转让，20 - 转让中 */
    private String transactionState;
    /** 是否显示面议 10 - 是，20 - 否 */
    private String isDiscussPersonally;
    /** 是否回头背书  "回头背书" - 是，"" - 否 */
    private String backState;

    public String getAcceptorName() {
        return acceptorName;
    }

    public String getAdjustDays() {
        return adjustDays;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public String getBillAttribute() {
        return billAttribute;
    }

    public String getBillNo() {
        return billNo;
    }

    public String getDiscount() {
        return discount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public String getQuotationMethod() {
        return quotationMethod;
    }

    public String getShelvesTime() {
        return shelvesTime;
    }

    public String getType() {
        return type;
    }

    public String getYearRate() {
        return yearRate;
    }

    public String getTransactionState() {
        return transactionState;
    }

    public String getIsDiscussPersonally() {
        return isDiscussPersonally;
    }

    public String getBackState() {
        return backState;
    }
}