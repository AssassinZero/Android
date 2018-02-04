package com.rd.tools.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.DataOutputStream;
import java.io.File;
import java.io.OutputStream;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.rd.tools.utils.ContextHolder.getContext;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/1/12 16:55
 * <p/>
 * Description:
 */
public class AndroidUtil {
    /**
     * 通过执行测试命令来检测
     */
    public static boolean isRoot() {
        return execRootCmdSilent("echo test") != -1;
    }

    public static int execRootCmdSilent(String paramString) {
        try {
            Process          localProcess          = Runtime.getRuntime().exec("su");
            Object           localObject           = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream((OutputStream) localObject);
            String           str                   = String.valueOf(paramString);
            localObject = str + "\n";
            localDataOutputStream.writeBytes((String) localObject);
            localDataOutputStream.flush();
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            localProcess.waitFor();
            return localProcess.exitValue();
        } catch (Exception localException) {
            localException.printStackTrace();
            return -1;
        }
    }

    /**
     * copy字符串到粘贴板
     */
    public static void copy(String content) {
        ClipboardManager myClipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
        ClipData         myClip;
        myClip = ClipData.newPlainText("text", content);
        myClipboard.setPrimaryClip(myClip);
    }

    /**
     * 是否运行在主线程中
     */
    public static boolean isInMainThread() {
        return Looper.getMainLooper().equals(Looper.myLooper());
        // return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 通过 View 获取Activity
     */
    public static Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (Activity) view.getRootView().getContext();
    }

    /**
     * 关闭输入法弹出窗
     */
    public static void closedInputMethod() {
        InputMethodManager imm = (InputMethodManager) ActivityManage.peek().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ActivityManage.peek().getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 获取SD卡的根目录
     */
    public static String getSDPath() {
        File sdDir = null;
        // 判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            // 获取跟目录
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir == null) {
            return "";
        } else {
            return sdDir.toString();
        }
    }
}