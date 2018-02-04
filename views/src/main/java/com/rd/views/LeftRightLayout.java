package com.rd.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rd.tools.utils.DensityUtil;

/**
 * Author: TaoHao
 * E-mail: taoh@erongdu.com
 * Date: 2016/11/7$ 17:58$
 * <p/>
 * Description: 横条左右控件
 */
public class LeftRightLayout extends RelativeLayout {
    /** 左边文字 */
    private String    leftTxt;
    /** 左边文字颜色 */
    private int       leftColor;
    /** 左边文字大小 */
    private float     leftSize;
    /** 右边文字 */
    private String    rightTxt;
    /** 右边文字颜色 */
    private int       rightColor;
    /** 右边文字大小 */
    private float     rightSize;
    /** 箭头 */
    private Bitmap    arrow;
    /** 设置GroupView的enabled */
    private boolean   leftRightEnabled;
    /** 设置GroupView的enabled颜色 */
    private int       enabledColor;
    /** 右侧图片ID */
    private int       arrowRightRes;
    private TextView  mLeftView;
    private TextView  mRightView;
    private ImageView mImageView;

    public LeftRightLayout(Context context) {
        this(context, null);
    }

    public LeftRightLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftRightLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.left_right_layout, this, true);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LeftRightLayout);
        // 获取自定义属性和默认值
        if (mTypedArray != null) {
            leftTxt = mTypedArray.getString(R.styleable.LeftRightLayout_leftTxt);
            leftColor = mTypedArray.getColor(R.styleable.LeftRightLayout_leftTxtColor, ContextCompat.getColor(context, R.color.text_black));
            leftSize = mTypedArray.getDimension(R.styleable.LeftRightLayout_leftTxtSize, DensityUtil.sp2px(context, 16));
            rightTxt = mTypedArray.getString(R.styleable.LeftRightLayout_rightTxt);
            rightColor = mTypedArray.getColor(R.styleable.LeftRightLayout_rightTxtColor, ContextCompat.getColor(context, R.color.text_grey));
            rightSize = mTypedArray.getDimension(R.styleable.LeftRightLayout_rightTxtSize, DensityUtil.sp2px(context, 16));
            arrowRightRes = mTypedArray.getResourceId(R.styleable.LeftRightLayout_arrow, 0);
            leftRightEnabled = mTypedArray.getBoolean(R.styleable.LeftRightLayout_leftRightEnabled, true);
            enabledColor = mTypedArray.getColor(R.styleable.LeftRightLayout_enabledColor, ContextCompat.getColor(context, R.color.text_grey));
            mTypedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // 获取子控件
        mLeftView = (TextView) findViewById(R.id.left_textView);
        mRightView = (TextView) findViewById(R.id.right_textView);
        mImageView = (ImageView) findViewById(R.id.right_arrow);

        //将从资源文件中加载的属性设置给子控件
        if (!TextUtils.isEmpty(leftTxt)) {
            setLeftText(leftTxt);
        }

        if (!TextUtils.isEmpty(rightTxt)) {
            setRightText(rightTxt);
        }
        setRightTextColor(rightColor);
        setRightTextSize(rightSize);
        setLeftTextColor(leftColor);
        setLeftTextSize(leftSize);

        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        if (arrowRightRes != 0) {
            arrow = BitmapFactory.decodeResource(getResources(), arrowRightRes);
            mImageView.setImageBitmap(arrow);
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置左边文字
     */
    public void setLeftText(String text) {
        mLeftView.setText(text);
    }

    /**
     * 设置左边文字颜色
     */
    public void setLeftTextColor(int color) {
        mLeftView.setTextColor(color);
    }

    /**
     * 设置左边文字大小
     */
    public void setLeftTextSize(float size) {
        mLeftView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置左边文字是否可点击
     */
    public void setLeftTxtEnabled(boolean enabled) {
        mLeftView.setEnabled(enabled);
    }

    /**
     * 设置右边文字
     */
    public void setRightText(String text) {
        mRightView.setText(text);
    }

    /**
     * 设置右边文字颜色
     */
    public void setRightTextColor(int color) {
        mRightView.setTextColor(color);
    }

    /**
     * 设置右边文字大小
     */
    public void setRightTextSize(float size) {
        mRightView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置右边文字是否可点击
     */
    public void setRightTxtEnabled(boolean enabled) {
        mRightView.setEnabled(enabled);
    }

    public void setLeftTxt(String leftTxt) {
        this.leftTxt = leftTxt;
        setLeftText(leftTxt);
    }

    public void setLeftTxtColor(int leftColor) {
        this.leftColor = leftColor;
        setLeftTextColor(leftColor);
    }

    public void setRightTxt(String rightTxt) {
        this.rightTxt = rightTxt;
        setRightText(rightTxt);
    }

    public void setRightTxtColor(int rightColor) {
        this.rightColor = rightColor;
        setRightTextColor(rightColor);
    }

    public void setArrow(int arrowRightRes) {
        arrow = BitmapFactory.decodeResource(getResources(), arrowRightRes);
        mImageView.setImageBitmap(arrow);
        mImageView.setVisibility(View.VISIBLE);
    }

    public boolean isLeftRightEnabled() {
        return leftRightEnabled;
    }

    public void setLeftRightEnabled(boolean leftRightEnabled) {
        if (!leftRightEnabled) {
            setLeftTextColor(enabledColor);
            setRightTextColor(enabledColor);
        } else {
            setLeftTextColor(leftColor);
            setRightTextColor(rightColor);
        }
        setEnabled(leftRightEnabled);
        this.leftRightEnabled = leftRightEnabled;
    }
}