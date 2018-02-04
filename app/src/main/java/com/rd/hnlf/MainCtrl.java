package com.rd.hnlf;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.common.ui.AbsRefreshAndLoadMore;
import com.rd.hnlf.common.ui.BaseListControl;
import com.rd.hnlf.module.common.dataModel.receive.StatisticsRec;
import com.rd.hnlf.module.user.UserLogic;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.CommonService;
import com.rd.hnlf.network.api.UserService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.logic.info.SharedInfo;
import com.rd.network.entity.HttpResult;

import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/16 14:51
 * <p/>
 * Description: {@link MainAct}
 */
public class MainCtrl extends BaseListControl {
    private MainVM    viewModel;
    // 顺时针旋转
    private Animation CWAnimation;
    // 逆时针旋转
    private Animation CCWAnimation;

    public MainCtrl() {
        viewModel = new MainVM();
        setListener(new AbsRefreshAndLoadMore() {
            @Override
            public void refreshInit(PtrFrameLayout ptrFrame) {
            }

            @Override
            public void loadMoreInit(BaseQuickAdapter adapter) {
            }

            @Override
            public void onRequest() {
                reqData();
            }
        });
        reqData();
    }

    /**
     * 请求接口，获取 "累计交易金额" 和 "累计交易笔数"
     */
    private void reqData() {
        Call<HttpResult<StatisticsRec>> call = RDClient.getService(CommonService.class).getStatisticsInfo();
        call.enqueue(new RequestCallBack<HttpResult<StatisticsRec>>(getListener(), null) {
            @Override
            public void onSuccess(Call<HttpResult<StatisticsRec>> call, Response<HttpResult<StatisticsRec>> response) {
                getListener().complete();
                StatisticsRec rec = response.body().getData();
                viewModel.setAmount(rec.getTotalAmount());
                viewModel.setTimes(rec.getTotalCount());
            }
        });
    }

    /**
     * 票据商城点击
     */
    public void mallClick(View view) {
        ARouter.getInstance().build(RouterUrl.E_COMMERCE_NOTE_MALL).navigation();
    }

    /**
     * 个人中心点击
     */
    public void personalClick(View view) {
        OauthTokenRec rec = SharedInfo.getInstance().getEntity(OauthTokenRec.class);
        if (null != rec) {
            Call<HttpResult<OauthTokenRec>> call = RDClient.getService(UserService.class).refreshToken(rec.getRefreshToken());
            call.enqueue(new RequestCallBack<HttpResult<OauthTokenRec>>() {
                @Override
                public void onSuccess(Call<HttpResult<OauthTokenRec>> call, Response<HttpResult<OauthTokenRec>> response) {
                    SharedInfo.getInstance().saveEntity(response.body().getData());
                    ARouter.getInstance().build(RouterUrl.USER_PERSONAL_CENTER).navigation();
                }
            });
        } else {
            ARouter.getInstance().build(RouterUrl.USER_LOGIN).navigation();
        }
    }

    /**
     * 更多点击
     */
    public void moreClick(View moreView, View popupView) {
        // 需要登录
        if (UserLogic.isLand()) {
            OauthTokenRec rec = SharedInfo.getInstance().getEntity(OauthTokenRec.class);
            if (rec.isVIP()) {
                // 会员用户 允许买票 和 卖票
                Context context = moreView.getContext();
                if (moreView.getTag() == context.getString(R.string.tag_on)) {
                    showPopupWindow(moreView, popupView);
                } else {
                    hidePopupWindow(moreView, popupView);
                }
            } else if (rec.isAgent()) {
                // 代理商 只能卖票
                ARouter.getInstance().build(RouterUrl.TRADE_NOTE_INFO).withString(BundleKeys.TYPE, Constant.STATUS__1).navigation();
            } else {
                // 普通用户 只能卖票
                ARouter.getInstance().build(RouterUrl.TRADE_NOTE_INFO).withString(BundleKeys.TYPE, Constant.STATUS_1).navigation();
            }
        } else {
            ARouter.getInstance().build(RouterUrl.USER_LOGIN).navigation();
        }
    }

    /**
     * 弹出窗点击
     */
    public void popupWindowClick(View popupView, View moreView) {
        hidePopupWindow(moreView, popupView);
    }

    /**
     * 我要买票点击
     */
    public void noteBuyClick(View view, View moreView, View popupView) {
        hidePopupWindow(moreView, popupView);
        ARouter.getInstance().build(RouterUrl.TRADE_NOTE_INFO).withString(BundleKeys.TYPE, Constant.STATUS_0).navigation();
    }

    /**
     * 我要卖票点击
     */
    public void noteSellClick(View view, View moreView, View popupView) {
        hidePopupWindow(moreView, popupView);
        ARouter.getInstance().build(RouterUrl.TRADE_NOTE_INFO).withString(BundleKeys.TYPE, Constant.STATUS_1).navigation();
    }

    /**
     * ICON 顺时针旋转并"显示"弹出窗
     */
    private void showPopupWindow(View moreView, View popupView) {
        if (CWAnimation == null) {
            CWAnimation = new RotateAnimation(0, 45, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            // 保持旋转后的状态
            CWAnimation.setFillAfter(true);
            // 动画持续时间
            CWAnimation.setDuration(100);
            // 默认为0，为-1时一直循环动画
            CWAnimation.setRepeatCount(0);
            // 添加匀速加速器
            CWAnimation.setInterpolator(new LinearInterpolator());
        }
        moreView.setTag(moreView.getContext().getString(R.string.tag_off));
        moreView.startAnimation(CWAnimation);
        popupView.setVisibility(View.VISIBLE);
        getListener().setRefreshEnable(false);
    }

    /**
     * ICON 逆时针旋转并"关闭"弹出窗
     */
    private void hidePopupWindow(View moreView, View popupView) {
        if (CCWAnimation == null) {
            CCWAnimation = new RotateAnimation(45, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            // 保持旋转后的状态
            CCWAnimation.setFillAfter(true);
            // 动画持续时间
            CCWAnimation.setDuration(100);
            // 默认为0，为-1时一直循环动画
            CCWAnimation.setRepeatCount(0);
            // 添加匀速加速器
            CCWAnimation.setInterpolator(new LinearInterpolator());
        }
        moreView.setTag(moreView.getContext().getString(R.string.tag_on));
        moreView.startAnimation(CCWAnimation);
        popupView.setVisibility(View.GONE);
        getListener().setRefreshEnable(true);
    }

    public MainVM getViewModel() {
        return viewModel;
    }
}