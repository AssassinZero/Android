package com.rd.hnlf;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.databinding.MainActBinding;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;
import com.rd.tools.utils.ActivityManage;

@Route(path = RouterUrl.MAIN, extras = RouterExtras.EXTRA_COMMON)
public class MainAct extends BaseActivity {
    private MainActBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedTranslucent = false;
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_act);
        binding.setViewCtrl(new MainCtrl(this,MainAct.this));
    }

    @Override
    public void onBackPressed() {
        if (binding.popupView.getVisibility() == View.VISIBLE) {
            binding.getViewCtrl().moreClick(binding.moreView, binding.popupView);
        } else {
            ActivityManage.onExit();
        }
    }
}