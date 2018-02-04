package com.rd.hnlf.common.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.rd.hnlf.R;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/14 16:59
 * <p/>
 * Description:
 */
public abstract class BaseMultiItemDataBindingAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseMultiItemQuickAdapter<T, K> {
    public BaseMultiItemDataBindingAdapter(List<T> data) {
        super(data);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}