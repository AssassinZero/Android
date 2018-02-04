package com.rd.hnlf.module.pure.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.basic.BaseLazyLoadFragment;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.PureNoteListFragBinding;
import com.rd.hnlf.module.pure.ui.activity.HistoryNoteListAct;
import com.rd.hnlf.module.pure.ui.activity.MyNoteAct;
import com.rd.hnlf.module.pure.viewControl.NoteListCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/28 10:07
 * <p/>
 * Description: 票据列表
 * 普通用户 - 历史票据 {@link HistoryNoteListAct}
 * 会员用户 - 我的票据 {@link MyNoteAct}
 */
public class NoteListFrag extends BaseLazyLoadFragment {
    private NoteListCtrl viewCtrl;
    /** 监听刷新动作 */
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            viewCtrl.exitSelectMode();
            viewCtrl.getListener().refresh();
        }
    };

    public static NoteListFrag newInstance(String type) {
        return newInstance(type, 0);
    }

    public static NoteListFrag newInstance(String type, int title) {
        NoteListFrag fragment = new NoteListFrag();
        Bundle       args     = new Bundle();
        args.putString(BundleKeys.TYPE, type);
        args.putInt(BundleKeys.TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PureNoteListFragBinding binding = DataBindingUtil.inflate(inflater, R.layout.pure_note_list_frag, container, false);
        viewCtrl = new NoteListCtrl(getArguments().getString(BundleKeys.TYPE));
        binding.setViewCtrl(viewCtrl);

        int title = getArguments().getInt(BundleKeys.TITLE);
        if (title != 0) {
            binding.toolBar.setTitle(title);
        } else {
            binding.toolBar.setTitleBarVisibility(false);
        }
        getActivity().registerReceiver(receiver, new IntentFilter(BundleKeys.REFRESH_LIST));
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void fetchData() {
        viewCtrl.reqData();
    }

    /**
     * 当前是否在选择模式
     */
    public boolean exitSelectMode() {
        return viewCtrl.exitSelectMode();
    }
}