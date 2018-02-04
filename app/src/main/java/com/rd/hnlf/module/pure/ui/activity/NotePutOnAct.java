package com.rd.hnlf.module.pure.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.PureNotePutOnActBinding;
import com.rd.hnlf.module.pure.viewControl.NotePutOnCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/28 17:02
 * <p/>
 * Description: 票据上架
 */
@Route(path = RouterUrl.PURE_NOTE_PUT_ON, extras = RouterExtras.EXTRA_LOGIN)
public class NotePutOnAct extends BaseActivity {
    @Autowired(name = BundleKeys.ID)
    String ids;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PureNotePutOnActBinding binding = DataBindingUtil.setContentView(this, R.layout.pure_note_put_on_act);
        binding.setViewCtrl(new NotePutOnCtrl(ids));
    }
}