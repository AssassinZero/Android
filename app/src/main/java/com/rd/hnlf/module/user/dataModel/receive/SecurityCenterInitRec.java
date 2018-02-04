package com.rd.hnlf.module.user.dataModel.receive;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/25 17:28
 * <p/>
 * Description:
 */
public class SecurityCenterInitRec {
    /** 绑定手机号 */
    private String verifyMobile;
    /** 银行卡数量 */
    private String bankCount;

    public String getVerifyMobile() {
        return verifyMobile;
    }

    public String getBankCount() {
        return bankCount;
    }
}