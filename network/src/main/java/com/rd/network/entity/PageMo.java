package com.rd.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/5/11 10:41
 * <p/>
 * Description: 页数信息
 */
public class PageMo {
    /** 当前页 */
    // @SerializedName(value = "page.totalPage", alternate = {"totalPage"})
    @SerializedName(Params.CURRENT)
    private int current = 1;
    /** 每页记录数 */
    @SerializedName(Params.PAGE_SIZE)
    private int pageSize;
    /** 总页数 */
    @SerializedName(Params.PAGES)
    private int pages;
    /** 记录数 */
    @SerializedName(Params.TOTAL)
    private int total;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * 是否是刷新
     */
    public boolean isRefresh() {
        return current <= 1;
    }

    /**
     * 刷新 页数归零
     */
    public void refresh() {
        current = 1;
    }

    /**
     * 加载 页数加一
     */
    public void loadMore() {
        current++;
    }

    /**
     * @return 是否已经显示完全
     * true - 已经是最后页，无需再次请求，loadMore 无需再显示
     * false - 还不是最后页，需要再次请求数据，loadMore 需要再显示
     */
    public boolean isOver() {
        return current >= pages;
    }
}