package com.rd.hnlf.module.pure.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.pure.ui.fragment.PureListFrag;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/26 15:23
 * <p/>
 * Description: 纯票订单
 */
@Route(path = RouterUrl.PURE_PURE_ORDER, extras = RouterExtras.EXTRA_LOGIN)
public class PureOrderAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_fragment_act);
        int title = R.string.personal_pure_order;
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, PureListFrag.newInstance(Constant.PURE_LIST, title)).commit();
    }
}