package com.rd.hnlf.module.pure.dataModel.receive;

import com.rd.hnlf.module.common.dataModel.receive.OrderBillRec;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/12 15:32
 * <p/>
 * Description:
 */
public class PureDetailRec {
    /** 订单ID */
    private String             id;
    /** 收票方账户名称 */
    private String             analogueAccountName;
    /** 收票方银行账户 */
    private String             analogueBankAccount;
    /** 收票方银行code */
    private String             analogueBankCode;
    /** 收票方开户行行号 */
    private String             analogueBankNumber;
    /** 收票方开户行名称 */
    private String             analogueOpeningBank;
    /** 交易状态 */
    private String             businessState;
    /** 每十万贴现金额 */
    private String             discount;
    /** 报价方式 10 - 年利率,20 - 每十万 */
    private String             quotationMethod;
    /** 手续费 */
    private String             serviceFee;
    /** 结算金额 */
    private String             settlementAmount;
    /** 年利率 */
    private String             yearRate;
    /** 票面信息列表 */
    private List<OrderBillRec> bills;
    /** 是否需要复核 */
    private boolean            isReview;

    public String getId() {
        return id;
    }

    public String getAnalogueAccountName() {
        return analogueAccountName;
    }

    public String getAnalogueBankAccount() {
        return analogueBankAccount;
    }

    public String getAnalogueBankCode() {
        return analogueBankCode;
    }

    public String getAnalogueBankNumber() {
        return analogueBankNumber;
    }

    public String getAnalogueOpeningBank() {
        return analogueOpeningBank;
    }

    public String getBusinessState() {
        return businessState;
    }

    public String getDiscount() {
        return discount;
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

    public List<OrderBillRec> getBills() {
        return bills;
    }

    public boolean isReview() {
        return isReview;
    }
}