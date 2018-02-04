package com.rd.hnlf.module.user;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.hnlf.MainAct;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.router.RouterUrl;
import com.rd.hnlf.utils.DialogUtils;
import com.rd.logic.GestureLogic;
import com.rd.logic.info.SharedInfo;
import com.rd.tools.utils.ContextHolder;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/6/13 18:02
 * <p/>
 * Description:
 */
public class UserLogic {
    /**
     * 是否已经登录
     */
    public static boolean isLand() {
        boolean       isLand   = (boolean) SharedInfo.getInstance().getValue(Constant.IS_LAND, false);
        OauthTokenRec tokenRec = SharedInfo.getInstance().getEntity(OauthTokenRec.class);
        // 是否已经登录
        return isLand && null != tokenRec;
    }

    /**
     * 用户登录
     */
    public static void login(Activity activity, OauthTokenRec oauthTokenMo) {
        // 登录逻辑处理
        SharedInfo.getInstance().saveValue(Constant.IS_LAND, true);
        SharedInfo.getInstance().saveEntity(oauthTokenMo);

        ContextHolder.getContext().sendBroadcast(new Intent(BundleKeys.LOGIN_STATUS_CHANGED));

        activity.finish();
    }

    /**
     * 用户登出
     */
    public static void signOut(Activity activity) {
        // 标记未登录
        SharedInfo.getInstance().remove(Constant.IS_LAND);
        // 删除保存的 OauthToken 信息
        SharedInfo.getInstance().remove(OauthTokenRec.class);
        // 清楚缓存的手势密码信息
        GestureLogic.getInstance().clean();

        ContextHolder.getContext().sendBroadcast(new Intent(BundleKeys.LOGIN_STATUS_CHANGED));

        ARouter.getInstance().build(RouterUrl.MAIN).navigation();
        if (!(activity instanceof MainAct)) {
            activity.finish();
        }
    }

    /**
     * 用户主动登出(到登录界面)
     */
    public static void initiativeSignOut(final Activity activity) {
        DialogUtils.showDialog(activity, R.string.login_out, R.string.dialog_confirm, true, new OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog dialog) {
                dialog.dismiss();
                signOut(activity);
                ARouter.getInstance().build(RouterUrl.USER_LOGIN).navigation();
            }
        });
    }
}