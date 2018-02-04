package com.rd.hnlf.module.trade.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.TradeAddNoteActBinding;
import com.rd.hnlf.module.trade.viewControl.AddNoteCtrl;
import com.rd.hnlf.module.trade.viewModel.NoteDealingVM;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/29 14:28
 * <p/>
 * Description: 票据交易 - 添加票据
 */
@Route(path = RouterUrl.TRADE_ADD_NOTE, extras = RouterExtras.EXTRA_LOGIN)
public class AddNoteAct extends BaseActivity {
    @Autowired(name = BundleKeys.ITEM)
    NoteDealingVM item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TradeAddNoteActBinding binding = DataBindingUtil.setContentView(this, R.layout.trade_add_note_act);
        binding.setViewCtrl(new AddNoteCtrl(item));
    }
}