package com.rd.hnlf.module.user.viewControl;

import android.app.Activity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.hnlf.R;
import com.rd.hnlf.module.user.UserLogic;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.module.user.dataModel.submit.RegisterSub;
import com.rd.hnlf.module.user.ui.activity.RegisterAct;
import com.rd.hnlf.module.user.viewModel.RegisterVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.UserService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.hnlf.utils.InputCheck;
import com.rd.hnlf.utils.LoadingDialogUtils;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.RegularUtil;
import com.rd.tools.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/17 16:23
 * <p/>
 * Description: {@link RegisterAct}
 */
public class RegisterCtrl {
    private RegisterVM viewModel;

    public RegisterCtrl() {
        viewModel = new RegisterVM();
    }

    /**
     * 注册按钮点击
     */
    public void submitClick(View view) {
        if (!RegularUtil.isPhone(viewModel.getPhone())) {
            ToastUtil.toast(R.string.validate_phone);
        } else if (!InputCheck.checkCode(viewModel.getCode())) {
            ToastUtil.toast(R.string.validate_code);
        } else if (!InputCheck.checkPassword(viewModel.getPassword())) {
            ToastUtil.toast(R.string.validate_pwd);
        } else {
            doRegister(view);
        }
    }

    /**
     * 发送注册请求
     */
    private void doRegister(final View view) {
        RegisterSub sub = new RegisterSub();
        sub.setMobile(viewModel.getPhone());
        sub.setCode(viewModel.getCode());
        sub.setPassword(viewModel.getPassword());
        sub.setType(viewModel.getRole());

        Call<HttpResult<OauthTokenRec>> call = RDClient.getService(UserService.class).register(sub);
        call.enqueue(new RequestCallBack<HttpResult<OauthTokenRec>>(new LoadingDialogUtils().show(view.getContext())) {
            @Override
            public void onSuccess(Call<HttpResult<OauthTokenRec>> call, Response<HttpResult<OauthTokenRec>> response) {
                Activity activity = AndroidUtil.getActivity(view);
                activity.setResult(Activity.RESULT_OK);
                UserLogic.login(activity, response.body().getData());
                ARouter.getInstance().build(RouterUrl.USER_REGISTER_SUCCEED).navigation();
            }
        });
    }

    /**
     * 注册协议点击
     */
    public void protocolClick(View view) {
        // ARouter.getInstance().build(RouterUrl.WEB_VIEW)
        //         .withString(BundleKeys.TITLE, "")
        //         .withString(BundleKeys.URL, CommonService.REGISTER_PROTOCOL)
        //         .withString(BundleKeys.POST_DATA, "")
        //         .navigation();
    }

    public RegisterVM getViewModel() {
        return viewModel;
    }
}