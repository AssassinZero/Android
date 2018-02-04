package com.rd.basic;

import android.os.Bundle;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/7/8 13:47
 * <p/>
 * Description: Fragment LazyLoad
 */
public abstract class BaseLazyLoadFragment extends BaseFragment {
    /** UI 初始化是否完成 */
    private boolean isViewInitiated;
    /** DATA 初始化是否完成 */
    private boolean isDataInitiated;

    /** 获取数据方法 */
    public abstract void fetchData();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        prepareFetchData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // UI初始化完成
        isViewInitiated = true;
        prepareFetchData();
    }

    /**
     * 准备获取数据
     */
    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    /**
     * 准备获取数据
     *
     * @param forceUpdate
     *         是否强制性更新
     */
    public boolean prepareFetchData(boolean forceUpdate) {
        if (getUserVisibleHint() && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        } else {
            return false;
        }
    }
}