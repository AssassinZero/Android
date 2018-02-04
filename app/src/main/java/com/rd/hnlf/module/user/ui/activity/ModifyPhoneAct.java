package com.rd.hnlf.module.user.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.UserModifyPhoneActBinding;
import com.rd.hnlf.module.user.CodeLogic;
import com.rd.hnlf.module.user.viewControl.ModifyPhoneCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;
import com.rd.views.textView.TimeButton;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 10:20
 * <p/>
 * Description: 手机绑定
 */
@Route(path = RouterUrl.USER_MODIFY_PHONE, extras = RouterExtras.EXTRA_LOGIN)
public class ModifyPhoneAct extends BaseActivity {
    @Autowired(name = BundleKeys.ID)
    String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final UserModifyPhoneActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_modify_phone_act);
        binding.setViewCtrl(new ModifyPhoneCtrl(phone));
        binding.include.timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view instanceof TimeButton) {
                    CodeLogic.getCodeClick((TimeButton) view, binding.getViewCtrl().getViewModel().getNewPhone(), CodeLogic.BIND_MOBILE);
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