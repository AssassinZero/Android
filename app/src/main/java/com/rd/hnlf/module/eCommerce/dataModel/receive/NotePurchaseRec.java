package com.rd.hnlf.module.eCommerce.dataModel.receive;

import com.rd.hnlf.module.common.dataModel.receive.OrderBillRec;
import com.rd.hnlf.module.user.dataModel.receive.BankcardRec;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/15 14:15
 * <p/>
 * Description:
 */
public class NotePurchaseRec {
    /** 票据信息 */
    private List<OrderBillRec> orderBills;
    /** 被背书信息 - 企业名称 */
    private String             enterpriseNameOwn;
    /** 被背书账户银行卡列表 */
    private List<BankcardRec>  endorseeInfo;
    /** 支付信息 - 企业名称 */
    private String             enterpriseName;
    /** 支付信息 - 银行卡号 */
    private String             bankAccount;
    /** 支付信息 - 账户名称 */
    private String             accountName;
    /** 支付信息 - 支行名称 */
    private String             openingBank;
    /** 支付信息 - 开户行行号 */
    private String             bankNumber;
    /** 支付信息 - 开户行行号 */
    private String            payMobile;
    /** 结算金额 */
    private String             parseDouble;

    public List<OrderBillRec> getOrderBills() {
        return orderBills;
    }

    public String getEnterpriseNameOwn() {
        return enterpriseNameOwn;
    }

    public List<BankcardRec> getEndorseeInfo() {
        return endorseeInfo;
    }

    public String getPayMobile() {
        return payMobile;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public String getParseDouble() {
        return parseDouble;
    }
}