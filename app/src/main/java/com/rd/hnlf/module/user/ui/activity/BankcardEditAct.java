package com.rd.hnlf.module.user.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.databinding.UserBankcardEditActBinding;
import com.rd.hnlf.module.common.viewModel.bean.KVPBean;
import com.rd.hnlf.module.user.viewControl.BankcardEditCtrl;
import com.rd.hnlf.module.user.viewModel.BankcardVM;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 16:42
 * <p/>
 * Description: 新增、修改银行卡
 */
@Route(path = RouterUrl.USER_BANKCARD_EDIT, extras = RouterExtras.EXTRA_LOGIN)
public class BankcardEditAct extends BaseActivity {
    /**
     * -2  - 新增账户
     * -1  - 银行卡列表，新增银行卡
     * >=0 - 银行卡列表，修改银行卡
     */
    @Autowired(name = BundleKeys.POSITION)
    int        position;
    /** 在银行卡列表中的 position 对应的对象 */
    @Autowired(name = BundleKeys.ITEM)
    BankcardVM viewModel;
    /** 持票方帐号 - 新增账户 */
    @Autowired(name = BundleKeys.ID)
    String     phone;
    private UserBankcardEditActBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_bankcard_edit_act);
        binding.setViewCtrl(new BankcardEditCtrl(position, viewModel, phone));
        binding.toolBar.setTitle(position < 0 ? (position == -1 ? R.string.bankcard_add_title : R.string.note_account_add) : R.string.bankcard_modify_title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.CHOOSE_BANK && resultCode == RESULT_OK) {
            // 选择开户银行
            KVPBean bean = (KVPBean) data.getSerializableExtra(BundleKeys.ITEM);
            binding.getViewCtrl().getViewModel().setBankName(bean.getText());
            binding.getViewCtrl().getViewModel().setBankNo(bean.getCode());
            binding.getViewCtrl().getViewModel().setBranchName("");
            binding.getViewCtrl().getViewModel().setBranchNo("");
        } else if (requestCode == Constant.CHOOSE_BRANCH && resultCode == RESULT_OK) {
            // 选择开户支行
            KVPBean bean = (KVPBean) data.getSerializableExtra(BundleKeys.ITEM);
            binding.getViewCtrl().getViewModel().setBranchName(bean.getText());
            binding.getViewCtrl().getViewModel().setBranchNo(bean.getCode());
        }
    }
}