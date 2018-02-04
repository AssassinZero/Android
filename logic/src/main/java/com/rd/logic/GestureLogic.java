package com.rd.logic;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.rd.logic.greendao.entity.GestureBean;
import com.rd.logic.greendao.helper.GestureDaoHelper;
import com.rd.tools.log.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/8/15 14:32
 * <p/>
 * Description: 手势密码管理类,用来指示 当一个Activity Resume时, 是否要跳转到解锁界面
 * 在Application中调用 GestureLogic.init(...)方法
 */
@SuppressWarnings("unused")
public final class GestureLogic {
    private static final String                         TAG              = "GestureLogic";
    /** APP在后台运行后返回APP，无需解锁的最大时间，默认5秒 */
    private              long                           millsWaitLock    = 5 * 1000;
    /** 最大输入错误次数 */
    private              int                            maxErrorCount    = 5;
    /** 忽略的Activity */
    private              Set<Class<? extends Activity>> ignoreActivities = new HashSet<>();
    /** 锁屏 或者 按了 Home 按键的时间 */
    private              long                           millsStartTime   = -2L;
    /** 手势密码数据 */
    private GestureBean               gestureBean;
    /** 设置手势密码页面 */
    private Class<? extends Activity> lockActivity;
    /** 手势密码解锁页面 */
    private Class<? extends Activity> unlockActivity;

    private GestureLogic() {
    }

    public static GestureLogic getInstance() {
        return LockLogicInstance.instance;
    }

    private static class LockLogicInstance {
        static GestureLogic instance = new GestureLogic();
    }

    /**
     * 手势密码参数初始化
     *
     * @param millsWaitLock
     *         APP在后台运行后返回APP，无需解锁的最大时间
     * @param maxErrorCount
     *         手势密码最大输入错误次数
     * @param lockActivity
     *         设置手势密码页面
     * @param unlockActivity
     *         手势密码解锁页面
     * @param ignoreActivities
     *         忽略的Activity
     */
    public static void init(long millsWaitLock, int maxErrorCount, Class<? extends Activity> lockActivity, Class<? extends Activity> unlockActivity,
                            Set<Class<? extends Activity>> ignoreActivities) {
        GestureLogic.getInstance().millsWaitLock = millsWaitLock;
        GestureLogic.getInstance().maxErrorCount = maxErrorCount;
        GestureLogic.getInstance().lockActivity = lockActivity;
        GestureLogic.getInstance().unlockActivity = unlockActivity;
        GestureLogic.getInstance().ignoreActivities.clear();
        GestureLogic.getInstance().ignoreActivities.addAll(ignoreActivities);
        GestureLogic.getInstance().ignoreActivities.add(lockActivity);
        GestureLogic.getInstance().ignoreActivities.add(unlockActivity);
    }

    /***
     * 检测当前情况下 是否要跳转到手势界面
     */
    public void check(Activity activity, String userId, boolean isLand) {
        // 该Activity在忽略列表，则不处理
        if (GestureLogic.getInstance().isIgnoreActivity(activity)) {
            return;
        }
        showLockView(activity, userId, isLand);
    }

    /**
     * 锁屏
     */
    public void showLockView(Activity activity, String userId, boolean isLand) {
        // 是否开启手势密码
        boolean isOpen = isOpenGesturePassword(userId);
        Logger.d(TAG, "isOpenGesturePassword = " + isOpen);

        if (isOpen) {
            // APP在后台运行的时间，是否小于无需解锁的最小时间
            if (GestureLogic.getInstance().isElapsedEnough()) {
                Logger.i(TAG, "lock from " + activity.getClass().getSimpleName());
                activity.startActivityForResult(new Intent(activity, unlockActivity), 0);
            }
        }
        // else if (isLand) {
        //     // 用户已登录，却没有开启手势密码，则跳转到设置手势密码界面
        //     activity.startActivityForResult(new Intent(activity, lockActivity), 0);
        // }
    }

    /**
     * 当前手势密码是否打开
     */
    private boolean isOpenGesturePassword(String userId) {
        GestureBean bean = getEntity(userId);
        return null != bean && !TextUtils.isEmpty(userId) && !TextUtils.isEmpty(bean.getPassword());
    }

    /**
     * APP到后台,记录时间
     */
    public void start() {
        millsStartTime = System.currentTimeMillis();
    }

    /**
     * APP在后台运行的时间是否小于无需解锁的最小时间
     * true - 需要进入解锁界面
     * false - 无需进入解锁界面
     */
    private boolean isElapsedEnough() {
        return (millsStartTime > 0L || millsStartTime == -2L) && System.currentTimeMillis() > (millsStartTime + millsWaitLock);
    }

    /**
     * 获取还剩余的可解锁的次数
     */
    public int getErrorCount() {
        return Math.max(maxErrorCount - gestureBean.getErrorCount(), 0);
    }

    /**
     * 保存解锁错误次数
     */
    public void addErrorCount() {
        gestureBean.setErrorCount(Math.min(gestureBean.getErrorCount() + 1, maxErrorCount));
        saveEntity(gestureBean);
    }

    public int getMaxErrorCount() {
        return maxErrorCount;
    }

    /**
     * 判断activity是否在忽略列表内
     */
    private boolean isIgnoreActivity(Activity activity) {
        return null != activity && ignoreActivities.contains(activity.getClass());
    }

    /**
     * 重置状态，解锁次数、APP到后台时间 等
     * 设置手势密码 或 解锁成功后调用
     */
    public void reset() {
        millsStartTime = -1L;
        if (null != gestureBean) {
            gestureBean.setErrorCount(0);
            saveEntity(gestureBean);
        }
    }

    /**
     * 清除内存中的手势密码信息.
     * 用户登出时调用
     */
    public void clean() {
        gestureBean = null;
    }

    /**
     * 保存手势密码数据
     */
    public void saveEntity(GestureBean bean) {
        if (null != bean) {
            GestureDaoHelper.getInstance().add(bean);
            gestureBean = bean;
        }
    }

    /**
     * 获取手势密码数据
     */
    public GestureBean getEntity(String userId) {
        if (null == gestureBean) {
            gestureBean = GestureDaoHelper.getInstance().getByUserId(userId);
        }
        return gestureBean;
    }

    public void setEnable(String userId, boolean enable) {
        if (null != getEntity(userId)) {
            getEntity(userId).setEnable(enable);
            saveEntity(getEntity(userId));
        }
    }

    /**
     * 手势密码是否有缓存或者启用
     */
    public boolean isEnable(String userId) {
        return null != getEntity(userId) && getEntity(userId).isEnable();
    }

    /**
     * 删除手势密码
     */
    public void removeEntity(String userId) {
        GestureDaoHelper.getInstance().deleteByUserId(userId);
    }
}