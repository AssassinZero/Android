package com.rd.hnlf.module.user.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.databinding.UserForgotPasswordActBinding;
import com.rd.hnlf.module.user.CodeLogic;
import com.rd.hnlf.module.user.viewControl.ForgotPasswordCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;
import com.rd.views.textView.TimeButton;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/25 14:01
 * <p/>
 * Description: 忘记密码
 */
@Route(path = RouterUrl.USER_FORGOT_PASSWORD, extras = RouterExtras.EXTRA_COMMON)
public class ForgotPasswordAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final UserForgotPasswordActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_forgot_password_act);
        binding.setViewCtrl(new ForgotPasswordCtrl());
        binding.include.timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view instanceof TimeButton) {
                    CodeLogic.getCodeClick((TimeButton) view, binding.getViewCtrl().getViewModel().getPhone(), CodeLogic.FORGOT_PASSWORD);
                }
            }
        });
        binding.include.timeButton.setResetCallback(new TimeButton.TimeButtonCallback() {
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