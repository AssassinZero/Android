package com.rd.hnlf.module.eCommerce.dataModel.receive;

import com.rd.hnlf.module.common.viewModel.bean.KVPBean;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/13 10:10
 * <p/>
 * Description: 票据商城 - 查询条件
 */
public class ConditionRec {
    /** 票据类别 */
    private List<KVPBean> typeList;
    /** 发布日期 */
    private List<KVPBean> shelvesTimeList;
    /** 到期期限 */
    private List<KVPBean> dueDateList;
    /** 票面金额 */
    private List<KVPBean> faceAmountList;
    /** 所属企业 */
    private List<KVPBean> countEnterpriselist;

    public List<KVPBean> getTypeList() {
        return typeList;
    }

    public List<KVPBean> getShelvesTimeList() {
        return shelvesTimeList;
    }

    public List<KVPBean> getDueDateList() {
        return dueDateList;
    }

    public List<KVPBean> getFaceAmountList() {
        return faceAmountList;
    }

    public List<KVPBean> getCountEnterpriselist() {
        return countEnterpriselist;
    }
}