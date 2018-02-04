package com.rd.hnlf.module.eCommerce.ui.fragment;

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
import com.rd.hnlf.databinding.CommonRecyclerViewBinding;
import com.rd.hnlf.module.eCommerce.ui.activity.BuyerNormalAct;
import com.rd.hnlf.module.eCommerce.ui.activity.ECommerceOrderAct;
import com.rd.hnlf.module.eCommerce.viewControl.ECommerceListCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 10:00
 * <p/>
 * Description: 电商订单列表
 * 普通用户 - 我是买家{@link BuyerNormalAct}
 * 会员用户 - 电商订单{@link ECommerceOrderAct}
 */
public class ECommerceListFrag extends BaseLazyLoadFragment {
    private ECommerceListCtrl viewCtrl;
    /** 监听刷新动作 */
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            viewCtrl.getListener().refresh();
        }
    };

    public static ECommerceListFrag newInstance(String type) {
        return newInstance(type, 0);
    }

    public static ECommerceListFrag newInstance(String type, int title) {
        ECommerceListFrag fragment = new ECommerceListFrag();
        Bundle            args     = new Bundle();
        args.putString(BundleKeys.TYPE, type);
        args.putInt(BundleKeys.TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CommonRecyclerViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.common_recycler_view, container, false);
        viewCtrl = new ECommerceListCtrl(getArguments().getString(BundleKeys.TYPE));
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
}