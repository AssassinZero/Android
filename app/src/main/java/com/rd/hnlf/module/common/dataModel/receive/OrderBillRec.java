package com.rd.hnlf.module.common.dataModel.receive;

import com.google.gson.annotations.SerializedName;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/8 14:30
 * <p/>
 * Description:
 */
public class OrderBillRec {
    /** 调整天数 */
    private String adjustDays;
    /** 票面金额 */
    @SerializedName(value = "billAmount", alternate = "faceAmount")
    private String billAmount;
    /** 票据票号 */
    private String billNo;
    /** 票据到期日 */
    @SerializedName(value = "dueDateStr", alternate = "dueDate")
    private String dueDateStr;
    /** 票ID */
    private String id;
    /** 匹配状态 */
    private String matchState;
    /** 关联订单ID */
    @SerializedName(value = "orderId", alternate = "nonRegulationOrderId")
    private String orderId;
    /** 结算金额 */
    private String singleSettleAmt;
    /** 票据属性 */
    private String billAttribute;
    /** 票据类别 10 - 银票,20 - 商票 */
    private String type;
    /** 年利率 */
    private String yearRate;
    /** 每十万 */
    private String discount;
    /** 应付金额 */
    @SerializedName(value = "originalMoney", alternate = "needPayAmount")
    private String originalMoney;

    public String getAdjustDays() {
        return adjustDays;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public String getBillNo() {
        return billNo;
    }

    public String getDueDateStr() {
        return dueDateStr;
    }

    public String getId() {
        return id;
    }

    public String getMatchState() {
        return matchState;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSingleSettleAmt() {
        return singleSettleAmt;
    }

    public String getBillAttribute() {
        return billAttribute;
    }

    public String getType() {
        return type;
    }

    public String getYearRate() {
        return yearRate;
    }

    public String getDiscount() {
        return discount;
    }

    public String getOriginalMoney() {
        return originalMoney;
    }
}