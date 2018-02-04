package com.rd.hnlf.common.ui;

import android.support.annotation.StringRes;
import android.view.View;

import com.rd.tools.utils.ContextHolder;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/14 15:39
 * <p/>
 * Description:
 */
public class BaseListControlWithButton extends BaseListControl {
    /** 白色按钮文字 */
    private String whiteButton;
    /** 紫色按钮文字 */
    private String purpleButton;

    public String getWhiteButton() {
        return whiteButton;
    }

    public void setWhiteButton(String whiteButton) {
        this.whiteButton = whiteButton;
    }

    public void setWhiteButton(@StringRes int whiteButton) {
        this.whiteButton = ContextHolder.getContext().getString(whiteButton);
    }

    public String getPurpleButton() {
        return purpleButton;
    }

    public void setPurpleButton(String purpleButton) {
        this.purpleButton = purpleButton;
    }

    public void setPurpleButton(@StringRes int purpleButton) {
        this.purpleButton = ContextHolder.getContext().getString(purpleButton);
    }

    public void whiteButtonClick(View view) {
    }

    public void purpleButtonClick(View view) {
    }
}