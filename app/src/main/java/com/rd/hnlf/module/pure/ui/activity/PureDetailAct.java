package com.rd.hnlf.module.pure.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.PureDetailActBinding;
import com.rd.hnlf.module.pure.viewControl.PureDetailCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 14:41
 * <p/>
 * Description: 纯票订单详情
 */
@Route(path = RouterUrl.PURE_PURE_DETAIL, extras = RouterExtras.EXTRA_LOGIN)
public class PureDetailAct extends BaseActivity {
    @Autowired(name = BundleKeys.ID)
    String orderId;
    private PureDetailCtrl viewCtrl;
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
        PureDetailActBinding binding = DataBindingUtil.setContentView(this, R.layout.pure_detail_act);
        viewCtrl = new PureDetailCtrl(orderId);
        binding.setViewCtrl(viewCtrl);
        registerReceiver(receiver, new IntentFilter(BundleKeys.REFRESH_LIST));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}