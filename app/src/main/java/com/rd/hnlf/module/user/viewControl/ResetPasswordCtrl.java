package com.rd.hnlf.module.user.viewControl;

import android.app.Activity;
import android.view.View;

import com.rd.hnlf.R;
import com.rd.hnlf.module.user.dataModel.submit.ModifyPasswordSub;
import com.rd.hnlf.module.user.ui.activity.ResetPasswordAct;
import com.rd.hnlf.module.user.viewModel.ResetPasswordVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.UserService;
import com.rd.hnlf.utils.InputCheck;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/25 14:18
 * <p/>
 * Description: {@link ResetPasswordAct}
 */
public class ResetPasswordCtrl {
    private ResetPasswordVM viewModel;
    private String          phone;
    private String          code;

    public ResetPasswordCtrl(String phone, String code) {
        viewModel = new ResetPasswordVM();
        this.phone = phone;
        this.code = code;
    }

    /**
     * 保存
     */
    public void submitClick(View view) {
        if (!InputCheck.checkPassword(viewModel.getNewPassword())) {
            ToastUtil.toast(R.string.validate_pwd);
        } else if (!viewModel.getNewPassword().equals(viewModel.getConfirmPassword())) {
            ToastUtil.toast(R.string.validate_pwd_different);
        } else {
            doSubmit(view);
        }
    }

    /**
     * 提交数据
     */
    private void doSubmit(final View view) {
        ModifyPasswordSub sub = new ModifyPasswordSub();
        sub.setMobile(phone);
        sub.setCode(code);
        sub.setPassword(viewModel.getNewPassword());

        Call<HttpResult> call = RDClient.getService(UserService.class).resetPassword(sub);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                ToastUtil.toast(response.body().getMsg());
                Activity activity = AndroidUtil.getActivity(view);
                activity.setResult(Activity.RESULT_OK);
                activity.finish();
            }
        });
    }

    public ResetPasswordVM getViewModel() {
        return viewModel;
    }
}