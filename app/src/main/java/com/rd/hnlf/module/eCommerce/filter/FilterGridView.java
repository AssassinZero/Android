package com.rd.hnlf.module.eCommerce.filter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.rd.hnlf.R;
import com.rd.hnlf.module.common.viewModel.bean.ConditionBean;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/4 18:01
 * <p/>
 * Description:
 */
public class FilterGridView extends LinearLayout {
    private RecyclerView             recyclerView;
    private List<ConditionBean>      data;
    private OnItemChildClickListener onItemChildClickListener;
    private OnClickListener          onClickListener;

    public FilterGridView(Context context) {
        super(context);
        init(context);
    }

    public FilterGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.WHITE);
        inflate(context, R.layout.filter_grid_view_layout, this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    public List<ConditionBean> getData() {
        return data;
    }

    public FilterGridView setData(List<ConditionBean> data) {
        this.data = data;
        return this;
    }

    public FilterGridView setOnButtonClickListener(OnClickListener listener) {
        onClickListener = listener;
        return this;
    }

    public FilterGridView setOnItemChildClickListener(OnItemChildClickListener listener) {
        onItemChildClickListener = listener;
        return this;
    }

    public FilterGridView build() {
        findViewById(R.id.filterCompete).setOnClickListener(onClickListener);

        recyclerView.setAdapter(new FilterGridAdapter(data));
        recyclerView.addOnItemTouchListener(onItemChildClickListener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                String remark = data.get(position).getRemark();
                if (TextUtils.isEmpty(remark) || remark.equals(ConditionBean.REMARK_ENTERPRISE)) {
                    return 3;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        return this;
    }
}