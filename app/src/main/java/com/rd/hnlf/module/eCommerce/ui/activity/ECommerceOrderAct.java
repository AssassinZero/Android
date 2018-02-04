package com.rd.hnlf.module.eCommerce.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.common.ui.BaseViewPagerVM;
import com.rd.hnlf.databinding.CommonViewPagerBinding;
import com.rd.hnlf.module.eCommerce.ui.fragment.ECommerceListFrag;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;
import com.rd.hnlf.utils.Utils;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/25 9:42
 * <p/>
 * Description: 电商订单
 */
@Route(path = RouterUrl.E_COMMERCE_ORDER, extras = RouterExtras.EXTRA_LOGIN)
public class ECommerceOrderAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final CommonViewPagerBinding binding   = DataBindingUtil.setContentView(this, R.layout.common_view_pager);
        BaseViewPagerVM              viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.e_commerce_order_tabs), getSupportFragmentManager());

        // 买入票据
        viewModel.items.add(ECommerceListFrag.newInstance(Constant.E_COMMERCE_BUY));
        // 卖出票据
        viewModel.items.add(ECommerceListFrag.newInstance(Constant.E_COMMERCE_SELL));
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        binding.toolBar.setTitle(R.string.personal_e_commerce_order);
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