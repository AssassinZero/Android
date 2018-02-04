package com.rd.hnlf.module.trade.viewModel.bean;

import android.databinding.Bindable;

import com.rd.hnlf.BR;
import com.rd.hnlf.module.common.viewModel.bean.KVPBean;
import com.rd.hnlf.module.eCommerce.viewModel.bean.EndorseeInfo;
import com.rd.hnlf.module.user.viewModel.BankcardVM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/2 16:30
 * <p/>
 * Description:
 */
public class EndorseeEditInfo extends EndorseeInfo {
    /** 银行账户列表 */
    private HashMap<String, BankcardVM> bankcardMap;

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

    public void add(BankcardVM vm) {
        if (null == bankcardMap || null == vm) {
            return;
        }
        bankcardMap.put(vm.getBankcard(), vm);
        setBankcard(vm.getBankcard());
        notifyPropertyChanged(BR.bankcardList);
    }

    @Override
    public void setBankcard(String bankcard) {
        super.setBankcard(bankcard);
        BankcardVM vm = bankcardMap.get(bankcard);
        setAccountName(vm.getAccountName());
        setBankName(vm.getBankName());
        setBankNo(vm.getBankNo());
        setBranchName(vm.getBranchName());
        setBranchNo(vm.getBranchNo());
    }
}