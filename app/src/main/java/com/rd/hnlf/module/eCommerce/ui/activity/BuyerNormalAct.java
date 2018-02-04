package com.rd.hnlf.module.eCommerce.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.eCommerce.ui.fragment.ECommerceListFrag;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/22 18:20
 * <p/>
 * Description: 普通用户 - 我是买家
 */
@Route(path = RouterUrl.E_COMMERCE_BUYER_NORMAL, extras = RouterExtras.EXTRA_LOGIN)
public class BuyerNormalAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_fragment_act);
        int title = R.string.personal_buyer;
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, ECommerceListFrag.newInstance(Constant.E_COMMERCE_BUYER, title)).commit();
    }
}