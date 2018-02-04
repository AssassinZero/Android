package com.rd.hnlf.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.StringRes;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.Gravity;

import com.rd.hnlf.R;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.pedant.SweetAlert.SweetAlertType;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/1/11 11:22
 * <p/>
 * Description: 提示框工具类
 */
public class DialogUtils {
    /** 取消文字资源 */
    private static int cancelResId  = R.string.dialog_cancel;
    /** 确定文字资源 */
    private static int confirmResId = R.string.dialog_confirm;
    /** 按钮文字颜色 */
    private static int textColor    = R.color.text_purple;
    ///////////////////////////////////////////////////////////////////////////
    // String文本
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param context
     *         上下文
     * @param contentText
     *         内容
     * @param cancelText
     *         取消文本
     * @param confirmText
     *         确定文本
     * @param cancelable
     *         返回键是否可取消
     * @param cancelClick
     *         取消事件
     * @param confirmClick
     *         确定事件
     */
    public static void showDialog(Context context, String contentText, String cancelText, String confirmText, boolean cancelable,
                                  OnSweetClickListener cancelClick, OnSweetClickListener confirmClick) {
        if (!activityIsRunning(context)) {
            return;
        }
        if (TextUtils.isEmpty(cancelText)) {
            cancelText = context.getString(cancelResId);
        }

        if (TextUtils.isEmpty(confirmText)) {
            confirmText = context.getString(confirmResId);
        }
        SweetAlertDialog dialog = new SweetAlertDialog(context)
                .setContentText(contentText)
                .setCancelText(cancelText)
                .setCancelTextColor(textColor)
                .setCancelClickListener(cancelClick)
                .setConfirmText(confirmText)
                .setConfirmTextColor(textColor)
                .setConfirmClickListener(confirmClick);
        dialog.setCancelable(cancelable);
        dialog.show();
    }

    /**
     * @param context
     *         上下文
     * @param contentText
     *         内容
     * @param cancelText
     *         取消文本
     * @param confirmText
     *         确定文本
     * @param cancelable
     *         返回键是否可取消
     * @param cancelClick
     *         取消事件
     * @param confirmClick
     *         确定事件
     */
    public static void showDialog(Context context, Spannable contentText, String cancelText, String confirmText, boolean cancelable,
                                  OnSweetClickListener cancelClick, OnSweetClickListener confirmClick) {
        if (!activityIsRunning(context)) {
            return;
        }
        if (TextUtils.isEmpty(cancelText)) {
            cancelText = context.getString(cancelResId);
        }

        if (TextUtils.isEmpty(confirmText)) {
            confirmText = context.getString(confirmResId);
        }
        SweetAlertDialog dialog = new SweetAlertDialog(context)
                .setContentTextGray(Gravity.LEFT | Gravity.FILL_HORIZONTAL)
                .setContentText(contentText)
                .setCancelText(cancelText)
                .setCancelTextColor(textColor)
                .setCancelClickListener(cancelClick)
                .setConfirmText(confirmText)
                .setConfirmTextColor(textColor)
                .setConfirmClickListener(confirmClick);
        dialog.setCancelable(cancelable);
        dialog.show();
    }

    /**
     * @param context
     *         上下文
     * @param contentText
     *         内容
     * @param cancelText
     *         取消文本
     * @param confirmText
     *         确定文本
     * @param cancelClick
     *         取消事件
     * @param confirmClick
     *         确定事件
     */
    public static void showDialog(Context context, Spannable contentText, String cancelText, String confirmText,
                                  OnSweetClickListener cancelClick, OnSweetClickListener confirmClick) {
        showDialog(context, contentText, cancelText, confirmText, true, cancelClick, confirmClick);
    }

    /**
     * @param context
     *         上下文
     * @param contentText
     *         内容
     * @param cancelText
     *         取消文本
     * @param confirmText
     *         确定文本
     * @param cancelClick
     *         取消事件
     * @param confirmClick
     *         确定事件
     */
    public static void showDialog(Context context, String contentText, String cancelText, String confirmText,
                                  OnSweetClickListener cancelClick, OnSweetClickListener confirmClick) {
        showDialog(context, contentText, cancelText, confirmText, true, cancelClick, confirmClick);
    }

    /**
     * @param context
     *         上下文
     * @param contentText
     *         内容
     * @param confirmText
     *         确定文本
     * @param isShowCancel
     *         是否显示取消按钮
     * @param confirmClick
     *         确定事件
     */
    public static void showDialog(Context context, String contentText, String confirmText, boolean isShowCancel, OnSweetClickListener confirmClick) {
        if (!activityIsRunning(context)) {
            return;
        }
        if (TextUtils.isEmpty(confirmText)) {
            confirmText = context.getString(confirmResId);
        }
        SweetAlertDialog dialog = new SweetAlertDialog(context)
                .setContentText(contentText)
                .setConfirmText(confirmText)
                .setConfirmTextColor(textColor)
                .setConfirmClickListener(confirmClick)
                .showCancelButton(isShowCancel);
        dialog.setCancelable(true);
        dialog.show();
    }

