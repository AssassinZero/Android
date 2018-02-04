package com.rd.hnlf.module.user.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.databinding.UserAccountInfoVipActBinding;
import com.rd.hnlf.module.user.viewControl.AccountInfoVIPCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/6 11:05
 * <p/>
 * Description: 账户信息 - 会员用户
 */
@Route(path = RouterUrl.USER_ACCOUNT_INFO_VIP, extras = RouterExtras.EXTRA_LOGIN)
public class AccountInfoVIPAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final UserAccountInfoVipActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_account_info_vip_act);
        binding.setViewCtrl(new AccountInfoVIPCtrl());
    }
}