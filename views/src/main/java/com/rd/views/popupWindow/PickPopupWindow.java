package com.rd.views.popupWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.rd.views.R;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/24 14:07
 * <p>
 * Description: 拍照选择窗口
 */
public class PickPopupWindow extends PopupWindow {
    private LinearLayout layout;

    public PickPopupWindow(Context context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View           mMenuView = inflater.inflate(R.layout.pick_popup_window, null);
        layout = (LinearLayout) mMenuView.findViewById(R.id.layout);
        mMenuView.findViewById(R.id.pickPhotoBtn).setOnClickListener(itemsOnClick);
        mMenuView.findViewById(R.id.takePhotoBtn).setOnClickListener(itemsOnClick);
        mMenuView.findViewById(R.id.cancelBtn).setOnClickListener(itemsOnClick);

        layout.setOnClickListener(itemsOnClick);

        // 设置PickPopupWindow的View
        this.setContentView(mMenuView);
        // 设置PickPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置PickPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置PickPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置PickPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置PickPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = layout.getTop();
                int y      = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}