package com.rd.hnlf.module.user.viewControl;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.module.user.UserLogic;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.module.user.dataModel.receive.SecurityCenterInitRec;
import com.rd.hnlf.module.user.ui.activity.SecurityCenterAct;
import com.rd.hnlf.module.user.viewModel.SecurityCenterVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.UserService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.logic.info.SharedInfo;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.ContextHolder;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/18 18:10
 * <p/>
 * Description: {@link SecurityCenterAct}
 */
public class SecurityCenterCtrl {
    private SecurityCenterVM viewModel;

    public SecurityCenterCtrl() {
        viewModel = new SecurityCenterVM();
        viewModel.setBankAccountVisible(SharedInfo.getInstance().getEntity(OauthTokenRec.class).isAgent());
        reqData();
    }

    /**
     * 网络请求
     */
    public void reqData() {
        Call<HttpResult<SecurityCenterInitRec>> call = RDClient.getService(UserService.class).securityCenterInit();
        call.enqueue(new RequestCallBack<HttpResult<SecurityCenterInitRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<SecurityCenterInitRec>> call, Response<HttpResult<SecurityCenterInitRec>> response) {
                SecurityCenterInitRec rec = response.body().getData();
                viewModel.setPhone(rec.getVerifyMobile());
                viewModel.setBankcardCount(ContextHolder.getContext().getString(R.string.piece, rec.getBankCount()));
            }
        });
    }

    /**
     * 手机绑定点击
     */
    public void bindPhoneClick(View view) {
        ARouter.getInstance().build(RouterUrl.USER_MODIFY_PHONE).withString(BundleKeys.ID, viewModel.getPhone()).navigation();
    }

    /**
     * 修改密码点击
     */
    public void modifyPasswordClick(View view) {
        ARouter.getInstance().build(RouterUrl.USER_MODIFY_PASSWORD).navigation();
    }

    /**
     * 我的银行账户点击
     */
    public void myBankAccountClick(View view) {
        ARouter.getInstance().build(RouterUrl.USER_BANKCARD_LIST).navigation();
    }

    /**
     * 退出登录点击
     */
    public void loginOutClick(View view) {
        UserLogic.initiativeSignOut(AndroidUtil.getActivity(view));
    }

    public SecurityCenterVM getViewModel() {
        return viewModel;
    }
}