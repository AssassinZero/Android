package com.rd.hnlf.module.pure.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.common.ui.BaseViewPagerVM;
import com.rd.hnlf.databinding.CommonViewPagerBinding;
import com.rd.hnlf.module.pure.ui.fragment.NoteListFrag;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;
import com.rd.hnlf.utils.Utils;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/26 15:25
 * <p/>
 * Description: 我的票据
 */
@Route(path = RouterUrl.PURE_MY_NOTE, extras = RouterExtras.EXTRA_LOGIN)
public class MyNoteAct extends BaseActivity {
    private CommonViewPagerBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.common_view_pager);
        BaseViewPagerVM viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.note_tabs), getSupportFragmentManager());

        // 持有中(监管账户的票)
        viewModel.items.add(NoteListFrag.newInstance(Constant.NOTE_HOLDING));
        // 已上架(监管账户的票)
        viewModel.items.add(NoteListFrag.newInstance(Constant.NOTE_PUT_ON));
        // 历史票据(直接交易模式，电商模式，纯票模式卖掉的票)
        viewModel.items.add(NoteListFrag.newInstance(Constant.NOTE_HISTORY));
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        binding.toolBar.setTitle(R.string.personal_my_note);
        binding.tabs.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(viewModel.items.size());
        binding.tabs.post(new Runnable() {
            @Override
            public void run() {
                Utils.setTabLayoutIndicatorWidth(binding.tabs);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment frag = binding.getViewModel().items.get(binding.pager.getCurrentItem());
        if (frag instanceof NoteListFrag && !((NoteListFrag) frag).exitSelectMode()) {
            super.onBackPressed();
        }
    }
}