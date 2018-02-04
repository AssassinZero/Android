package com.rd.hnlf.module.pure.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.databinding.PureTransactionActBinding;
import com.rd.hnlf.module.common.viewModel.bean.KVPBean;
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.pure.viewControl.PureTransactionCtrl;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

import java.util.ArrayList;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/28 15:23
 * <p/>
 * Description: 纯票交易
 */
@Route(path = RouterUrl.PURE_TRANSACTION, extras = RouterExtras.EXTRA_LOGIN)
public class PureTransactionAct extends BaseActivity {
    private PureTransactionActBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.pure_transaction_act);
        ArrayList<NoteInfo> listExtra = getIntent().getParcelableArrayListExtra(BundleKeys.ITEM);
        binding.setViewCtrl(new PureTransactionCtrl(listExtra));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.CHOOSE_BANK && resultCode == RESULT_OK) {
            // 选择开户银行
            KVPBean bean = (KVPBean) data.getSerializableExtra(BundleKeys.ITEM);
            binding.getViewCtrl().getViewModel().getBillToPartyInfo().setBankName(bean.getText());
            binding.getViewCtrl().getViewModel().getBillToPartyInfo().setBankNo(bean.getCode());
            binding.getViewCtrl().getViewModel().getBillToPartyInfo().setBranchName("");
            binding.getViewCtrl().getViewModel().getBillToPartyInfo().setBranchNo("");
        } else if (requestCode == Constant.CHOOSE_BRANCH && resultCode == RESULT_OK) {
            // 选择开户支行
            KVPBean bean = (KVPBean) data.getSerializableExtra(BundleKeys.ITEM);
            binding.getViewCtrl().getViewModel().getBillToPartyInfo().setBranchName(bean.getText());
            binding.getViewCtrl().getViewModel().getBillToPartyInfo().setBranchNo(bean.getCode());
        }
    }
}