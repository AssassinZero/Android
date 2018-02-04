package com.rd.hnlf.module.pure.dataModel.submit;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/20 19:03
 * <p/>
 * Description:
 */
public class NoteSub {
    /** 当前票号 */
    private String billNo;
    /** 票面金额 */
    private String faceAmount;
    /** 调整天数 */
    private String adjustDays;
    /** 到期日 */
    private String dueDate;

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public void setFaceAmount(String faceAmount) {
        this.faceAmount = faceAmount;
    }

    public void setAdjustDays(String adjustDays) {
        this.adjustDays = adjustDays;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}