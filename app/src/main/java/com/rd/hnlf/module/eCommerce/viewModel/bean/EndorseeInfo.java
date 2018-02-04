package com.rd.hnlf.module.eCommerce.viewModel.bean;

import android.databinding.Bindable;

import com.rd.hnlf.BR;
import com.rd.hnlf.module.user.viewModel.BankcardVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/24 17:32
 * <p/>
 * Description: 被背书账户信息
 */
public class EndorseeInfo extends BankcardVM {
    /** 企业名称 */
    private String companyName;

    @Bindable
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
        notifyPropertyChanged(BR.companyName);
    }
}