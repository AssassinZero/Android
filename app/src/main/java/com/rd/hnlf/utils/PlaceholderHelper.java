package com.rd.hnlf.utils;

import android.content.Context;

import com.rd.hnlf.R;
import com.rd.tools.utils.ContextHolder;
import com.rd.views.PlaceholderLayout;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/1/6 14:16
 * <p/>
 * Description: placeholder 工具类
 */
public class PlaceholderHelper {
    /** 上下文 */
    private Context context;

    private PlaceholderHelper() {
        context = ContextHolder.getContext();
    }

    public static PlaceholderHelper getInstance() {
        return PlaceholderHelperInstance.instance;
    }

    private static class PlaceholderHelperInstance {
        static PlaceholderHelper instance = new PlaceholderHelper();
    }

    public void setStatus(PlaceholderLayout layout, int status) {
        switch (status) {
            case PlaceholderLayout.SUCCESS:
            case PlaceholderLayout.ERROR:
            case PlaceholderLayout.NO_NETWORK:
            case PlaceholderLayout.LOADING:
                layout.setStatus(status);
                return;

            case PlaceholderLayout.EMPTY:
            default:
                layout.setEmptyText(context.getString(R.string.placeholder_empty));
                layout.setEmptyImage(R.drawable.placeholder_empty);
                break;
        }
        layout.setStatus(PlaceholderLayout.EMPTY);
    }
}