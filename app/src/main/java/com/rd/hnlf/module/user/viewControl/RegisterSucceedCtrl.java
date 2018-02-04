package com.rd.hnlf.module.user.viewControl;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.hnlf.module.user.ui.activity.RegisterSucceedAct;
import com.rd.hnlf.router.RouterUrl;
import com.rd.tools.utils.ActivityManage;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/18 10:11
 * <p/>
 * Description: {@link RegisterSucceedAct}
 */
public class RegisterSucceedCtrl {
    /**
     * 前往票据商城
     */
    public void toShoppingClick(View view) {
        ARouter.getInstance().build(RouterUrl.E_COMMERCE_NOTE_MALL).navigation();
        ActivityManage.finish();
    }

    /**
     * 前往个人中心
     */
    public void toPersonalClick(View view) {
        ARouter.getInstance().build(RouterUrl.USER_PERSONAL_CENTER).navigation();
        ActivityManage.finish();
    }
}