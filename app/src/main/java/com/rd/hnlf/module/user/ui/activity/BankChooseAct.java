package com.rd.hnlf.module.user.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.UserBankChooseActBinding;
import com.rd.hnlf.module.user.viewControl.BankChooseCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/28 9:58
 * <p/>
 * Description: 银行选择界面
 */
@Route(path = RouterUrl.USER_BANK_CHOOSE, extras = RouterExtras.EXTRA_LOGIN)
public class BankChooseAct extends BaseActivity {
    @Autowired(name = BundleKeys.CODE)
    String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final UserBankChooseActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_bank_choose_act);
        binding.setViewCtrl(new BankChooseCtrl(code));
    }
}