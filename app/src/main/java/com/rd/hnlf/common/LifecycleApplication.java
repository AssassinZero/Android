package com.rd.hnlf.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.logic.GestureLogic;
import com.rd.logic.info.SharedInfo;
import com.rd.tools.log.Logger;
import com.rd.tools.utils.ActivityManage;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/8/15 14:13
 * <p/>
 * Description: 程序中所有Activity的管理类
 */
@SuppressWarnings("unused")
public class LifecycleApplication extends Application {
    protected static final String TAG   = "LifecycleApplication";
    /** 当前活动的Activity数量 */
    private                int    count = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                ActivityManage.push(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                ActivityManage.setTopActivity(activity);
                if (count++ == 0) {
                    Logger.i(TAG, ">>>>>>>>>>>>>>>>>>> 切到前台 <<<<<<<<<<<<<<<<<<<");
                    gestureCheck(activity);
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                count--;
                if (count == 0) {
                    GestureLogic.getInstance().start();
                    Logger.i(TAG, ">>>>>>>>>>>>>>>>>>> 切到后台 <<<<<<<<<<<<<<<<<<<");
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityManage.remove(activity);
            }
        });
    }

    /**
     * 判断是否进入解锁手势密码界面
     */
    private void gestureCheck(Activity activity) {
        OauthTokenRec oauthTokenMo = SharedInfo.getInstance().getEntity(OauthTokenRec.class);
        if (null != oauthTokenMo && GestureLogic.getInstance().isEnable(oauthTokenMo.getUserId())) {
            GestureLogic.getInstance().check(activity, oauthTokenMo.getUserId(), (boolean) SharedInfo.getInstance().getValue(Constant.IS_LAND, false));
        }
    }
}