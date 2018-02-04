package com.rd.hnlf.module.eCommerce.viewModel.bean;

import com.rd.hnlf.module.user.viewModel.BankcardVM;
import com.rd.tools.utils.StringFormat;

import java.util.Map;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/24 17:38
 * <p/>
 * Description: 支付信息
 */
public class PaymentInfo extends BankcardVM {
    /** 企业名称 */
    private String companyName;
    /** 手续费 */
    private String fee;
    /** 手续费 */
    private String bankAccount;
    /** 手续费 */
    private String mobile;

    public String getBankAcount() {
        return this.bankAccount;
    }

    public void setBankAcount(String bankAcount) {
        this.bankAccount = bankAcount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFee() {
        return fee;
    }

    public String getFeeStr() {
        return StringFormat.doubleFormat(fee);
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}