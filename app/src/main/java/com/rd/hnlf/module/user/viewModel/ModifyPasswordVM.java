package com.rd.hnlf.module.user.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.hnlf.BR;
import com.rd.hnlf.module.user.viewControl.ModifyPasswordCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 14:42
 * <p/>
 * Description: {@link ModifyPasswordCtrl}
 */
public class ModifyPasswordVM extends BaseObservable {
    /** 旧密码 */
    private String oldPassword;
    /** 新密码 */
    private String newPassword;

    @Bindable
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        notifyPropertyChanged(BR.oldPassword);
    }

    @Bindable
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        notifyPropertyChanged(BR.newPassword);
    }
}