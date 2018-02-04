package com.rd.hnlf.module.eCommerce.dataModel.receive;

import com.rd.hnlf.module.common.dataModel.receive.OrderBillRec;
import com.rd.hnlf.module.logic.ButtonOperateRec;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/19 11:50
 * <p/>
 * Description:
 */
public class ECommerceDetailRec extends ButtonOperateRec {
    /** 票面信息列表 */
    private List<OrderBillRec> bizOnlineOrderBills;
    /** 被背书 - 企业名称 */
    private String             enterpriseNameEndorsed;
    /** 被背书 - 银行账户 */
    private String             bankAccountEndorsed;
    /** 被背书 - 账户名称 */
    private String             accountNameEndorsed;
    /** 被背书 - 开户行名称 */
    private String             subbarnchBankNameEndorsed;
    /** 被背书 - 开户行行号 */
    private String             subbranchBankCodeEndorsed;
    /** 支付信息 - 企业名称 */
    private String             enterpriseNameTicket;
    /** 支付信息 - 银行账户 */
    private String             bankAccountTicket;
    /** 支付信息 - 账户名称 */
    private String             accountNameTicket;
    /** 支付信息 - 开户行名称 */
    private String             subbarnchBankNameTicket;
    /** 支付信息 - 开户行行号 */
    private String             subbranchBankCodeTicket;
    /** 支付信息 - 手续费（元） */
    private String             serviceFee;
    /** 订单号 */
    private String             orderNo;
    /** 订单号 */
    private String             mobile;
    /** 下单时间 */
    private String             createTime;
    /** 订单完成时间 */
    private String             orderFinishTime;
    /** 订单状态 */
    private String             businessStateName;
    /** 结算金额 */
    private String             settlementAmount;

    public List<OrderBillRec> getBizOnlineOrderBills() {
        return bizOnlineOrderBills;
    }

    public String getEnterpriseNameEndorsed() {
        return enterpriseNameEndorsed;
    }

    public String getMobile() {
        return mobile;
    }

    public String getBankAccountEndorsed() {
        return bankAccountEndorsed;
    }

    public String getAccountNameEndorsed() {
        return accountNameEndorsed;
    }

    public String getSubbarnchBankNameEndorsed() {
        return subbarnchBankNameEndorsed;
    }

    public String getSubbranchBankCodeEndorsed() {
        return subbranchBankCodeEndorsed;
    }

    public String getEnterpriseNameTicket() {
        return enterpriseNameTicket;
    }

    public String getBankAccountTicket() {
        return bankAccountTicket;
    }

    public String getAccountNameTicket() {
        return accountNameTicket;
    }

    public String getSubbarnchBankNameTicket() {
        return subbarnchBankNameTicket;
    }

    public String getSubbranchBankCodeTicket() {
        return subbranchBankCodeTicket;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getOrderFinishTime() {
        return orderFinishTime;
    }

    public String getBusinessStateName() {
        return businessStateName;
    }

    public String getSettlementAmount() {
        return settlementAmount;
    }
}