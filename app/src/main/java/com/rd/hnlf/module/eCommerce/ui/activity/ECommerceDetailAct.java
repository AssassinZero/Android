package com.rd.hnlf.module.eCommerce.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.ECommerceDetailActBinding;
import com.rd.hnlf.module.eCommerce.viewControl.ECommerceDetailCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/24 17:22
 * <p/>
 * Description: 电商订单详情
 */
@Route(path = RouterUrl.E_COMMERCE_DETAIL, extras = RouterExtras.EXTRA_LOGIN)
public class ECommerceDetailAct extends BaseActivity {
    @Autowired(name = BundleKeys.ID)
    String orderNo;
    @Autowired(name = BundleKeys.TYPE)
    String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ECommerceDetailActBinding binding = DataBindingUtil.setContentView(this, R.layout.e_commerce_detail_act);
        binding.setViewCtrl(new ECommerceDetailCtrl(orderNo, type));
    }
}