package com.rd.hnlf.module.pure.dataModel.submit;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/14 10:48
 * <p/>
 * Description:
 */
public class MyNoteSub extends NoteSub {
    /** 是否显示面议 10 - 是，20 - 否 */
    private String        isDiscussPersonally;
    /** 报价方式  10 - 年利率，20 - 每十万 */
    private String        quotationMethod;
    /** 年利率 */
    private String        yearRate;
    /** 每十万 */
    private String        discount;
    /** 原结算总金额 */
    private String        originalMoney;
    private List<NoteSub> billNos;

    public void setIsDiscussPersonally(String isDiscussPersonally) {
        this.isDiscussPersonally = isDiscussPersonally;
    }

    public void setQuotationMethod(String quotationMethod) {
        this.quotationMethod = quotationMethod;
    }

    public void setYearRate(String yearRate) {
        this.yearRate = yearRate;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setOriginalMoney(String originalMoney) {
        this.originalMoney = originalMoney;
    }

    public void setBillNos(List<NoteSub> billNos) {
        this.billNos = billNos;
    }
}