package com.rd.hnlf.module.pure.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.pure.ui.fragment.NoteListFrag;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/22 14:57
 * <p/>
 * Description: 普通用户 - 历史票据
 */
@Route(path = RouterUrl.PURE_HISTORY_NOTE, extras = RouterExtras.EXTRA_LOGIN)
public class HistoryNoteListAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_fragment_act);
        int title = R.string.personal_history_note;
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, NoteListFrag.newInstance(Constant.NOTE_HISTORY, title)).commit();
    }
}