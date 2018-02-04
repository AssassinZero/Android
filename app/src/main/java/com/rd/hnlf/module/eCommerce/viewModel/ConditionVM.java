package com.rd.hnlf.module.eCommerce.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.hnlf.BR;
import com.rd.hnlf.module.common.viewModel.bean.ConditionBean;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/4 16:46
 * <p/>
 * Description:
 */
public class ConditionVM extends BaseObservable {
    /** 票据类别 */
    private List<ConditionBean> typeList;
    /** 发布日期 */
    private List<ConditionBean> releaseDateList;
    /** 到期期限 */
    private List<ConditionBean> dueDateList;
    /** 票面金额 */
    private List<ConditionBean> amountList;
    /** 所属企业 */
    private List<ConditionBean> enterpriseList;
/*
    public ConditionVM() {
        typeList = new ArrayList<>();
        typeList.add(new ConditionBean("00", "全部", ConditionBean.REMARK_TYPE, true));
        typeList.add(new ConditionBean("01", "银票", ConditionBean.REMARK_TYPE));
        typeList.add(new ConditionBean("02", "商票", ConditionBean.REMARK_TYPE));

        releaseDateList = new ArrayList<>();
        releaseDateList.add(new ConditionBean("10", "全部", ConditionBean.REMARK_RELEASE_DATE, true));
        releaseDateList.add(new ConditionBean("11", "近三天", ConditionBean.REMARK_RELEASE_DATE));
        releaseDateList.add(new ConditionBean("12", "近一周", ConditionBean.REMARK_RELEASE_DATE));
        releaseDateList.add(new ConditionBean("13", "近一月", ConditionBean.REMARK_RELEASE_DATE));

        dueDateList = new ArrayList<>();
        dueDateList.add(new ConditionBean("20", "全部", ConditionBean.REMARK_DUE_DATE, true));
        dueDateList.add(new ConditionBean("21", "1月以内", ConditionBean.REMARK_DUE_DATE));
        dueDateList.add(new ConditionBean("22", "1-3月", ConditionBean.REMARK_DUE_DATE));
        dueDateList.add(new ConditionBean("23", "3-6月", ConditionBean.REMARK_DUE_DATE));
        dueDateList.add(new ConditionBean("24", "3-6月以上", ConditionBean.REMARK_DUE_DATE));

        amountList = new ArrayList<>();
        amountList.add(new ConditionBean("30", "全部", ConditionBean.REMARK_AMOUNT, true));
        amountList.add(new ConditionBean("31", "5万元以下", ConditionBean.REMARK_AMOUNT));
        amountList.add(new ConditionBean("32", "5-30万", ConditionBean.REMARK_AMOUNT));
        amountList.add(new ConditionBean("33", "30-100万", ConditionBean.REMARK_AMOUNT));
        amountList.add(new ConditionBean("34", "100-300万", ConditionBean.REMARK_AMOUNT));
        amountList.add(new ConditionBean("35", "300万以上", ConditionBean.REMARK_AMOUNT));

        enterpriseList = new ArrayList<>();
        enterpriseList.add(new ConditionBean("40", "杭州融都科技股份有限公司1", ConditionBean.REMARK_ENTERPRISE, true));
        enterpriseList.add(new ConditionBean("41", "杭州融都科技股份有限公司2", ConditionBean.REMARK_ENTERPRISE));
        enterpriseList.add(new ConditionBean("42", "杭州融都科技股份有限公司3", ConditionBean.REMARK_ENTERPRISE));
    }*/

    @Bindable
    public List<ConditionBean> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<ConditionBean> typeList) {
        this.typeList = typeList;
        notifyPropertyChanged(BR.typeList);
    }

    @Bindable
    public List<ConditionBean> getReleaseDateList() {
        return releaseDateList;
    }

    public void setReleaseDateList(List<ConditionBean> releaseDateList) {
        this.releaseDateList = releaseDateList;
        notifyPropertyChanged(BR.releaseDateList);
    }

    @Bindable
    public List<ConditionBean> getDueDateList() {
        return dueDateList;
    }

    public void setDueDateList(List<ConditionBean> dueDateList) {
        this.dueDateList = dueDateList;
        notifyPropertyChanged(BR.dueDateList);
    }

    @Bindable
    public List<ConditionBean> getAmountList() {
        return amountList;
    }

    public void setAmountList(List<ConditionBean> amountList) {
        this.amountList = amountList;
        notifyPropertyChanged(BR.amountList);
    }

    @Bindable
    public List<ConditionBean> getEnterpriseList() {
        return enterpriseList;
    }

    public void setEnterpriseList(List<ConditionBean> enterpriseList) {
        this.enterpriseList = enterpriseList;
        notifyPropertyChanged(BR.enterpriseList);
    }
}