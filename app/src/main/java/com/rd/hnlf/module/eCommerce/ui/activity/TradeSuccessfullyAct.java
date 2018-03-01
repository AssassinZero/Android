package com.rd.hnlf.module.eCommerce.ui.activity;

/**
 * Created by 12409 on 2018/2/28.
 */

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.TradeSuccessfullyActBinding;
import com.rd.hnlf.module.eCommerce.viewControl.TradeSuccessfullyCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: chaiyuan
 * E-mail: 275108586@qq.com
 * Date: 2018/2/28
 * <p/>
 * Description: 订单交易成功
 */
@Route(path = RouterUrl.TRADE_SUCCESSFULLY, extras = RouterExtras.EXTRA_LOGIN)
public class TradeSuccessfullyAct extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TradeSuccessfullyActBinding binding = DataBindingUtil.setContentView(this, R.layout.trade_successfully_act);
        binding.setViewCtrl(new TradeSuccessfullyCtrl(this));
    }

}
