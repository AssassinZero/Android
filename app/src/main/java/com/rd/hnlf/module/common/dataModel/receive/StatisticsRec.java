package com.rd.hnlf.module.common.dataModel.receive;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/11/7 10:41
 * <p/>
 * Description:
 */
public class StatisticsRec {
    /** 累计交易金额 */
    private String totalAmount;
    /** 累计交易笔数 */
    private String totalCount;

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getTotalCount() {
        return totalCount;
    }
}