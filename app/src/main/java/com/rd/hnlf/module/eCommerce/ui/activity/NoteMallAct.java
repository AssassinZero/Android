package com.rd.hnlf.module.eCommerce.ui.activity;

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
import com.rd.hnlf.databinding.ECommerceNoteMallActBinding;
import com.rd.hnlf.module.eCommerce.viewControl.NoteMallCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/31 10:38
 * <p/>
 * Description: 票据商城
 */
@Route(path = RouterUrl.E_COMMERCE_NOTE_MALL, extras = RouterExtras.EXTRA_COMMON)
public class NoteMallAct extends BaseActivity {
    private ECommerceNoteMallActBinding binding;
    /** 监听刷新动作 */
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            binding.getViewCtrl().getListener().refresh();
            binding.getViewCtrl().exitSelectMode();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.e_commerce_note_mall_act);
        binding.setViewCtrl(new NoteMallCtrl(binding.dropDownMenu));
        registerReceiver(receiver, new IntentFilter(BundleKeys.REFRESH_LIST));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            binding.getViewCtrl().reqData();
            binding.getViewCtrl().exitSelectMode();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        if (binding.dropDownMenu.isShowing()) {
            binding.dropDownMenu.close();
            return;
        }
        if (!binding.getViewCtrl().exitSelectMode()) {
            super.onBackPressed();
        }
    }
}