package com.rd.hnlf.module.pure.viewModel.bean;

import com.rd.tools.utils.DateUtil;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/24 11:49
 * <p/>
 * Description: 票据背面信息
 */
public class NegativeInfo {
    /** 背书人 */
    private String endorser;
    /** 被背书人 */
    private String endorsee;
    /** 背书日期 */
    private String date;
    /** 可转让标记 */
    private String transferable;

    public String getEndorser() {
        return endorser;
    }

    public void setEndorser(String endorser) {
        this.endorser = endorser;
    }

    public String getEndorsee() {
        return endorsee;
    }

    public void setEndorsee(String endorsee) {
        this.endorsee = endorsee;
    }

    public String getDate() {
        return DateUtil.formatter(DateUtil.Format.DATE, date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransferable() {
        return transferable;
    }

    public void setTransferable(String transferable) {
        this.transferable = transferable;
    }
}