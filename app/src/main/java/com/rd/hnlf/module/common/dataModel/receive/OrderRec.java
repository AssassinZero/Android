package com.rd.hnlf.module.common.dataModel.receive;

import com.google.gson.annotations.SerializedName;
import com.rd.hnlf.module.logic.ButtonOperateRec;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 10:24
 * <p/>
 * Description:
 */
public class OrderRec extends ButtonOperateRec {
    /** 票据属性 */
    private String  billsAttribute;
    /** 票据总数 */
    @SerializedName(value = "billsNum", alternate = "onlineOrederIdCount")
    private String  billsNum;
    /** 票面总金额 */
    @SerializedName(value = "billsTotalAmt", alternate = "billAmountCount")
    private String  billsTotalAmt;
    /** 票据类型 */
    private String  billsType;
    /** 交易状态 */
    private String  businessState;
    /** 交易状态中文显示 */
    @SerializedName(value = "businessStatus", alternate = "businessStateName")
    private String  businessStatus;
    /** 交易类型 */
    private String  businessType;
    /** 订单发起时间 */
    private String  createTime;
    /** 每十万贴现金额 */
    private String  discount;
    /** 收票方企业名称 */
    @SerializedName(value = "enterpriseName", alternate = "collectionAccount")
    private String  enterpriseName;
    /** 订单ID */
    @SerializedName(value = "orderId", alternate = "id")
    private String  orderId;
    /** 订单号 */
    private String  orderNo;
    /** 报价方式 10年利率 20每十万 */
    private String  quotationMethod;
    /** 结算总金额 */
    private String  settlementAmount;
    /** 年利率 */
    private String  yearRate;
    /** 是否需要复核 */
    private boolean isReview;

    public String getBillsAttribute() {
        return billsAttribute;
    }

    public String getBillsNum() {
        return billsNum;
    }

    public String getBillsTotalAmt() {
        return billsTotalAmt;
    }

    public String getBillsType() {
        return billsType;
    }

    public String getBusinessState() {
        return businessState;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public String getBusinessType() {
        return businessType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getDiscount() {
        return discount;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getQuotationMethod() {
        return quotationMethod;
    }

    public String getSettlementAmount() {
        return settlementAmount;
    }

    public String getYearRate() {
        return yearRate;
    }

    public boolean isReview() {
        return isReview;
    }
}