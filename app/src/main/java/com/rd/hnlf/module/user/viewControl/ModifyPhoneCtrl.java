package com.rd.hnlf.module.user.viewControl;

import android.content.Intent;
import android.view.View;

import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.module.user.dataModel.submit.ModifyPhoneSub;
import com.rd.hnlf.module.user.ui.activity.ModifyPhoneAct;
import com.rd.hnlf.module.user.viewModel.ModifyPhoneVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.UserService;
import com.rd.hnlf.utils.InputCheck;
import com.rd.hnlf.utils.LoadingDialogUtils;
import com.rd.logic.info.SharedInfo;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.ActivityManage;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.RegularUtil;
import com.rd.tools.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 10:22
 * <p/>
 * Description: {@link ModifyPhoneAct}
 */
public class ModifyPhoneCtrl {
    private ModifyPhoneVM viewModel;

    public ModifyPhoneCtrl(String phone) {
        viewModel = new ModifyPhoneVM();
        viewModel.setOldPhone(phone);
    }

    /**
     * 保存点击
     */
    public void submitClick(View view) {
        if (!InputCheck.checkPassword(viewModel.getPassword())) {
            ToastUtil.toast(R.string.validate_pwd);
        } else if (!RegularUtil.isPhone(viewModel.getNewPhone())) {
            ToastUtil.toast(R.string.validate_phone);
        } else if (!InputCheck.checkCode(viewModel.getCode())) {
            ToastUtil.toast(R.string.validate_code);
        } else {
            doSubmit(view);
        }
    }

    /**
     * 发送修改手机号请求
     */
    private void doSubmit(final View view) {
        ModifyPhoneSub sub = new ModifyPhoneSub();
        sub.setMobile(viewModel.getOldPhone());
        sub.setPassword(viewModel.getPassword());
        sub.setNewMobile(viewModel.getNewPhone());
        sub.setCode(viewModel.getCode());

        Call<HttpResult> call = RDClient.getService(UserService.class).modifyPhone(sub);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(view.getContext())) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                ToastUtil.toast(response.body().getMsg());
                SharedInfo.getInstance().getEntity(OauthTokenRec.class).setMobile(viewModel.getNewPhone());
                ContextHolder.getContext().sendBroadcast(new Intent(BundleKeys.REFRESH_LIST));
                ActivityManage.finish();
            }
        });
    }

    public ModifyPhoneVM getViewModel() {
        return viewModel;
    }
}