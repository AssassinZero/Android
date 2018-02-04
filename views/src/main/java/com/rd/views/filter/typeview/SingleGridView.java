package com.rd.views.filter.typeview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.rd.views.filter.adapter.FilterBaseAdapter;
import com.rd.views.filter.interfaces.OnFilterItemClickListener;
import com.rd.views.filter.util.CommonUtil;
import com.rd.views.filter.util.UIUtil;

import java.util.List;

public class SingleGridView<DATA> extends GridView implements AdapterView.OnItemClickListener {
    private FilterBaseAdapter<DATA>         mAdapter;
    private OnFilterItemClickListener<DATA> mOnItemClickListener;

    public SingleGridView(Context context) {
        this(context, null);
    }

    public SingleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SingleGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        setSelector(new ColorDrawable(Color.TRANSPARENT));
        setNumColumns(3);
        setBackgroundColor(Color.WHITE);
        setSmoothScrollbarEnabled(false);

        int dp = UIUtil.dp(context, 15);

        setVerticalSpacing(dp);
        setHorizontalSpacing(dp);
        setPadding(dp, dp, dp, dp);

        setOnItemClickListener(this);
    }

    public SingleGridView<DATA> adapter(FilterBaseAdapter<DATA> adapter) {
        this.mAdapter = adapter;
        setAdapter(adapter);
        return this;
    }

    public SingleGridView<DATA> onItemClick(OnFilterItemClickListener<DATA> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        return this;
    }

    public void setList(List<DATA> list, int checkedPosition) {
        mAdapter.setList(list);

        if (checkedPosition != -1) {
            setItemChecked(checkedPosition, true);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (CommonUtil.isFastDoubleClick()) {
            return;
        }

        DATA item = mAdapter.getItem(position);

        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(position, item);
        }
    }
}