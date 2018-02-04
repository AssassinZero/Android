package com.rd.hnlf.module.eCommerce.filter;

import android.databinding.ViewDataBinding;

import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.common.ui.BaseDataBindingViewHolder;
import com.rd.hnlf.common.ui.BaseMultiItemDataBindingAdapter;
import com.rd.hnlf.module.common.viewModel.bean.ConditionBean;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/5 16:49
 * <p/>
 * Description:
 */
public class FilterGridAdapter extends BaseMultiItemDataBindingAdapter<ConditionBean, BaseDataBindingViewHolder> {
    public FilterGridAdapter(List<ConditionBean> data) {
        super(data);
        addItemType(Constant.NUMBER_0, R.layout.filter_item_layout);
        addItemType(Constant.NUMBER_1, R.layout.filter_title_layout);
    }

    @Override
    protected void convert(BaseDataBindingViewHolder helper, ConditionBean item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.item, item);
        binding.executePendingBindings();
    }
}