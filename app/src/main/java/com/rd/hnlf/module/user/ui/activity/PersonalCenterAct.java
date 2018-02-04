package com.rd.hnlf.module.user.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.databinding.UserPersonalActBinding;
import com.rd.hnlf.module.user.viewControl.PersonalCenterCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/18 14:37
 * <p/>
 * Description: 个人中心
 */
@Route(path = RouterUrl.USER_PERSONAL_CENTER, extras = RouterExtras.EXTRA_LOGIN)
public class PersonalCenterAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isNeedTranslucent = false;
        super.onCreate(savedInstanceState);
        UserPersonalActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_personal_act);
        binding.setViewCtrl(new PersonalCenterCtrl());
    }
}