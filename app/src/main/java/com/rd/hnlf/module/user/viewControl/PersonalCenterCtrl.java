package com.rd.hnlf.module.user.viewControl;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.module.user.ui.activity.PersonalCenterAct;
import com.rd.hnlf.module.user.viewModel.PersonalCenterVM;
import com.rd.hnlf.router.RouterUrl;
import com.rd.logic.info.SharedInfo;
import com.rd.tools.utils.AndroidUtil;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/18 14:43
 * <p/>
 * Description: {@link PersonalCenterAct}
 */
public class PersonalCenterCtrl {
    private PersonalCenterVM viewModel;

    public PersonalCenterCtrl() {
        viewModel = new PersonalCenterVM();
    }

    /**
     * 关闭页面 ICON 点击
     */
    public void closeClick(View view) {
        AndroidUtil.getActivity(view).onBackPressed();
    }

    /**
     * 我是买家点击
     */
    public void buyerClick(View view) {
        if (viewModel.isNormal()) {
            ARouter.getInstance().build(RouterUrl.E_COMMERCE_BUYER_NORMAL).navigation();
        } else if (viewModel.isVIP()) {
            ARouter.getInstance().build(RouterUrl.TRADE_BUYER_VIP).navigation();
        }
    }

    /**
     * 我是卖家点击
     */
    public void sellerClick(View view) {
        ARouter.getInstance().build(RouterUrl.TRADE_SELLER).navigation();
    }

    /**
     * 历史票据点击
     */
    public void historyNoteClick(View view) {
        ARouter.getInstance().build(RouterUrl.PURE_HISTORY_NOTE).navigation();
    }

    /**
     * 票据交易点击
     */
    public void noteDealingClick(View view) {
        ARouter.getInstance().build(RouterUrl.TRADE_NOTE_TRANSACTION).navigation();
    }

    /**
     * 电商订单点击
     */
    public void ECommerceOrderClick(View view) {
        ARouter.getInstance().build(RouterUrl.E_COMMERCE_ORDER).navigation();
    }

    /**
     * 纯票订单点击
     */
    public void pureNoteOrderClick(View view) {
        ARouter.getInstance().build(RouterUrl.PURE_PURE_ORDER).navigation();
    }

    /**
     * 我的票据点击
     */
    public void myNoteClick(View view) {
        ARouter.getInstance().build(RouterUrl.PURE_MY_NOTE).navigation();
    }

    /**
     * 账户信息点击
     */
    public void accountInfoClick(View view) {
        if (SharedInfo.getInstance().getEntity(OauthTokenRec.class).isVIP()) {
            ARouter.getInstance().build(RouterUrl.USER_ACCOUNT_INFO_VIP).navigation();
        } else {
            ARouter.getInstance().build(RouterUrl.USER_ACCOUNT_INFO).navigation();
        }
    }

    /**
     * 安全中心点击
     */
    public void securityCenterClick(View view) {
        ARouter.getInstance().build(RouterUrl.USER_SECURITY_CENTER).navigation();
    }

    public PersonalCenterVM getViewModel() {
        return viewModel;
    }
}