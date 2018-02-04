package com.rd.hnlf;

import android.app.Activity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.target.ViewTarget;
import com.rd.hnlf.common.BaseParams;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.common.LifecycleApplication;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.logic.greendao.RDDatabaseLoader;
import com.rd.logic.info.SharedInfo;
import com.rd.tools.Config;
import com.rd.tools.log.CrashHandler;
import com.rd.tools.utils.ActivityManage;
import com.rd.tools.utils.ContextHolder;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/16 9:44
 * <p/>
 * Description:
 */
public class MyApplication extends LifecycleApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        basicInit();
        gestureInit();
        dataInit();
    }

    /**
     * 初始化 Application 运行所需的配置信息
     */
    private void basicInit() {
        ContextHolder.init(this);
        Config.DEBUG.set(BaseParams.IS_DEBUG);
        Config.ROOT_PATH.set(BaseParams.ROOT_PATH);

        if (Config.DEBUG.get()) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(this);

        // 崩溃日志
        CrashHandler.getInstance().init();
        // 内存共享对象初始化
        SharedInfo.init(BaseParams.SP_NAME);
        // 数据库配置初始化
        RDDatabaseLoader.init(BaseParams.DATABASE_NAME, null);
        // 使用 OkHttp 加载网络图片
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(new OkHttpClient()));
        // 为 Glide 中使用的 setTag 设置特殊的 ID 用以标识，以防冲突。
        ViewTarget.setTagId(R.id.glide_tag);
        /*
            try {
                // RSA加密 key 初始化
                SerializedUtil.init(RSA.getKey(RSA.MODE.MODULUS_EXPONENT, "密钥类型: 0 - 私钥, 1 - 公钥", "模数", "指数"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        */

        // 退出动作初始化
        ActivityManage.setOperations(new ActivityManage.ExtraOperations() {
            @Override
            public void onExit() {
                // TODO APP退出时需要额外处理的事情，例如广播的反注册，服务的解绑
            }

            @Override
            public void onActivityFinish(Activity activity) {
                // TODO activity 销毁时需要额外处理的事情，例如finish动画等
            }
        });
    }

    /**
     * 手势密码配置信息初始化
     */
    private void gestureInit() {
        // // APP在后台运行后返回APP，无需解锁的最大时间
        // long millsWaitLock = 5 * 60 * 1000;
        // // 手势密码最大输入错误次数
        // int maxErrorCount = 5;
        // // 忽略的Activity
        // Set<Class<? extends Activity>> ignoreActivities = new HashSet<>();
        // ignoreActivities.add(GuideAct.class);
        // ignoreActivities.add(SplashAct.class);
        // // 初始化
        // GestureLogic.init(millsWaitLock, maxErrorCount, LockAct.class, UnlockAct.class, ignoreActivities);
    }

    /**
     * 数据初始化
     */
    private void dataInit() {
        // 根据保存的 OauthToken ，判断用户是否已登录
        if (null != SharedInfo.getInstance().getEntity(OauthTokenRec.class)) {
            SharedInfo.getInstance().saveValue(Constant.IS_LAND, true);
        } else {
            SharedInfo.getInstance().saveValue(Constant.IS_LAND, false);
        }
    }
}