package com.rd.hnlf.module.user.viewControl;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.module.user.UserLogic;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.module.user.dataModel.submit.LoginSub;
import com.rd.hnlf.module.user.ui.activity.LoginAct;
import com.rd.hnlf.module.user.viewModel.LoginVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.UserService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.hnlf.utils.InputCheck;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.RegularUtil;
import com.rd.tools.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/17 9:59
 * <p/>
 * Description:{@link LoginAct}
 */
public class LoginCtrl {
    private LoginVM viewModel;

    public LoginCtrl() {
        viewModel = new LoginVM();
    }

    /**
     * 关闭页面 ICON 点击
     */
    public void closeClick(View view) {
        AndroidUtil.getActivity(view).onBackPressed();
    }

    /**
     * 登录按钮点击
     */
    public void submitClick(View view) {
        if (!RegularUtil.isPhone(viewModel.getPhone())) {
            ToastUtil.toast(R.string.validate_phone);
        } else if (!viewModel.getMode() && (!InputCheck.checkPassword(viewModel.getPassword())) && !InputCheck.checkLength(viewModel.getPassword(), 6)) {
            ToastUtil.toast(R.string.validate_pwd);
        } else if (viewModel.getMode() && !InputCheck.checkCode(viewModel.getCode())) {
            ToastUtil.toast(R.string.validate_code);
        } else {
            doLogin(view);
        }
    }

    /**
     * 发送登录请求
     */
    private void doLogin(final View view) {
        LoginSub sub = new LoginSub();
        sub.setMobile(viewModel.getPhone());
        sub.setPassword(viewModel.getPassword());
        sub.setType(viewModel.getMode());
        sub.setCode(viewModel.getCode());

        Call<HttpResult<OauthTokenRec>> call = RDClient.getService(UserService.class).login(sub);
        call.enqueue(new RequestCallBack<HttpResult<OauthTokenRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<OauthTokenRec>> call, Response<HttpResult<OauthTokenRec>> response) {
                UserLogic.login(AndroidUtil.getActivity(view), response.body().getData());
            }
        });
    }

    /**
     * 忘记密码点击
     */
    public void forgotClick(View view) {
        ARouter.getInstance().build(RouterUrl.USER_FORGOT_PASSWORD).navigation();
    }

    /**
     * 手机验证码登录点击
     */
    public void enterClick(View view) {
        viewModel.setMode();
    }

    /**
     * 注册新用户点击
     */
    public void toRegisterClick(View view) {
        ARouter.getInstance().build(RouterUrl.USER_REGISTER).navigation(AndroidUtil.getActivity(view), BaseActivity.REQUEST_CODE_CLOSED);
    }

    public LoginVM getViewModel() {
        return viewModel;
    }
}