package com.rd.basic;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/13 10:02
 * <p/>
 * Description: Activity基类
 */
public class BaseActivity extends AppCompatActivity {
    public static final int     REQUEST_CODE_CLOSED = 0x1234;
    public static final int     REQUEST_CODE        = 0x4321;
    protected           boolean isNeedTranslucent   = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNeedTranslucent && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 底部透明导航栏
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CLOSED && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}