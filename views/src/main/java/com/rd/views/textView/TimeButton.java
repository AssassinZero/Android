package com.rd.views.textView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.rd.views.NoDoubleClickTextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/23 19:27
 * <p/>
 * Description: 获取验证码倒计时按钮
 * PS: 由于发现timer每次cancel()之后不能重新schedule方法,所以计时完毕只恐timer.每次开始计时的时候重新设置timer,没想到好办法出次下策.
 * PPS: 如果需要记住上次倒计时，则请注意把该类的onCreate()、onDestroy() 和 activity的onCreate()、onDestroy()同步处理
 */
public class TimeButton extends NoDoubleClickTextView implements OnClickListener {
    private static Map<String, Long> map        = new HashMap<>();
    /** 倒计时长，默认 59 秒 */
    private        long              length     = 59 * 1000;
    /** enable时显示的内容 */
    private        String            textBefore = "获取验证码";
    /** disable时显示的内容 */
    private        String            textAfter  = "s重新获取";
    /** TimeButton的点击监听 */
    private OnClickListener    mOnclickListener;
    /** 状态被复位的通知 */
    private TimeButtonCallback mCallback;
    private Timer              timer;
    private TimerTask          timerTask;
    /** 倒计时时间 */
    private long               time;
    /** onDestroy()时，倒计时剩余时间 */
    private String             timeStr;
    /** onDestroy()时，系统时间 */
    private String             currentTimeStr;

    public TimeButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
            TimeButton.this.setText(time / 1000 + textAfter);
            time -= 1000;
            if (time < 0) {
                reset();
            }
        }
    };

    /**
     * 初始化定时器
     */
    private void initTimer() {
        time = length;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                han.sendEmptyMessage(0x01);
            }
        };
    }

    /**
     * 清除计时器
     */
    public void clearTimer() {
        han.removeMessages(0x01);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        time = 0;
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        if (listener instanceof TimeButton) {
            super.setOnClickListener(listener);
        } else {
            this.mOnclickListener = listener;
        }
    }

    /**
     * 设置复位监听
     */
    public void setResetCallback(TimeButtonCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (!enabled || isClickable()) {
            super.setEnabled(enabled);
        }
    }

    @Override
    public void onClick(View view) {
        this.setClickable(false);
        this.setEnabled(false);
        this.setFocusable(false);
        if (mOnclickListener != null)
            mOnclickListener.onClick(view);
    }

    /**
     * 开始倒计时
     */
    public void countdown() {
        initTimer();
        this.setText(time / 1000 + textAfter);
        timer.schedule(timerTask, 0, 1000);
        // timer.scheduleAtFixedRate(task, delay, period);
        if (null != mCallback) {
            mCallback.countdown();
        }
    }

    /**
     * 和activity的onCreate()方法同步
     */
    public void onCreate(Activity activity) {
        timeStr = activity.getClass().getSimpleName() + "_time";
        currentTimeStr = activity.getClass().getSimpleName() + "_ctime";
        // 这里表示没有上次未完成的计时

        if (map == null || !map.containsKey(timeStr) || !map.containsKey(currentTimeStr))
            return;
        long time = System.currentTimeMillis() - map.get(currentTimeStr) - map.get(timeStr);
        map.remove(timeStr);
        map.remove(currentTimeStr);
        if (time > 0) {
            // TODO
        } else {
            initTimer();
            this.time = Math.abs(time);
            timer.schedule(timerTask, 0, 1000);
            this.setText(time + textAfter);
            this.setClickable(false);
            this.setEnabled(false);
            this.setFocusable(false);
        }
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy() {
        if (map == null)
            map = new HashMap<>();
        map.put(timeStr, time);
        map.put(currentTimeStr, System.currentTimeMillis());
        clearTimer();
    }

    /**
     * 设置点击之前的文本
     *
     * @param textBefore
     *         点击之前的文本
     *
     * @return TimeButton
     */
    public TimeButton setTextBefore(String textBefore) {
        this.textBefore = textBefore;
        return this;
    }

    /**
     * 设置计时时显示的文本
     *
     * @param textAfter
     *         计时时显示的文本
     *
     * @return TimeButton
     */
    public TimeButton setTextAfter(String textAfter) {
        this.textAfter = textAfter;
        return this;
    }

    /**
     * 设置倒计时时长
     *
     * @param length
     *         时间 默认毫秒
     *
     * @return TimeButton
     */
    public TimeButton setLength(long length) {
        this.length = length;
        return this;
    }

    /**
     * 获得倒计时时间
     *
     * @return TimeButton
     */
    public long getTime() {
        return time / 1000;
    }

    /**
     * 被动复位
     */
    public void reset() {
        clearTimer();
        this.setClickable(true);
        this.setEnabled(true);
        this.setFocusable(true);
        this.setText(textBefore);
        if (null != mCallback) {
            mCallback.reset();
        }
    }

    public interface TimeButtonCallback {
        void reset();

        void countdown();
    }
}