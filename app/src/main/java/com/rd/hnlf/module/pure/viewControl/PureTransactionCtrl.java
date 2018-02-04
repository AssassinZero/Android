package com.rd.hnlf.module.pure.viewControl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.common.dataModel.submit.JsonSub;
import com.rd.hnlf.module.common.viewModel.bean.BillToPartyInfo;
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.common.viewModel.bean.QuotationInfo;
import com.rd.hnlf.module.pure.dataModel.submit.MyNoteSub;
import com.rd.hnlf.module.pure.dataModel.submit.NoteSub;
import com.rd.hnlf.module.pure.dataModel.submit.PureTransactionSub;
import com.rd.hnlf.module.pure.ui.activity.PureTransactionAct;
import com.rd.hnlf.module.pure.viewModel.PureDetailVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.PureService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.hnlf.utils.InputCheck;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.ActivityManage;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.ConverterUtil;
import com.rd.tools.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/28 15:31
 * <p/>
 * Description: {@link PureTransactionAct}
 */
public class PureTransactionCtrl {
    private PureDetailVM       viewModel;
    private PureTransactionSub sub;

    public PureTransactionCtrl(ArrayList<NoteInfo> list) {
        viewModel = new PureDetailVM();
        sub = new PureTransactionSub();
        viewModel.setNoteInfo(list);
        viewModel.setQuotationInfo(new QuotationInfo());
        viewModel.setBillToPartyInfo(new BillToPartyInfo());
    }

    /**
     * 选择开户银行
     */
    public void chooseBankClick(View view) {
        ARouter.getInstance().build(RouterUrl.USER_BANK_CHOOSE)
                .navigation(AndroidUtil.getActivity(view), Constant.CHOOSE_BANK);
    }

    /**
     * 选择银行支行
     */
    public void chooseBranchBankClick(View view) {
        if (TextUtils.isEmpty(viewModel.getBillToPartyInfo().getBankNo())) {
            // 为空，则去选择开户银行
            ARouter.getInstance().build(RouterUrl.USER_BANK_CHOOSE)
                    .navigation(AndroidUtil.getActivity(view), Constant.CHOOSE_BANK);
        } else {
            // 不为空，则去选择银行支行
            ARouter.getInstance().build(RouterUrl.USER_BANK_CHOOSE)
                    .withString(BundleKeys.CODE, viewModel.getBillToPartyInfo().getBankNo())
                    .navigation(AndroidUtil.getActivity(view), Constant.CHOOSE_BRANCH);
        }
    }

    /**
     * 发起交易点击
     */
    public void submitClick(View view) {
        Context context = view.getContext();
        // if (viewModel.getQuotationInfo().isAprMode() && TextUtils.isEmpty(viewModel.getQuotationInfo().getOriginApr())) {
        //     ToastUtil.toast(R.string.quotation_info_edit_apr_hint);
        // } else if (viewModel.getQuotationInfo().isDiscountMode() && TextUtils.isEmpty(viewModel.getQuotationInfo().getOriginDiscount())) {
        //     ToastUtil.toast(R.string.quotation_info_edit_discount_hint);
        // } else if (TextUtils.isEmpty(viewModel.getQuotationInfo().getOriginFee())) {
        //     ToastUtil.toast(R.string.quotation_info_edit_fee_hint);
        // } else
        if (TextUtils.isEmpty(viewModel.getBillToPartyInfo().getBankcard())) {
            ToastUtil.toast(R.string.bankcard_bankcard_hint);
        } else if (!InputCheck.checkBankcard(viewModel.getBillToPartyInfo().getBankcard())) {
            ToastUtil.toast(R.string.validate_bank_card);
        } else if (TextUtils.isEmpty(viewModel.getBillToPartyInfo().getAccountName())) {
            ToastUtil.toast(R.string.bankcard_account_name_hint);
        } else if (TextUtils.isEmpty(viewModel.getBillToPartyInfo().getBankName())) {
            ToastUtil.toast(context.getString(R.string.validate_please_choose, context.getString(R.string.bankcard_bank_name)));
        } else if (TextUtils.isEmpty(viewModel.getBillToPartyInfo().getBranchName())) {
            ToastUtil.toast(context.getString(R.string.validate_please_choose, context.getString(R.string.bankcard_branch_name)));
        } else {
            doSubmit(view);
        }
    }

    /**
     * 发起交易
     */
    private void doSubmit(View view) {
        // 票面信息
        List<NoteSub> bills = new ArrayList<>();
        for (NoteInfo info : viewModel.getNoteInfo()) {
            NoteSub noteSub = new MyNoteSub();
            noteSub.setBillNo(info.getId());
            noteSub.setAdjustDays(info.getDays());
            bills.add(noteSub);
        }
        sub.setBills(bills);
        // 报价信息
        sub.setQuotationMethod(viewModel.getQuotationInfo().getQuotationMethod());
        sub.setYearRate(ConverterUtil.getDouble(viewModel.getQuotationInfo().getOriginApr()) / 100 + "");
        sub.setDiscount(viewModel.getQuotationInfo().getOriginDiscount());
        sub.setServiceFee(viewModel.getQuotationInfo().getOriginFee());
        // 收票方信息
        sub.setAnalogueBankAccount(viewModel.getBillToPartyInfo().getBankcard());
        sub.setAnalogueAccountName(viewModel.getBillToPartyInfo().getAccountName());
        sub.setAnalogueBankCode(viewModel.getBillToPartyInfo().getBankNo());
        sub.setAnalogueOpeningBank(viewModel.getBillToPartyInfo().getBranchName());
        sub.setAnalogueBankNumber(viewModel.getBillToPartyInfo().getBranchNo());

        Call<HttpResult> call = RDClient.getService(PureService.class).doPureTransaction(new JsonSub().setTradeInfo(sub));
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                ToastUtil.toast(response.body().getMsg());
                ActivityManage.finish();
                ContextHolder.getContext().sendBroadcast(new Intent(BundleKeys.REFRESH_LIST));
            }
        });
    }

    public PureDetailVM getViewModel() {
        return viewModel;
    }
}