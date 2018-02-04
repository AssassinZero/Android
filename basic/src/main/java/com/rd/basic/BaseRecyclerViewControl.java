package com.rd.basic;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;

import com.rd.views.PlaceholderLayout;
import com.rd.views.recyclerView.DividerLine;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/14$ 15:55$
 * <p/>
 * Description:
 */
public class BaseRecyclerViewControl extends BaseObservable {
    /** RecyclerView Adapter */
    private RecyclerView.Adapter               recyclerAdapter;
    /**
     * RecyclerView LayoutManager
     * 0 - 线性布局管理器 - 垂直
     * 1 - 线性布局管理器 - 垂直(ScrollView 嵌套 RecyclerView)
     * 2 - 线性布局管理器 - 水平
     */
    private int                                layoutManagerType;
    /** RecyclerView 点击事件 */
    private RecyclerView.OnItemTouchListener   itemTouchListener;
    /**
     * 绘制区域是否在padding里面
     * true  - 不在
     * false - 在
     */
    private boolean                            clipToPadding;
    /**
     * 分割线类型
     * -9 - 十字分割线
     * -1 - 没有分割线
     * 0  - 水平分割线(不缩进)
     * 1  - 垂直分割线
     * 2  - 水平分割线(左缩进)
     */
    private int                                itemDecorationType;
    /** 占位图 */
    private ObservableInt                      placeHolderType;
    private PlaceholderLayout.OnReloadListener retryListener;

    protected BaseRecyclerViewControl() {
        clipToPadding = true;
        itemDecorationType = DividerLine.DEFAULT;
        placeHolderType = new ObservableInt(PlaceholderLayout.LOADING);
    }

    @Bindable
    public RecyclerView.Adapter getRecyclerAdapter() {
        return recyclerAdapter;
    }

    public void setRecyclerAdapter(RecyclerView.Adapter recyclerAdapter) {
        this.recyclerAdapter = recyclerAdapter;
        notifyPropertyChanged(BR.recyclerAdapter);
    }

    @Bindable
    public int getLayoutManagerType() {
        return layoutManagerType;
    }

    public void setLayoutManagerType(int layoutManagerType) {
        this.layoutManagerType = layoutManagerType;
        notifyPropertyChanged(BR.layoutManagerType);
    }

    @Bindable
    public RecyclerView.OnItemTouchListener getItemTouchListener() {
        return itemTouchListener;
    }

    public void setItemTouchListener(RecyclerView.OnItemTouchListener itemTouchListener) {
        this.itemTouchListener = itemTouchListener;
        notifyPropertyChanged(BR.itemTouchListener);
    }

    @Bindable
    public boolean isClipToPadding() {
        return clipToPadding;
    }

    public void setClipToPadding(boolean clipToPadding) {
        this.clipToPadding = clipToPadding;
        notifyPropertyChanged(BR.clipToPadding);
    }

    @Bindable
    public int getItemDecorationType() {
        return itemDecorationType;
    }

    public void setItemDecorationType(int itemDecorationType) {
        this.itemDecorationType = itemDecorationType;
        notifyPropertyChanged(BR.itemDecorationType);
    }

    public ObservableInt getPlaceHolderType() {
        return placeHolderType;
    }

    public void setPlaceHolderType(int placeHolderType) {
        this.placeHolderType.set(placeHolderType);
    }

    @Bindable
    public PlaceholderLayout.OnReloadListener getRetryListener() {
        return retryListener;
    }

    public void setRetryListener(PlaceholderLayout.OnReloadListener retryListener) {
        this.retryListener = retryListener;
        notifyPropertyChanged(BR.retryListener);
    }
}