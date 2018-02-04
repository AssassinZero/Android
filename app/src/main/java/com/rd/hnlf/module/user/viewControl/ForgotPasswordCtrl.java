package com.rd.hnlf.module.user.viewControl;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.module.user.ui.activity.ForgotPasswordAct;
import com.rd.hnlf.module.user.viewModel.ForgotPasswordVM;
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
 * Date: 2017/9/25 14:09
 * <p/>
 * Description: {@link ForgotPasswordAct}
 */
public class ForgotPasswordCtrl {
    private ForgotPasswordVM viewModel;

    public ForgotPasswordCtrl() {
        viewModel = new ForgotPasswordVM();
    }

    public void nextClick(View view) {
        if (!RegularUtil.isPhone(viewModel.getPhone())) {
            ToastUtil.toast(R.string.validate_phone);
        } else if (!InputCheck.checkCode(viewModel.getCode())) {
            ToastUtil.toast(R.string.validate_code);
        } else {
            doNext(view);
        }
    }

    /**
     * 验证手机号是否存在
     */
    private void doNext(final View view) {
        Call<HttpResult> call = RDClient.getService(UserService.class).checkMobile(viewModel.getPhone(), viewModel.getCode());
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                ARouter.getInstance().build(RouterUrl.USER_RESET_PASSWORD)
                        .withString(BundleKeys.ID, viewModel.getPhone())
                        .withString(BundleKeys.CODE, viewModel.getCode())
                        .navigation(AndroidUtil.getActivity(view), BaseActivity.REQUEST_CODE_CLOSED);
            }
        });
    }

    public ForgotPasswordVM getViewModel() {
        return viewModel;
    }
}