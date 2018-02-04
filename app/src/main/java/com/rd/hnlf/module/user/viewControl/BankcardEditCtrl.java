package com.rd.hnlf.module.user.viewControl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.common.dataModel.submit.JsonSub;
import com.rd.hnlf.module.user.dataModel.receive.BankcardRec;
import com.rd.hnlf.module.user.dataModel.submit.BankcardSub;
import com.rd.hnlf.module.user.ui.activity.BankcardEditAct;
import com.rd.hnlf.module.user.viewModel.BankcardVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.TradeService;
import com.rd.hnlf.network.api.UserService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.hnlf.utils.InputCheck;
import com.rd.hnlf.utils.LoadingDialogUtils;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 17:49
 * <p/>
 * Description: {@link BankcardEditAct}
 */
public class BankcardEditCtrl {
    /** 在银行卡列表中的 position */
    private int        position;
    /** 在银行卡列表中的 position 对应的对象 */
    private BankcardVM viewModel;
    /** 持票方帐号 - 新增账户 */
    private String     phone;
    /** 是否可编辑 */
    public ObservableBoolean editable = new ObservableBoolean(true);

    public BankcardEditCtrl(int position, BankcardVM viewModel, String phone) {
        this.position = position;
        if (null == viewModel) {
            this.viewModel = new BankcardVM();
        } else {
            this.viewModel = viewModel;
        }
        this.phone = phone;
    }

    /**
     * 收票方银行帐号焦点变更
     */
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            getReceiverInfo();
        }
    }

    /**
     * 获取银行卡信息
     */
    private void getReceiverInfo() {
        if (TextUtils.isEmpty(phone)) {
            return;
        }
        String bankcard = viewModel.getBankcard();
        if (!InputCheck.checkBankcard(bankcard)) {
            ToastUtil.toast(R.string.validate_bank_card);
        } else {
            Call<HttpResult<BankcardRec>> call = RDClient.getService(TradeService.class).getSignBankAccount(phone, bankcard);
            call.enqueue(new RequestCallBack<HttpResult<BankcardRec>>() {
                @Override
                public void onSuccess(Call<HttpResult<BankcardRec>> call, Response<HttpResult<BankcardRec>> response) {
                    editable.set(false);
                    AndroidUtil.closedInputMethod();

                    BankcardRec rec = response.body().getData();
                    viewModel.setAccountName(rec.getAccountName());
                    viewModel.setBankName(rec.getBankName());
                    viewModel.setBankNo(rec.getBankNo());
                    viewModel.setBranchName(rec.getOpeningBank());
                    viewModel.setBranchNo(rec.getBankCode());
                }

                @Override
                public void onFailure(Call<HttpResult<BankcardRec>> call, Throwable t) {
                    super.onFailure(call, t);
                    editable.set(true);
                    AndroidUtil.closedInputMethod();

                    viewModel.setAccountName("");
                    viewModel.setBankName("");
                    viewModel.setBankNo("");
                    viewModel.setBranchName("");
                    viewModel.setBranchNo("");
                }
            });
        }
    }

    /**
     * 选择开户银行
     */
    public void chooseBankClick(View view) {
        if (editable.get()) {
            ARouter.getInstance().build(RouterUrl.USER_BANK_CHOOSE)
                    .navigation(AndroidUtil.getActivity(view), Constant.CHOOSE_BANK);
        }
    }

    /**
     * 选择银行支行
     */
    public void chooseBranchBankClick(View view) {
        if (editable.get()) {
            if (TextUtils.isEmpty(viewModel.getBankNo())) {
                // 为空，则去选择开户银行
                ARouter.getInstance().build(RouterUrl.USER_BANK_CHOOSE)
                        .navigation(AndroidUtil.getActivity(view), Constant.CHOOSE_BANK);
            } else {
                // 不为空，则去选择银行支行
                ARouter.getInstance().build(RouterUrl.USER_BANK_CHOOSE)
                        .withString(BundleKeys.CODE, viewModel.getBankNo())
                        .navigation(AndroidUtil.getActivity(view), Constant.CHOOSE_BRANCH);
            }
        }
    }

    /**
     * 保存点击
     */
    public void submitClick(View view) {
        Context context = view.getContext();
        if (TextUtils.isEmpty(viewModel.getBankcard())) {
            ToastUtil.toast(R.string.bankcard_bankcard_hint);
        } else if (!InputCheck.checkBankcard(viewModel.getBankcard())) {
            ToastUtil.toast(R.string.validate_bank_card);
        } else if (TextUtils.isEmpty(viewModel.getAccountName())) {
            ToastUtil.toast(R.string.bankcard_account_name_hint);
        } else if (TextUtils.isEmpty(viewModel.getBankName())) {
            ToastUtil.toast(context.getString(R.string.validate_please_choose, context.getString(R.string.bankcard_bank_name)));
        } else if (TextUtils.isEmpty(viewModel.getBranchName())) {
            ToastUtil.toast(context.getString(R.string.validate_please_choose, context.getString(R.string.bankcard_branch_name)));
        } else {
            if (position == -2) {
                // 新增持票方账户 - 本地保存返回
                completed(view);
            } else {
                // 新增、修改银行卡 - 提交保存后返回
                doSave(view);
            }
        }
    }

    /**
     * 发送保存银行卡请求
     */
    private void doSave(final View view) {
        BankcardSub sub = new BankcardSub();
        sub.setId(viewModel.getId());
        // 10 - 普通账户，20 - 签约账户
        sub.setType("10");
        sub.setBankAccount(viewModel.getBankcard());
        sub.setAccountName(viewModel.getAccountName());
        sub.setBankCode(viewModel.getBankNo());
        sub.setSubbranchBankName(viewModel.getBranchName());
        sub.setSubbranchBankCode(viewModel.getBranchNo());

        Call<HttpResult> call = RDClient.getService(UserService.class).saveOrUpdateBankcard(new JsonSub().setBankAccount(sub));
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(view.getContext())) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                ToastUtil.toast(response.body().getMsg());
                completed(view);
            }
        });
    }

    /**
     * 新增、修改完成后，更新银行卡列表页数据
     */
    private void completed(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.POSITION, position);
        intent.putExtra(BundleKeys.ITEM, viewModel);
        Activity activity = AndroidUtil.getActivity(view);
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
        ContextHolder.getContext().sendBroadcast(new Intent(BundleKeys.REFRESH_LIST));
    }

    public BankcardVM getViewModel() {
        return viewModel;
    }
}