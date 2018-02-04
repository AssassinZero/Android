package com.rd.views.filter.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.Checkable;

import com.rd.views.R;

public class FilterCheckedTextView extends AppCompatTextView implements Checkable {
    private boolean mChecked;

    public FilterCheckedTextView(Context context) {
        this(context, null);
    }

    public FilterCheckedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked != mChecked) {
            mChecked = checked;
            refreshDrawableState();
        }
        if (checked) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.common_filter_item_selected, 0);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}