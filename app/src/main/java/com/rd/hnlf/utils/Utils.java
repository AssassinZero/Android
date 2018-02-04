package com.rd.hnlf.utils;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rd.tools.utils.DensityUtil;

import java.lang.reflect.Field;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 14:19
 * <p/>
 * Description:
 */
public class Utils {
    /**
     * 根据字宽，设置 TabLayout 的下划线 (Indicator) 宽度
     */
    public static void setTabLayoutIndicatorWidth(TabLayout tabLayout) {
        try {
            // 拿到 tabLayout 的 mTabStrip 属性
            Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
            mTabStripField.setAccessible(true);

            LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);

            // tab 左右间距为 10dp
            int margin = DensityUtil.dp2px(tabLayout.getContext(), 10);

            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);

                // 拿到 tabView 的 mTextView 属性
                Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                mTextViewField.setAccessible(true);

                TextView mTextView = (TextView) mTextViewField.get(tabView);
                tabView.setPadding(0, 0, 0, 0);

                // 字多宽线就多宽
                int width = mTextView.getWidth();
                if (width == 0) {
                    mTextView.measure(0, 0);
                    width = mTextView.getMeasuredWidth();
                }
                // MODE_FIXED 模式下，margin = (tabView的宽度 - textView的宽度) / 2
                if (tabLayout.getTabMode() == TabLayout.MODE_FIXED) {
                    margin = (tabView.getWidth() - width) / 2;
                }

                // 设置 tab 左右间距。注意这里不能使用 padding 因为源码中线的宽度是根据 tabView 的宽度来设置的
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                params.width = width;
                params.leftMargin = margin;
                params.rightMargin = margin;
                tabView.setLayoutParams(params);

                tabView.invalidate();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}