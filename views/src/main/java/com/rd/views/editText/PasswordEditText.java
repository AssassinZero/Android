package com.rd.views.editText;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.rd.tools.utils.EditTextFormat;
import com.rd.tools.utils.TextUtil;
import com.rd.views.R;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/12/15 17:14
 * <p/>
 * Description:
 */
public class PasswordEditText extends ClearEditText {
    // 资源
    private final int INVISIBLE = R.drawable.icon_hide;
    private final int VISIBLE   = R.drawable.icon_show;
    // 按钮宽度dp
    private int           mWidth;
    // 按钮的bitmap
    private Bitmap        mBitmapInvisible;
    private Bitmap        mBitmapVisible;
    // 按钮出现动画
    private ValueAnimator mAnimatorVisible;
    // 按钮消失动画
    private ValueAnimator mAnimatorGone;
    // 内容是否是明文
    private boolean isPlaintext = false;
    // 是否只在获得焦点后显示
    private boolean focusedShow = false;
    // 按钮是否可见（描述的当前状态）
    private boolean isVisible   = false;
    private Rect rect;

    public PasswordEditText(final Context context) {
        super(context);
        init(context);
    }

    public PasswordEditText(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PasswordEditText(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setSingleLine();
        // 限制EditText不能输入空格
        EditTextFormat.addFilter(this, EditTextFormat.getBlankFilter());
        // 设置EditText文本为隐藏的(注意！需要在setSingleLine()之后调用)
        setTransformationMethod(PasswordTransformationMethod.getInstance());
        // 输入方式
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        mWidth = getWidthClear() + (int) context.getResources().getDimension(R.dimen.x10);
        addRight(mWidth + interval);
        mBitmapInvisible = createBitmap(INVISIBLE, context);
        mBitmapVisible = createBitmap(VISIBLE, context);
        mAnimatorVisible = ValueAnimator.ofFloat(0f, 1f).setDuration(ANIMATOR_TIME);
        mAnimatorGone = ValueAnimator.ofFloat(1f, 0f).setDuration(ANIMATOR_TIME);
        rect = new Rect();
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (text.length() > 0 && isFocused() && isEditThis()) {
            if (!isVisible) {
                startVisibleAnimator();
            }
        } else {
            if (isVisible && focusedShow) {
                startGoneAnimator();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAnimatorVisible.isRunning()) {
            float scale = (float) mAnimatorVisible.getAnimatedValue();
            drawVisible(scale, canvas);
            invalidate();
        } else if (!focusedShow || isVisible) {
            drawVisible(1f, canvas);
        }

        if (mAnimatorGone.isRunning()) {
            float scale = (float) mAnimatorGone.getAnimatedValue();
            drawVisible(scale, canvas);
            invalidate();
        }
    }

    /**
     * 绘制内容是否是明文的ICON
     */
    private void drawVisible(float scale, Canvas canvas) {
        int right  = (int) (getWidth() + getScrollX() - getmPaddingRight() - interval - mWidth * (1f - scale) / 2f);
        int left   = (int) (getWidth() + getScrollX() - getmPaddingRight() - interval - mWidth * (scale + (1f - scale) / 2f));
        int top    = (int) ((getHeight() - mWidth * scale) / 2);
        int bottom = (int) (top + mWidth * scale);

        rect.set(left, top, right, bottom);
        if (isPlaintext) {
            canvas.drawBitmap(mBitmapVisible, null, rect, null);
        } else {
            canvas.drawBitmap(mBitmapInvisible, null, rect, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            boolean touchable = (getWidth() - getmPaddingRight() - mWidth - interval < event.getX())
                    && (event.getX() < getWidth() - getmPaddingRight() - interval);
            if (touchable && isVisible) {
                isPlaintext = !isPlaintext;
                if (isPlaintext) {
                    // 设置EditText文本为可见的
                    setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // 设置EditText文本为隐藏的
                    setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focusedShow) {
            focusedShow(focused);
        } else {
            if (!isVisible) {
                startVisibleAnimator();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (focusedShow) {
            focusedShow(isFocusable());
        } else {
            if (!isVisible) {
                startVisibleAnimator();
            }
        }
    }

    /**
     * 只在获得焦点后显示
     */
    private void focusedShow(boolean focused) {
        if (!TextUtil.isEmpty(getText()) && isEditThis() && focused) {
            // 隐藏，则显示
            if (!isVisible) {
                startVisibleAnimator();
            }
        } else {
            // 显示，则隐藏
            if (isVisible) {
                startGoneAnimator();
            }
        }
    }

    /**
     * 显示动画
     */
    private void startVisibleAnimator() {
        isVisible = true;
        endAnimator();
        mAnimatorVisible.start();
        invalidate();
    }

    /**
     * 消失动画
     */
    private void startGoneAnimator() {
        isVisible = false;
        endAnimator();
        mAnimatorGone.start();
        invalidate();
    }

    /**
     * 结束所有动画
     */
    private void endAnimator() {
        mAnimatorGone.end();
        mAnimatorVisible.end();
    }

    public void setFocusedShow(boolean focusedShow) {
        this.focusedShow = focusedShow;
    }
}