package com.rd.hnlf.module.user.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.databinding.UserRegisterSucceedActBinding;
import com.rd.hnlf.module.user.viewControl.RegisterSucceedCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/18 10:10
 * <p/>
 * Description: 注册成功
 */
@Route(path = RouterUrl.USER_REGISTER_SUCCEED, extras = RouterExtras.EXTRA_COMMON)
public class RegisterSucceedAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserRegisterSucceedActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_register_succeed_act);
        binding.setViewCtrl(new RegisterSucceedCtrl());
    }
}