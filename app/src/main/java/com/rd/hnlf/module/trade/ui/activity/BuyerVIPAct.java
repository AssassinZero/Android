package com.rd.hnlf.module.trade.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.common.ui.BaseViewPagerVM;
import com.rd.hnlf.databinding.CommonViewPagerBinding;
import com.rd.hnlf.module.trade.ui.fragment.TradeListFrag;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;
import com.rd.hnlf.utils.Utils;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/22 18:20
 * <p/>
 * Description: 会员用户 - 我是买家
 */
@Route(path = RouterUrl.TRADE_BUYER_VIP, extras = RouterExtras.EXTRA_LOGIN)
public class BuyerVIPAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final CommonViewPagerBinding binding   = DataBindingUtil.setContentView(this, R.layout.common_view_pager);
        BaseViewPagerVM              viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.buyer_tabs), getSupportFragmentManager());

        // 全部
        viewModel.items.add(TradeListFrag.newInstance(Constant.TRADE_BUYER_ALL));
        // 待经办
        viewModel.items.add(TradeListFrag.newInstance(Constant.TRADE_BUYER_HANDLE));
        // 待复核
        viewModel.items.add(TradeListFrag.newInstance(Constant.TRADE_BUYER_REVIEW));
        // 待支付
        viewModel.items.add(TradeListFrag.newInstance(Constant.TRADE_BUYER_PAYMENT));
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        binding.toolBar.setTitle(R.string.personal_buyer);
        binding.tabs.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(viewModel.items.size());
        binding.tabs.post(new Runnable() {
            @Override
            public void run() {
                Utils.setTabLayoutIndicatorWidth(binding.tabs);
            }
        });
    }
}