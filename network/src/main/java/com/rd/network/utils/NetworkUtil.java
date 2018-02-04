package com.rd.network.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/1/12 15:27
 * <p/>
 * Description:
 */
public class NetworkUtil {
    private NetworkUtil() {
    }

    public static NetworkUtil getInstance() {
        return NetworkUtilInstance.instance;
    }

    private static class NetworkUtilInstance {
        static NetworkUtil instance = new NetworkUtil();
    }

    /**
     * 是否可以连接网络
     */
    public boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo         mNetworkInfo         = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}