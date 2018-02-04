package com.rd.hnlf.module.user.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.databinding.UserLoginActBinding;
import com.rd.hnlf.module.user.CodeLogic;
import com.rd.hnlf.module.user.viewControl.LoginCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;
import com.rd.views.textView.TimeButton;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/17 9:57
 * <p/>
 * Description: 登录
 */
@Route(path = RouterUrl.USER_LOGIN, extras = RouterExtras.EXTRA_COMMON)
public class LoginAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final UserLoginActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_login_act);
        binding.setViewCtrl(new LoginCtrl());
        binding.timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view instanceof TimeButton) {
                    CodeLogic.getCodeClick((TimeButton) view, binding.getViewCtrl().getViewModel().getPhone(), CodeLogic.LOGIN);
                }
            }
        });
        binding.timeButton.setResetCallback(new TimeButton.TimeButtonCallback() {
            @Override
            public void reset() {
                binding.getViewCtrl().getViewModel().refresh();
            }

            @Override
            public void countdown() {
            }
        });
    }
}