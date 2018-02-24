package com.rd.hnlf.module.trade.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.TradeNoteQuotationActBinding;
import com.rd.hnlf.module.eCommerce.dataModel.submit.TradeNoteInfoSub;
import com.rd.hnlf.module.trade.viewControl.NoteQuotationCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/31 9:55
 * <p/>
 * Description: 票据交易 - 报价信息
 */
@Route(path = RouterUrl.TRADE_NOTE_QUOTATION, extras = RouterExtras.EXTRA_LOGIN)
public class NoteQuotationAct extends BaseActivity {
    /** 需要上传的数据内容 */
    @Autowired(name = BundleKeys.ITEM)
    TradeNoteInfoSub infoSub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TradeNoteQuotationActBinding binding = DataBindingUtil.setContentView(this, R.layout.trade_note_quotation_act);
        binding.setViewCtrl(new NoteQuotationCtrl(infoSub));

    }
}