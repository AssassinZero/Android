package com.rd.hnlf.module.common.viewModel.bean;

import com.rd.hnlf.module.user.viewModel.BankcardVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 17:51
 * <p/>
 * Description: 收票方信息
 */
public class BillToPartyInfo extends BankcardVM {
    /** 收票方 、持票方账号 */
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}