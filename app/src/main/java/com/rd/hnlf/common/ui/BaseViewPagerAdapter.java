package com.rd.hnlf.common.ui;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/21 10:15
 * <p/>
 * Description:
 */
public class BaseViewPagerAdapter extends FragmentPagerAdapter {
    @NonNull
    private final List<Fragment> list;
    private       PageTitles     pageTitles;

    public BaseViewPagerAdapter(FragmentManager fm, @NonNull List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Sets the page titles for the adapter.
     */
    public void setPageTitles(@Nullable PageTitles pageTitles) {
        this.pageTitles = pageTitles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles == null ? null : pageTitles.getPageTitle(position);
    }

    public interface PageTitles {
        CharSequence getPageTitle(int position);
    }

    @BindingAdapter(value = {"items", "manager", "pageTitles"}, requireAll = false)
    public static void setAdapter(ViewPager viewPager, List<Fragment> list, FragmentManager fm, BaseViewPagerAdapter.PageTitles pageTitles) {
        if (fm == null) {
            throw new IllegalArgumentException("FragmentManager must not be null");
        }
        if (list == null) {
            throw new IllegalArgumentException("items must not be null");
        }
        BaseViewPagerAdapter adapter = new BaseViewPagerAdapter(fm, list);
        adapter.setPageTitles(pageTitles);
        viewPager.setAdapter(adapter);
    }
}