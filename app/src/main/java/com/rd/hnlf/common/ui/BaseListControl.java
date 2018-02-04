package com.rd.hnlf.common.ui;

import com.rd.basic.BaseRecyclerViewControl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/14 15:39
 * <p/>
 * Description:
 */
public class BaseListControl extends BaseRecyclerViewControl {
    /** 刷新 加载 */
    private AbsRefreshAndLoadMore listener;

    public AbsRefreshAndLoadMore getListener() {
        return listener;
    }

    public void setListener(AbsRefreshAndLoadMore listener) {
        this.listener = listener;
    }
}