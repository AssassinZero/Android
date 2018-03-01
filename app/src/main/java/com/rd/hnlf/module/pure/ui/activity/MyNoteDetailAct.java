package com.rd.hnlf.module.pure.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.PureMyNoteDetailActBinding;
import com.rd.hnlf.module.pure.viewControl.MyNoteDetailCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/22 16:48
 * <p/>
 * Description: 我的票据详情
 */
@Route(path = RouterUrl.MY_NOTE_DETAIL, extras = RouterExtras.EXTRA_LOGIN)
public class MyNoteDetailAct extends BaseActivity {
    @Autowired(name = BundleKeys.ID)
    String  orderNo;
    @Autowired(name = BundleKeys.HIDDEN)
    boolean hidden;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PureMyNoteDetailActBinding binding = DataBindingUtil.setContentView(this, R.layout.pure_my_note_detail_act);
        binding.setViewCtrl(new MyNoteDetailCtrl(orderNo, hidden,this));

    }
    public void back(View view){
        finish();
    }
}