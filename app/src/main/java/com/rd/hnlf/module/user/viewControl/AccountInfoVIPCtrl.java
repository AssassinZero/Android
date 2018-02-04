package com.rd.hnlf.module.user.viewControl;

import android.text.TextUtils;
import android.view.View;

import com.rd.hnlf.R;
import com.rd.hnlf.module.user.dataModel.receive.AccountInfoVIPRec;
import com.rd.hnlf.module.user.dataModel.submit.AccountInfoVIPSub;
import com.rd.hnlf.module.user.ui.activity.AccountInfoVIPAct;
import com.rd.hnlf.module.user.viewModel.AccountInfoVIPVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.UserService;
import com.rd.hnlf.utils.LoadingDialogUtils;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.ActivityManage;
import com.rd.tools.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/6 11:09
 * <p/>
 * Description: {@link AccountInfoVIPAct}
 */
public class AccountInfoVIPCtrl {
    private AccountInfoVIPVM viewModel;

    public AccountInfoVIPCtrl() {
        viewModel = new AccountInfoVIPVM();
        reqData();
    }

    private void reqData() {
        Call<HttpResult<AccountInfoVIPRec>> call = RDClient.getService(UserService.class).getAccountInfoVIP();
        call.enqueue(new RequestCallBack<HttpResult<AccountInfoVIPRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<AccountInfoVIPRec>> call, Response<HttpResult<AccountInfoVIPRec>> response) {
                convert(response.body().getData());
            }
        });
    }

    /**
     * 数据类型转换
     */
    private void convert(AccountInfoVIPRec rec) {
        viewModel.setCompanyName(rec.getEnterpriseName());
        viewModel.setSocialCreditCode(rec.getSocialCreditCode());
        viewModel.setCompanyAddress(rec.getContactAddress());
        viewModel.setPhone(rec.getOfficePhone());
        viewModel.setName(rec.getLegalPersonName());
        viewModel.setIDCard(rec.getCorporateIdentityCard());
    }

    /**
     * 保存点击
     */
    public void submitClick(View view) {
        if (TextUtils.isEmpty(viewModel.getCompanyName())) {
            ToastUtil.toast(R.string.account_info_company_name_hint);
            if (TextUtils.isEmpty(viewModel.getSocialCreditCode())) {
                ToastUtil.toast(R.string.account_info_id_no_hint);
            } else if (TextUtils.isEmpty(viewModel.getCompanyAddress())) {
                ToastUtil.toast(R.string.account_info_company_address_hint);
            } else if (TextUtils.isEmpty(viewModel.getPhone())) {
                ToastUtil.toast(R.string.account_info_office_phone_hint);
            } else if (TextUtils.isEmpty(viewModel.getName())) {
                ToastUtil.toast(R.string.account_info_legal_person_name_hint);
            } else if (TextUtils.isEmpty(viewModel.getIDCard())) {
                ToastUtil.toast(R.string.account_info_id_card_hint);
            } else {
                doSave(view);
            }
        }
    }

    /**
     * 发送保存请求
     */
    private void doSave(View view) {
        AccountInfoVIPSub sub = new AccountInfoVIPSub();
        sub.setEnterpriseName(viewModel.getCompanyName());
        sub.setSocialCreditCode(viewModel.getSocialCreditCode());
        sub.setContactAddress(viewModel.getCompanyAddress());
        sub.setOfficePhone(viewModel.getPhone());
        sub.setLegalPersonName(viewModel.getName());
        sub.setCorporateIdentityCard(viewModel.getIDCard());
        Call<HttpResult> call = RDClient.getService(UserService.class).saveOrUpdateAccountInfoVIP(sub);
        call.enqueue(new RequestCallBack<HttpResult>(new LoadingDialogUtils().show(view.getContext())) {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                ToastUtil.toast(response.body().getMsg());
                ActivityManage.finish();
            }
        });
    }

    public AccountInfoVIPVM getViewModel() {
        return viewModel;
    }
}