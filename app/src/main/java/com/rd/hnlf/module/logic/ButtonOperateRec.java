package com.rd.hnlf.module.logic;

import com.rd.hnlf.module.common.viewModel.bean.KVPBean;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/8 17:20
 * <p/>
 * Description:
 */
public class ButtonOperateRec {
    /** 我是买家 */
    private List<KVPBean> buyerButtonList;
    /** 我是卖家 */
    private List<KVPBean> sellerButtonList;
    /** 代理商 */
    private List<KVPBean> agentButtonList;

    public List<KVPBean> getBuyerButtonList() {
        return buyerButtonList;
    }

    public List<KVPBean> getSellerButtonList() {
        return sellerButtonList;
    }

    public List<KVPBean> getAgentButtonList() {
        return agentButtonList;
    }
}