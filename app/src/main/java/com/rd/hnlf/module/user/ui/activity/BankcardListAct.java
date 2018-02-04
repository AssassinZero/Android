package com.rd.hnlf.module.user.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.databinding.CommonRecyclerViewBinding;
import com.rd.hnlf.module.user.viewControl.BankcardListCtrl;
import com.rd.hnlf.module.user.viewModel.BankcardVM;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;
import com.rd.tools.utils.AndroidUtil;
import com.rd.views.appbar.TitleBar;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 15:22
 * <p/>
 * Description: 银行卡列表
 */
@Route(path = RouterUrl.USER_BANKCARD_LIST, extras = RouterExtras.EXTRA_LOGIN)
public class BankcardListAct extends BaseActivity {
    private BankcardListCtrl viewCtrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonRecyclerViewBinding binding = DataBindingUtil.setContentView(this, R.layout.common_recycler_view);
        viewCtrl = new BankcardListCtrl();
        binding.setViewCtrl(viewCtrl);
        binding.toolBar.setTitle(R.string.bankcard_list_title);
        binding.toolBar.addAction(new TitleBar.ImageAction(R.drawable.icon_add) {
            @Override
            public void performAction(View view) {
                ARouter.getInstance().build(RouterUrl.USER_BANKCARD_EDIT)
                        .withInt(BundleKeys.POSITION, Constant.NUMBER__1)
                        .navigation(AndroidUtil.getActivity(view), REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            int        position  = data.getIntExtra(BundleKeys.POSITION, -1);
            BankcardVM viewModel = (BankcardVM) data.getSerializableExtra(BundleKeys.ITEM);
            viewCtrl.updateData(position, viewModel);
        }
    }
}