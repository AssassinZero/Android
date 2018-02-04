package com.rd.hnlf.module.common.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rd.hnlf.MainAct;
import com.rd.hnlf.R;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/16 17:57
 * <p/>
 * Description: 启动页
 */
public class SplashAct extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView view = new ImageView(this);
        view.setBackgroundResource(R.drawable.splash);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(view);

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashAct.this, MainAct.class));
                SplashAct.this.finish();
            }
        }, 2 * 1000);
    }
}