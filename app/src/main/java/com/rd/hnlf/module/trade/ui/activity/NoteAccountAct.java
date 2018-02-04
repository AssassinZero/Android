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
import com.rd.hnlf.databinding.TradeNoteAccountActBinding;
import com.rd.hnlf.module.eCommerce.dataModel.submit.TradeNoteInfoSub;
import com.rd.hnlf.module.trade.viewControl.NoteAccountCtrl;
import com.rd.hnlf.module.user.viewModel.BankcardVM;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/29 15:56
 * <p/>
 * Description: 票据交易 - 收票方/持票方信息
 */
@Route(path = RouterUrl.TRADE_NOTE_ACCOUNT, extras = RouterExtras.EXTRA_LOGIN)
public class NoteAccountAct extends BaseActivity {
    /**
     * -1 - 代理买卖
     * 0  - 我要买票
     * 1  - 我要卖票
     */
    @Autowired(name = BundleKeys.TYPE)
    String           type;
    /** 需要上传的数据内容 */
    @Autowired(name = BundleKeys.ITEM)
    TradeNoteInfoSub infoSub;
    private NoteAccountCtrl viewCtrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TradeNoteAccountActBinding binding = DataBindingUtil.setContentView(this, R.layout.trade_note_account_act);
        viewCtrl = new NoteAccountCtrl(type, infoSub);
        binding.setViewCtrl(viewCtrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            viewCtrl.addNote((BankcardVM) data.getSerializableExtra(BundleKeys.ITEM));
        }
    }
}