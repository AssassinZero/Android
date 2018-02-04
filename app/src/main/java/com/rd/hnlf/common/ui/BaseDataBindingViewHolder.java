package com.rd.hnlf.common.ui;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.rd.hnlf.R;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/14 17:06
 * <p/>
 * Description:
 */
public class BaseDataBindingViewHolder extends BaseViewHolder {
    public BaseDataBindingViewHolder(View view) {
        super(view);
    }

    public ViewDataBinding getBinding() {
        return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
    }
}