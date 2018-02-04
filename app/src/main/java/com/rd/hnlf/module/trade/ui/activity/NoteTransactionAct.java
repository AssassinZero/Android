package com.rd.hnlf.module.trade.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.trade.ui.fragment.TradeListFrag;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/24 18:26
 * <p/>
 * Description: 代理商 - 票据交易
 */
@Route(path = RouterUrl.TRADE_NOTE_TRANSACTION, extras = RouterExtras.EXTRA_LOGIN)
public class NoteTransactionAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_fragment_act);
        int title = R.string.personal_note_dealing;
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, TradeListFrag.newInstance(Constant.TRADE_NOTE_DEALING, title)).commit();
    }
}