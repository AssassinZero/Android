package com.rd.hnlf.module.trade.dataModel.receive;

import com.rd.hnlf.module.common.viewModel.bean.KVPBean;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/11 18:37
 * <p/>
 * Description: 字典数据
 */
public class DictionaryRec {
    /** 票据类型 */
    private List<KVPBean> BILL_TYPE;
    /** 票据属性 */
    private List<KVPBean> BILL_ATTRIBUTE;

    public List<KVPBean> getBILL_TYPE() {
        return BILL_TYPE;
    }

    public List<KVPBean> getBILL_ATTRIBUTE() {
        return BILL_ATTRIBUTE;
    }
}