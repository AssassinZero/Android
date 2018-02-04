package com.rd.tools.utils;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/4/26$ 11:02$
 * <p/>
 * Description:
 */
public class ClickUtil {
    private static long previousTime;

    /**
     * 是否是快速点击
     */
    public static boolean isFastClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - previousTime < 1000) {
            previousTime = currentTime;
            return true;
        } else {
            previousTime = currentTime;
            return false;
        }
    }
}