package com.rd.hnlf.module.user.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.UserSecurityCenterActBinding;
import com.rd.hnlf.module.user.viewControl.SecurityCenterCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/18 18:05
 * <p/>
 * Description: 安全中心
 */
@Route(path = RouterUrl.USER_SECURITY_CENTER, extras = RouterExtras.EXTRA_LOGIN)
public class SecurityCenterAct extends BaseActivity {
    private SecurityCenterCtrl viewCtrl;
    /** 监听刷新动作 */
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            viewCtrl.reqData();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserSecurityCenterActBinding binding = DataBindingUtil.setContentView(this, R.layout.user_security_center_act);
        viewCtrl = new SecurityCenterCtrl();
        binding.setViewCtrl(viewCtrl);
        registerReceiver(receiver, new IntentFilter(BundleKeys.REFRESH_LIST));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}