    /**
     * 单按钮
     *
     * @param context
     *         上下文
     * @param contentText
     *         内容
     * @param confirmText
     *         确定文本
     * @param confirmClick
     *         确定事件
     */
    public static void showDialog(Context context, String contentText, String confirmText, OnSweetClickListener confirmClick) {
        if (!activityIsRunning(context)) {
            return;
        }
        if (TextUtils.isEmpty(confirmText)) {
            confirmText = context.getString(confirmResId);
        }
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertType.NORMAL_TYPE)
                .setContentText(contentText)
                .setConfirmText(confirmText)
                .setConfirmTextColor(textColor)
                .setConfirmClickListener(confirmClick)
                .showCancelButton(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 单按钮
     *
     * @param context
     *         上下文
     * @param titleText
     *         标题
     * @param contentText
     *         内容
     * @param confirmText
     *         确定文本
     * @param confirmClick
     *         确定事件
     */
    public static void showDialog(Context context, String titleText, String contentText, String confirmText, OnSweetClickListener confirmClick) {
        if (!activityIsRunning(context)) {
            return;
        }
        if (TextUtils.isEmpty(confirmText)) {
            confirmText = context.getString(confirmResId);
        }
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertType.NORMAL_TYPE)
                .setTitleText(titleText)
                .setContentText(contentText)
                .setConfirmText(confirmText)
                .setConfirmTextColor(textColor)
                .setConfirmClickListener(confirmClick)
                .showCancelButton(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 资源文本
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param context
     *         上下文
     * @param contentText
     *         内容资源ID
     * @param cancelText
     *         取消文本资源ID
     * @param confirmText
     *         确定文本资源ID
     * @param cancelClick
     *         取消事件
     * @param confirmClick
     *         确定事件
     * @param cancelable
     *         返回键是否可取消
     */
    public static void showDialog(Context context, @StringRes int contentText, @StringRes int cancelText, @StringRes int confirmText, boolean cancelable,
                                  OnSweetClickListener cancelClick, OnSweetClickListener confirmClick) {
        showDialog(context, context.getString(contentText), context.getString(cancelText), context.getString(confirmText), cancelable,
                cancelClick, confirmClick);
    }

    /**
     * @param context
     *         上下文
     * @param contentText
     *         内容资源ID
     * @param cancelText
     *         取消文本资源ID
     * @param confirmText
     *         确定文本资源ID
     * @param cancelClick
     *         取消事件
     * @param confirmClick
     *         确定事件
     */
    public static void showDialog(Context context, @StringRes int contentText, @StringRes int cancelText, @StringRes int confirmText,
                                  OnSweetClickListener cancelClick, OnSweetClickListener confirmClick) {
        showDialog(context, contentText, cancelText, confirmText, true, cancelClick, confirmClick);
    }

    /**
     * @param context
     *         上下文
     * @param contentText
     *         内容资源ID
     * @param confirmText
     *         确定文本资源ID
     * @param isShowCancel
     *         是否显示取消按钮
     * @param confirmClick
     *         确定事件
     */
    public static void showDialog(Context context, @StringRes int contentText, @StringRes int confirmText, boolean isShowCancel,
                                  OnSweetClickListener confirmClick) {
        showDialog(context, context.getString(contentText), context.getString(confirmText), isShowCancel, confirmClick);
    }

    /**
     * 单按钮
     *
     * @param context
     *         上下文
     * @param contentText
     *         内容资源ID
     * @param confirmText
     *         确定文本资源ID
     * @param confirmClick
     *         确定事件
     */
    public static void showDialog(Context context, @StringRes int contentText, @StringRes int confirmText, OnSweetClickListener confirmClick) {
        showDialog(context, context.getString(contentText), context.getString(confirmText), confirmClick);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 特殊样式
    ///////////////////////////////////////////////////////////////////////////
    public static void showEditDialog(Context context, SweetAlertType type, String contentText, String confirmText, OnSweetClickListener confirmClick) {
        if (!activityIsRunning(context)) {
            return;
        }
        new SweetAlertDialog(context, type)
                .setContentText(contentText)
                .setConfirmText(confirmText)
                .setConfirmTextColor(textColor)
                .setConfirmClickListener(confirmClick)
                .showCancelButton(true)
                .show();
    }

    /**
     * Activity是否可用
     */
    private static boolean activityIsRunning(Context context) {
        return !(context instanceof Activity) || !((Activity) context).isFinishing();
    }
}