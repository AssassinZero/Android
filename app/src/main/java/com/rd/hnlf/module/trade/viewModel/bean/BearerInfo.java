package com.rd.hnlf.module.trade.viewModel.bean;

import com.rd.hnlf.module.common.viewModel.bean.BillToPartyInfo;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 17:54
 * <p/>
 * Description: 持票方信息
 */
public class BearerInfo extends BillToPartyInfo {
    /** 持票方帐号 or 代理商帐号 */
    private String  name;
    /** 收款账户是否和背书账户一致 */
    private boolean same;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isSame() {
        return same;
    }

    public void setSame(String same) {
        this.same = "10".equals(same);
    }
}