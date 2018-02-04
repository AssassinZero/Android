package com.rd.hnlf.module.user.viewModel;

import android.databinding.ViewDataBinding;

import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.common.ui.BaseDataBindingViewHolder;
import com.rd.hnlf.common.ui.BaseMultiItemDataBindingAdapter;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/26 14:28
 * <p/>
 * Description:
 */
public class BankcardAdapter extends BaseMultiItemDataBindingAdapter<BankcardVM, BaseDataBindingViewHolder> {
    public BankcardAdapter(List<BankcardVM> data) {
        super(data);
        addItemType(Constant.NUMBER_0, R.layout.user_bankcard_item);
        addItemType(Constant.NUMBER_1, R.layout.user_bankcard_add_item);
    }

    @Override
    protected void convert(BaseDataBindingViewHolder helper, BankcardVM item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.item, item);
        binding.executePendingBindings();
        helper.addOnClickListener(R.id.deleteBtn);
    }
}