package com.rd.hnlf.common.ui;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.rd.hnlf.BR;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/21 10:13
 * <p/>
 * Description:
 */
public class BaseViewPagerVM extends BaseObservable {
    public final ObservableList<Fragment> items = new ObservableArrayList<>();
    private FragmentManager manager;
    /** title数组 */
    private String[]        mPageTitles;

    public BaseViewPagerVM(String[] pageTitles, FragmentManager manager) {
        this.manager = manager;
        this.mPageTitles = pageTitles;
        notifyPropertyChanged(BR.manager);
    }

    public int tabVisible() {
        return null == mPageTitles || mPageTitles.length == 0 ? View.GONE : View.VISIBLE;
    }

    /**
     * 为TabLayout设置title
     */
    public final BaseViewPagerAdapter.PageTitles pageTitles = new BaseViewPagerAdapter.PageTitles() {
        @Override
        public CharSequence getPageTitle(int position) {
            return mPageTitles[position];
        }
    };

    @Bindable
    public FragmentManager getManager() {
        return manager;
    }
}