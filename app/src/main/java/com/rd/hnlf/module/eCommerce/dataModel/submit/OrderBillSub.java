package com.rd.hnlf.module.eCommerce.dataModel.submit;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/11 15:03
 * <p/>
 * Description:
 */
public class OrderBillSub {
    /** 调整天数 */
    private String adjustDays;
    /** 票面金额 */
    private String billAmount;
    /** 票号 */
    private String billNo;
    /** 票据到期日 */
    private String dueDateStr;
    /** 结算金额 */
    private String singleSettleAmt;

    public void setAdjustDays(String adjustDays) {
        this.adjustDays = adjustDays;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public void setDueDateStr(String dueDateStr) {
        this.dueDateStr = dueDateStr;
    }

    public void setSingleSettleAmt(String singleSettleAmt) {
        this.singleSettleAmt = singleSettleAmt;
    }
}