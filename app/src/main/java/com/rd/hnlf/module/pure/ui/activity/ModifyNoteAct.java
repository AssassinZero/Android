package com.rd.hnlf.module.pure.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.PureModifyNoteActBinding;
import com.rd.hnlf.module.pure.viewControl.ModifyNoteCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/28 17:18
 * <p/>
 * Description: 修改票据
 */
@Route(path = RouterUrl.PURE_MODIFY_NOTE, extras = RouterExtras.EXTRA_LOGIN)
public class ModifyNoteAct extends BaseActivity {
    @Autowired(name = BundleKeys.ID)
    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PureModifyNoteActBinding binding = DataBindingUtil.setContentView(this, R.layout.pure_modify_note_act);
        binding.setViewCtrl(new ModifyNoteCtrl(id));
    }
}