package com.rd.hnlf.module.trade.viewModel.bean;

import android.databinding.Bindable;

import com.rd.hnlf.BR;
import com.rd.hnlf.module.common.viewModel.bean.KVPBean;
import com.rd.hnlf.module.user.viewModel.BankcardVM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/29 17:38
 * <p/>
 * Description: 持票方信息
 */
public class NoteAccountInfo extends BankcardVM {
    /** 账号（手机号） */
    private String                      account;
    /** 银行账户列表 */
    private HashMap<String, BankcardVM> bankcardMap;

    @Bindable
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
        notifyPropertyChanged(BR.account);
    }

    @Bindable
    public ArrayList<KVPBean> getBankcardList() {
        ArrayList<KVPBean> list = new ArrayList<>();
        if (null != bankcardMap) {
            for (Map.Entry entry : bankcardMap.entrySet()) {
                list.add(new KVPBean(entry.getKey().toString(), entry.getValue().toString()));
            }
        }
        return list;
    }

    public void setBankcardMap(HashMap<String, BankcardVM> bankcardMap) {
        this.bankcardMap = bankcardMap;
        notifyPropertyChanged(BR.bankcardList);
    }

    /**
     * 添加到选择框 and 选中并显示此卡
     */
    public void add(BankcardVM vm) {
        if (null == bankcardMap || null == vm) {
            return;
        }
        bankcardMap.put(vm.getBankcard(), vm);
        setSelectedBankcard(vm);
        notifyPropertyChanged(BR.bankcardList);
    }

    @Override
    public void setBankcard(String bankcard) {
        super.setBankcard(bankcard);
        if (null != bankcardMap) {
            setSelectedBankcard(bankcardMap.get(bankcard));
        }
    }

    /**
     * 选中并显示此卡
     */
    public void setSelectedBankcard(BankcardVM vm) {
        if (null != vm) {
            super.setBankcard(vm.getBankcard());
            setAccountName(vm.getAccountName());
            setBankName(vm.getBankName());
            setBankNo(vm.getBankNo());
            setBranchName(vm.getBranchName());
            setBranchNo(vm.getBranchNo());
        }
    }
}