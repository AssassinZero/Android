package com.rd.hnlf.module.pure.dataModel.receive;

import com.google.gson.annotations.SerializedName;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/22 15:05
 * <p/>
 * Description:
 */
public class MyNoteRec {
    /** 票据ID */
    private String billId;
    /** 票号 */
    private String billNo;
    /** 票面金额 */
    @SerializedName(value = "billAmount", alternate = "faceAmount")
    private String billAmount;
    /** 票据类型 */
    private String billType;
    /** 票据类型描述 */
    @SerializedName(value = "billTypeText", alternate = "typeText")
    private String billTypeText;
    /** 票据属性 */
    private String billsAttribute;
    /** 票据属性描述 */
    private String billAttributeText;
    /** 到期日 */
    private String dueDate;
    /** 来源 */
    private String source;
    /** 来源描述 */
    private String sourceText;
    /** 票据状态 10 - 可转让，20 - 转让中 */
    private String transferState;
    /** 票据是否到期 10 - 到期，20 - 未到期 */
    private String overdue;

    public String getBillId() {
        return billId;
    }

    public String getBillNo() {
        return billNo;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public String getBillType() {
        return billType;
    }

    public String getBillTypeText() {
        return billTypeText;
    }

    public String getBillsAttribute() {
        return billsAttribute;
    }

    public String getBillAttributeText() {
        return billAttributeText;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getSource() {
        return source;
    }

    public String getSourceText() {
        return sourceText;
    }

    public String getTransferState() {
        return transferState;
    }

    public String getOverdue() {
        return overdue;
    }
}