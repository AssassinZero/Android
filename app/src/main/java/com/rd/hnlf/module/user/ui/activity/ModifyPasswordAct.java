package com.rd.hnlf.module.user.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.databinding.UserModifyPasswordActBinding;
import com.rd.hnlf.module.user.viewControl.ModifyPasswordCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 14:41
 * <p/>
 * Description: 修改密码
 */
@Route(path = RouterUrl.USER_MODIFY_PASSWORD, extras = RouterExtras.EXTRA_LOGIN)
public class ModifyPasswordAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserModifyPasswordActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_modify_password_act);
        binding.setViewCtrl(new ModifyPasswordCtrl());
    }
}