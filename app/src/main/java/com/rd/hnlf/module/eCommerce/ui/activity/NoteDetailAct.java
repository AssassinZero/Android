package com.rd.hnlf.module.eCommerce.ui.activity;

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
import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.ECommerceNoteDetailActBinding;
import com.rd.hnlf.module.eCommerce.viewControl.NoteDetailCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;
import com.youth.banner.listener.OnBannerListener;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/1 17:19
 * <p/>
 * Description: 票据详情
 */
@Route(path = RouterUrl.E_COMMERCE_NOTE_DETAIL, extras = RouterExtras.EXTRA_COMMON)
public class NoteDetailAct extends BaseActivity {
    @Autowired(name = BundleKeys.ID)
    String id;
    ECommerceNoteDetailActBinding binding;
    /** 监听登录登出动作 */
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            binding.getViewCtrl().getViewModel().notifyPropertyChanged(BR.land);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.e_commerce_note_detail_act);
        binding.setViewCtrl(new NoteDetailCtrl(id,this));
        binding.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                binding.getViewCtrl().bannerClick(position);
            }
        });
        registerReceiver(receiver, new IntentFilter(BundleKeys.LOGIN_STATUS_CHANGED));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}