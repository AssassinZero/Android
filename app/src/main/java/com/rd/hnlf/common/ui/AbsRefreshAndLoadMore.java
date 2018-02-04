package com.rd.hnlf.common.ui;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rd.network.entity.PageMo;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/20 16:51
 * <p/>
 * Description:
 */
public abstract class AbsRefreshAndLoadMore {
    private PtrFrameLayout   ptrFrame;
    private BaseQuickAdapter adapter;
    private PageMo           pageMo;
    private boolean          refreshEnable;
    private boolean          loadMoreEnable;

    public AbsRefreshAndLoadMore() {
        pageMo = new PageMo();
        refreshEnable = true;
        loadMoreEnable = true;
    }

    public void setPtrFrame(PtrFrameLayout ptrFrame) {
        this.ptrFrame = ptrFrame;
    }

    public void setAdapter(BaseQuickAdapter adapter) {
        this.adapter = adapter;
    }

    public void setPageMo(PageMo pageMo) {
        if (null != pageMo) {
            this.pageMo.setCurrent(pageMo.getCurrent());
            this.pageMo.setPages(pageMo.getPages());
            this.pageMo.setPageSize(pageMo.getPageSize());
            this.pageMo.setTotal(pageMo.getTotal());
        }
    }

    public int getCurrent() {
        return pageMo.getCurrent();
    }

    public void setRefreshEnable(boolean refreshEnable) {
        if (null != ptrFrame) {
            ptrFrame.refreshComplete();
            ptrFrame.setEnabled(refreshEnable);
        }
        this.refreshEnable = refreshEnable;
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        if (null != adapter) {
            adapter.loadMoreComplete();
            adapter.setEnableLoadMore(loadMoreEnable);

            if (pageMo.isOver()) {
                adapter.loadMoreEnd();
                adapter.setEnableLoadMore(false);
            }
        }
        this.loadMoreEnable = loadMoreEnable;
    }

    /**
     * 初始化view，只会在第一次创建view的时候，被调用
     */
    public abstract void refreshInit(PtrFrameLayout ptrFrame);

    protected void onRefresh() {
    }

    /**
     * 刷新
     */
    public void refresh() {
        if (null != adapter && refreshEnable) {
            adapter.setEnableLoadMore(false);
        }
        pageMo.refresh();
        onRefresh();
        onRequest();
    }

    /**
     * 初始化view，只会在第一次创建view的时候，被调用
     */
    public abstract void loadMoreInit(BaseQuickAdapter adapter);

    protected void onLoadMore() {
    }

    /**
     * 加载
     */
    public void loadMore() {
        if (null != ptrFrame) {
            ptrFrame.setEnabled(false);
        }
        if (!pageMo.isOver()) {
            pageMo.loadMore();
            onLoadMore();
            onRequest();
        }
    }

    /**
     * 刷新时会调用，用以请求数据
     */
    public abstract void onRequest();

    /**
     * 刷新、加载 完成
     */
    public void complete() {
        if (null != ptrFrame && refreshEnable) {
            ptrFrame.setEnabled(true);
        }
        if (null != adapter && loadMoreEnable) {
            adapter.setEnableLoadMore(true);
        }
        if (null != ptrFrame) {
            ptrFrame.refreshComplete();
        }
        if (null != adapter) {
            adapter.loadMoreComplete();
            if (pageMo.isOver()) {
                adapter.loadMoreEnd();
                adapter.setEnableLoadMore(false);
            }
        }
    }

    /**
     * 刷新 list.clear() 后 adapter.notifyDataSetChanged()
     */
    public void clear(List list) {
        if (pageMo.isRefresh()) {
            list.clear();
            if (null != adapter) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    public boolean isRefresh() {
        return pageMo.isRefresh();
    }

    /**
     * 是否在 Refresh
     */
    private boolean isRefreshing() {
        return null != ptrFrame && ptrFrame.isRefreshing();
    }

    /**
     * 是否在 LoadMore
     */
    private boolean isLoading() {
        return null != adapter && adapter.isLoading();
    }
}