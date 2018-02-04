package com.rd.views.textView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/6/19 15:59
 * <p/>
 * Description: 可根据字符串所占的长度（非字符串长度）自动缩小字体大小， 以适应显示区域的宽度
 */
public class AdaptiveTextView extends AppCompatTextView {
    public AdaptiveTextView(Context context) {
        super(context);
    }

    public AdaptiveTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdaptiveTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Re size the font so the specified text fits in the text box * assuming
     * the text box is the specified width.
     * 在此方法中学习到：getTextSize 返回值是以像素(px)为单位的，而 setTextSize() 是以 sp 为单位的，
     * 因此要这样设置 setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
     */
    private void refitText(String text, int textWidth) {
        if (textWidth > 0) {
            Paint testPaint = new Paint();
            testPaint.set(this.getPaint());
            // 获得当前 TextView 的有效宽度
            int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
            // 所有字符串所占像素宽度
            float textWidths = testPaint.measureText(text);
            float cTextSize  = this.getTextSize();// 这个返回的单位为px
            while (textWidths > availableWidth) {
                cTextSize = cTextSize - 1;
                testPaint.setTextSize(cTextSize);// 这里传入的单位是px
                textWidths = testPaint.measureText(text);
            }
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, cTextSize);// 这里制定传入的单位是px
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        refitText(getText().toString(), this.getWidth());
    }
}