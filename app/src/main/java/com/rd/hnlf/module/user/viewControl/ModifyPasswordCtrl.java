package com.rd.hnlf.module.user.viewControl;

import android.view.View;

import com.rd.hnlf.R;
import com.rd.hnlf.module.user.dataModel.submit.ModifyPasswordSub;
import com.rd.hnlf.module.user.ui.activity.ModifyPasswordAct;
import com.rd.hnlf.module.user.viewModel.ModifyPasswordVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.UserService;
import com.rd.hnlf.utils.InputCheck;
import com.rd.hnlf.utils.LoadingDialogUtils;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.ActivityManage;
import com.rd.tools.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 14:42
 * <p/>
 * Description: {@link ModifyPasswordAct}
 */
public class ModifyPasswordCtrl {
    private ModifyPasswordVM viewModel;

    public ModifyPasswordCtrl() {
        viewModel = new ModifyPasswordVM();
    }

    /**
     * 保存点击
     */
    public void submitClick(View view) {
        if (!InputCheck.checkPassword(viewModel.getOldPassword()) || !InputCheck.checkPassword(viewModel.getNewPassword())) {
            ToastUtil.toast(R.string.validate_pwd);
        } else {
            doSave(view);
        }
    }

    /**
     * 请求修改密码接口
     */
    private void doSave(View view) {
        ModifyPasswordSub sub = new ModifyPasswordSub();
        sub.setPassword(viewModel.getOldPassword());
        sub.setNewPassword(viewModel.getNewPassword());

        Call<HttpResult> call = RDClient.getService(UserService.class).modifyPassword(sub);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(view.getContext())) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                ToastUtil.toast(response.body().getMsg());
                ActivityManage.finish();
            }
        });
    }

    public ModifyPasswordVM getViewModel() {
        return viewModel;
    }
}