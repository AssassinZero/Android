package com.rd.hnlf.module.common.viewModel.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.Keep;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.rd.hnlf.BR;

import java.io.Serializable;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/4 16:43
 * <p/>
 * Description: 键值对
 */
@Keep
public class ConditionBean extends BaseObservable implements Serializable, MultiItemEntity {
    /** 票据类型 */
    public static final String REMARK_TYPE         = "type";
    /** 发布日期 */
    public static final String REMARK_RELEASE_DATE = "shelvesTime";
    /** 到期期限 */
    public static final String REMARK_DUE_DATE     = "dueDate";
    /** 票面金额 */
    public static final String REMARK_AMOUNT       = "faceAmount";
    /** 所属企业 */
    public static final String REMARK_ENTERPRISE   = "enterpriseName";
    /** 键 */
    private String  key;
    /** 值 */
    private String  value;
    /** 备注 */
    private String  remark;
    /**
     * 布局type
     * 0 - item 布局
     * 1 - title 布局
     */
    private int     itemType;
    /** 标记：用于一些可能需要的逻辑判断，例如是否被选中 */
    private boolean flag;

    public ConditionBean(String remark) {
        this(remark, 0);
    }

    public ConditionBean(String remark, int itemType) {
        this.remark = remark;
        this.itemType = itemType;
    }

    public ConditionBean(String key, String value, String remark) {
        this(key, value, remark, 0);
    }

    public ConditionBean(String key, String value, String remark, boolean flag) {
        this(key, value, remark, 0, flag);
    }

    public ConditionBean(String key, String value, String remark, int itemType) {
        this(key, value, remark, itemType, false);
    }

    public ConditionBean(String key, String value, String remark, int itemType, boolean flag) {
        this.key = key;
        this.value = value;
        this.remark = remark;
        this.itemType = itemType;
        this.flag = flag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Bindable
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
        notifyPropertyChanged(BR.flag);
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    @Override
    public String toString() {
        return value;
    }
}