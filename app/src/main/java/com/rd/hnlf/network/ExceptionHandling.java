package com.rd.hnlf.network;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.hnlf.module.user.UserLogic;
import com.rd.hnlf.router.RouterUrl;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.ActivityManage;
import com.rd.tools.utils.ToastUtil;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/5/30 11:53
 * <p/>
 * Description: 异常处理
 */
@SuppressWarnings("unchecked")
final class ExceptionHandling {
    static void operate(final HttpResult result) {
        int code = result.getCode();
        if (code == AppResultCode.NOT_TOAST) {
            return;
        }
        switch (code) {
            case AppResultCode.TOKEN_NOT_LOGIN:
            case AppResultCode.TOKEN_OTHER:
                UserLogic.signOut(ActivityManage.peek());
                ARouter.getInstance().build(RouterUrl.USER_LOGIN).navigation();
                break;

            default:
                break;
        }
        ToastUtil.toastNow(result.getMsg());
    }
}