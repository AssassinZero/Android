package com.rd.hnlf.module.user.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.UserResetPasswordActBinding;
import com.rd.hnlf.module.user.viewControl.ResetPasswordCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/25 14:01
 * <p/>
 * Description: 重设密码
 */
@Route(path = RouterUrl.USER_RESET_PASSWORD, extras = RouterExtras.EXTRA_COMMON)
public class ResetPasswordAct extends BaseActivity {
    @Autowired(name = BundleKeys.ID)
    String phone;
    @Autowired(name = BundleKeys.CODE)
    String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserResetPasswordActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_reset_password_act);
        binding.setViewCtrl(new ResetPasswordCtrl(phone, code));
    }
}