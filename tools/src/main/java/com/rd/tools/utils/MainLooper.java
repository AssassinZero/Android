package com.rd.tools.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/16 10:46
 * <p/>
 * Description: 在主线程中运行
 */
public class MainLooper extends Handler {
    private MainLooper(Looper looper) {
        super(looper);
    }

    public static MainLooper getInstance() {
        return MainLooperInstance.instance;
    }

    private static class MainLooperInstance {
        static MainLooper instance = new MainLooper(Looper.getMainLooper());
    }

    public static void runOnUiThread(Runnable runnable) {
        if (AndroidUtil.isInMainThread()) {
            runnable.run();
        } else {
            getInstance().post(runnable);
        }
    }
}