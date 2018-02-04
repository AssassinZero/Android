package com.rd.hnlf.module.trade.dataModel.receive;

import com.rd.hnlf.module.common.dataModel.receive.OrderBillRec;
import com.rd.hnlf.module.logic.ButtonOperateRec;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/8 14:24
 * <p/>
 * Description:
 */
public class TradeDetailRec extends ButtonOperateRec {
    /** 账户余额 */
    private String             accountBalance;
    /** 持票方银行账户 */
    private String             bankAccount;
    /** 收票方账户名称 */
    private String             bankName;
    /** 票据属性 */
    private String             billsAttribute;
    /** 票据属性释义 */
    private String             billsAttributeText;
    /** 票据类型 */
    private String             billsType;
    /** 票据类型释义 */
    private String             billsTypeText;
    /** 交易状态 */
    private String             businessState;
    /** 收票方银行账户 */
    private String             collectingBankAccount;
    /** 收票方账号 */
    private String             collectorAccountId;
    /** 收票方开户行号 */
    private String             collectorBankCode;
    /** 收票方银行银行账户开户名称 */
    private String             collectorOpenBankName;
    /** 订单发起时间 */
    private String             createTime;
    /** 每十万贴现金额 */
    private String             discount;
    /** 持票方账号 */
    private String             holdAccountId;
    /** 持票方账户名称 */
    private String             holdAccountName;
    /** 持票方银行账户开户行号 */
    private String             holdAccountSubbranchCode;
    /** 持票方银行账户开户行名称 */
    private String             holdAccountSubbranchName;
    /** 收款账户是否和背书账户一致 */
    private String             uniformity;
    /** 订单ID */
    private String             id;
    /** 订单完成时间 */
    private String             orderFinishTime;
    /** 订单号 */
    private String             orderNo;
    /** 报价方式 10年利率 20每十万 */
    private String             quotationMethod;
    /** 手续费 */
    private String             serviceFee;
    /** 结算金额 */
    private String             settlementAmount;
    /** 年利率 */
    private String             yearRate;
    /** 代理商帐号 */
    private String             agentAccount;
    /**
     * 交易类型
     * 0100 - 会员发起收票交易
     * 0101 - 会员发起卖票交易
     * 0102 - 普通用户发起卖票交易
     * 0200 - 代理商发起撮合交易
     * PS:
     * 0100、0101、0102 都是直接交易
     * 0200 是撮合交易
     */
    private String             businessType;
    /** 票面信息列表 */
    private List<OrderBillRec> orderBills;

    public String getAccountBalance() {
        return accountBalance;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBillsAttribute() {
        return billsAttribute;
    }

    public String getBillsAttributeText() {
        return billsAttributeText;
    }

    public String getBillsType() {
        return billsType;
    }

    public String getBillsTypeText() {
        return billsTypeText;
    }

    public String getBusinessState() {
        return businessState;
    }

    public String getCollectingBankAccount() {
        return collectingBankAccount;
    }

    public String getCollectorAccountId() {
        return collectorAccountId;
    }

    public String getCollectorBankCode() {
        return collectorBankCode;
    }

    public String getCollectorOpenBankName() {
        return collectorOpenBankName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getDiscount() {
        return discount;
    }

    public String getHoldAccountId() {
        return holdAccountId;
    }

    public String getHoldAccountName() {
        return holdAccountName;
    }

    public String getHoldAccountSubbranchCode() {
        return holdAccountSubbranchCode;
    }

    public String getHoldAccountSubbranchName() {
        return holdAccountSubbranchName;
    }

    public String getUniformity() {
        return uniformity;
    }

    public String getId() {
        return id;
    }

    public String getOrderFinishTime() {
        return orderFinishTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getQuotationMethod() {
        return quotationMethod;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public String getSettlementAmount() {
        return settlementAmount;
    }

    public String getYearRate() {
        return yearRate;
    }

    public String getAgentAccount() {
        return agentAccount;
    }

    public String getBusinessType() {
        return businessType;
    }

    public List<OrderBillRec> getOrderBills() {
        return orderBills;
    }

    /**
     * 是否是撮合交易
     */
    public boolean isMatch() {
        return "0200".equals(businessType);
    }
}