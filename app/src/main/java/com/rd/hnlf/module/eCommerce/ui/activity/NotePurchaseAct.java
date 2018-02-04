package com.rd.hnlf.module.eCommerce.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.databinding.ECommerceNotePurchaseActBinding;
import com.rd.hnlf.module.eCommerce.viewControl.NotePurchaseCtrl;
import com.rd.hnlf.module.user.viewModel.BankcardVM;
import com.rd.hnlf.router.RouterExtras;
import com.rd.hnlf.router.RouterUrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/2 14:47
 * <p/>
 * Description: 票据购买
 */
@Route(path = RouterUrl.E_COMMERCE_NOTE_PURCHASE, extras = RouterExtras.EXTRA_LOGIN)
public class NotePurchaseAct extends BaseActivity {
    @Autowired(name = BundleKeys.ID)
    String ids;
    private NotePurchaseCtrl viewCtrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ECommerceNotePurchaseActBinding binding = DataBindingUtil.setContentView(this, R.layout.e_commerce_note_purchase_act);
        viewCtrl = new NotePurchaseCtrl(ids);
        binding.setViewCtrl(viewCtrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            viewCtrl.addNote((BankcardVM) data.getSerializableExtra(BundleKeys.ITEM));
        }
    }
}