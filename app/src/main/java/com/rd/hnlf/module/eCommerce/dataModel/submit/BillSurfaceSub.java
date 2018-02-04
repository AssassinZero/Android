package com.rd.hnlf.module.eCommerce.dataModel.submit;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/18 16:04
 * <p/>
 * Description:
 */
public class BillSurfaceSub {
    /** 调整天数 */
    private String adjustDays;
    /** 票据票号 */
    private String billNo;
    /** 应付金额 */
    private String originalMoney;

    public void setAdjustDays(String adjustDays) {
        this.adjustDays = adjustDays;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public void setOriginalMoney(String originalMoney) {
        this.originalMoney = originalMoney;
    }
}