package com.rd.hnlf.module.trade.ui.activity;

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
import com.rd.hnlf.databinding.TradeDetailActBinding;
import com.rd.hnlf.module.trade.viewControl.TradeDetailCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 14:41
 * <p/>
 * Description: 交易订单详情
 */
@Route(path = RouterUrl.TRADE_DETAIL, extras = RouterExtras.EXTRA_LOGIN)
public class TradeDetailAct extends BaseActivity {
    @Autowired(name = BundleKeys.ID)
    String orderId;
    @Autowired(name = BundleKeys.TYPE)
    String type;
    private TradeDetailCtrl viewCtrl;
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
        TradeDetailActBinding binding = DataBindingUtil.setContentView(this, R.layout.trade_detail_act);
        viewCtrl = new TradeDetailCtrl(orderId, type);
        binding.setViewCtrl(viewCtrl);
        registerReceiver(receiver, new IntentFilter(BundleKeys.REFRESH_LIST));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}