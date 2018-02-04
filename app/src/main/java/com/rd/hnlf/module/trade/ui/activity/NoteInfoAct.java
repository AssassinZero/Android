package com.rd.hnlf.module.trade.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.CommonRecyclerViewWithButtonBinding;
import com.rd.hnlf.module.trade.viewControl.NoteInfoCtrl;
import com.rd.hnlf.module.trade.viewModel.NoteDealingVM;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/29 11:04
 * <p/>
 * Description: 票据交易 - 票面信息
 */
@Route(path = RouterUrl.TRADE_NOTE_INFO, extras = RouterExtras.EXTRA_LOGIN)
public class NoteInfoAct extends BaseActivity {
    /**
     * -1 - 代理买卖
     * 0  - 我要买票
     * 1  - 我要卖票
     */
    @Autowired(name = BundleKeys.TYPE)
    String type;
    /** 订单ID */
    @Autowired(name = BundleKeys.ID)
    String id;
    private NoteInfoCtrl viewCtrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonRecyclerViewWithButtonBinding binding = DataBindingUtil.setContentView(this, R.layout.common_recycler_view_with_button);
        viewCtrl = new NoteInfoCtrl(type, id);
        binding.setViewCtrl(viewCtrl);
        binding.toolBar.setTitle(R.string.note_dealing_title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            viewCtrl.addNote((NoteDealingVM) data.getSerializableExtra(BundleKeys.ITEM));
        }
    }
}