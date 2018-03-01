package com.rd.hnlf.module.eCommerce.viewControl;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.basic.BaseActivity;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.common.dataModel.receive.OrderBillRec;
import com.rd.hnlf.module.common.dataModel.submit.JsonSub;
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.eCommerce.dataModel.receive.NotePurchaseRec;
import com.rd.hnlf.module.eCommerce.dataModel.submit.BillSurfaceSub;
import com.rd.hnlf.module.eCommerce.dataModel.submit.NotePurchaseSub;
import com.rd.hnlf.module.eCommerce.ui.activity.NotePurchaseAct;
import com.rd.hnlf.module.eCommerce.viewModel.NotePurchaseVM;
import com.rd.hnlf.module.eCommerce.viewModel.bean.PaymentInfo;
import com.rd.hnlf.module.trade.viewModel.bean.EndorseeEditInfo;
import com.rd.hnlf.module.user.dataModel.receive.BankcardRec;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.module.user.viewModel.BankcardVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.ECommerceService;
import com.rd.hnlf.router.RouterUrl;
import com.rd.logic.info.SharedInfo;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.ActivityManage;
import com.rd.tools.utils.AndroidUtil;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/2 14:50
 * <p/>
 * Description: {@link NotePurchaseAct}
 */
public class NotePurchaseCtrl {
    private NotePurchaseVM viewModel;

    public NotePurchaseCtrl(String ids) {
        viewModel = new NotePurchaseVM();
        reqData(ids);
    }

    /**
     * 网络请求
     */
    private void reqData(String ids) {
        String billNos = "[";
        for (String s : ids.split(",")) {
            billNos += "\"" + s + "\",";
        }
        billNos = billNos.substring(0, billNos.length() - 1) + "]";
        String                            mobile = SharedInfo.getInstance().getEntity(OauthTokenRec.class).getMobile();
        Call<HttpResult<NotePurchaseRec>> call   = RDClient.getService(ECommerceService.class).getPurchaseDetail(billNos, mobile);
        call.enqueue(new RequestCallBack<HttpResult<NotePurchaseRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<NotePurchaseRec>> call, Response<HttpResult<NotePurchaseRec>> response) {
                converter(response.body().getData());
            }
        });
    }

    /**
     * 数据类型转换
     */
    private void converter(NotePurchaseRec rec) {
        // 票面信息
        ArrayList<NoteInfo> noteInfo = new ArrayList<>();
        for (OrderBillRec billRec : rec.getOrderBills()) {
            NoteInfo info = new NoteInfo(Constant.NUMBER_0);
            info.setId(billRec.getBillNo());
            info.setAmount(billRec.getBillAmount());
            info.setDays(billRec.getAdjustDays());
            info.setDueDate(billRec.getDueDateStr());
            info.setApr(billRec.getYearRate());
            info.setDiscount(billRec.getDiscount());
            info.setType(billRec.getType());
            info.setProperty(billRec.getBillAttribute());
            info.setAmountPayable(billRec.getOriginalMoney());
            noteInfo.add(info);
        }
        viewModel.setNoteInfo(noteInfo);

        // 被背书人信息
        EndorseeEditInfo endorseeInfo = new EndorseeEditInfo();
        if (TextUtils.isEmpty(rec.getEnterpriseNameOwn())) {
            endorseeInfo.setCompanyName(ContextHolder.getContext().getString(R.string.not_available));
        } else {
            endorseeInfo.setCompanyName(rec.getEnterpriseNameOwn());
        }

        HashMap<String, BankcardVM> bankcardMap = new HashMap<>();
        for (BankcardRec bankcardRec : rec.getEndorseeInfo()) {
            BankcardVM vm = new BankcardVM();
            vm.setBankcard(bankcardRec.getBankAccount());
            vm.setAccountName(bankcardRec.getAccountName());
            vm.setBankNo(bankcardRec.getBankNo());
            vm.setBranchName(bankcardRec.getOpeningBank());
            vm.setBranchNo(bankcardRec.getBankCode());
            bankcardMap.put(vm.getBankcard(), vm);
        }
        endorseeInfo.setBankcardMap(bankcardMap);
        viewModel.setEndorseeInfo(endorseeInfo);

        // 支付信息
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setMobile(rec.getPayMobile());
        paymentInfo.setCompanyName(rec.getEnterpriseName());
        paymentInfo.setBankcard(rec.getBankAccount());
        paymentInfo.setAccountName(rec.getAccountName());
        paymentInfo.setBranchName(rec.getOpeningBank());
        paymentInfo.setBranchNo(rec.getBankNumber());
        viewModel.setPaymentInfo(paymentInfo);
        // 结算金额
        viewModel.setSettlementAmount(rec.getParseDouble());
    }

    /**
     * 新增背书账户
     */
    public void addNoteClick(View view) {
        ARouter.getInstance().build(RouterUrl.USER_BANKCARD_EDIT)
                .withInt(BundleKeys.POSITION, Constant.NUMBER__2)
                .navigation(AndroidUtil.getActivity(view), BaseActivity.REQUEST_CODE);
    }

    /**
     * 新增背书账户回调
     */
    public void addNote(BankcardVM info) {
        viewModel.getEndorseeInfo().add(info);
    }

    /**
     * 提交订单点击
     */
    public void submitClick(View view) {
        if (TextUtils.isEmpty(viewModel.getEndorseeInfo().getBankcard())) {
            ToastUtil.toast(R.string.note_endorsee_error);
        } else if (TextUtils.isEmpty(viewModel.getPaymentInfo().getFee())) {
            ToastUtil.toast(R.string.quotation_info_edit_fee_hint);
        } else {
            doSubmit(view);
        }
    }

    /**
     * 提交操作
     */
    private void doSubmit(View view) {
            //
        System.out.println(viewModel.getPaymentInfo().getMobile());
            //
        NotePurchaseSub sub = new NotePurchaseSub();
        sub.setBankAccount(viewModel.getEndorseeInfo().getBankcard());
        sub.setAccountNameOwn(viewModel.getEndorseeInfo().getAccountName());
        sub.setBankCode(viewModel.getEndorseeInfo().getBankNo());
        sub.setOpeningBankOwn(viewModel.getEndorseeInfo().getBranchName());
        sub.setBankNumberOwn(viewModel.getEndorseeInfo().getBranchNo());


        List<BillSurfaceSub> billSurfaceList = new ArrayList<>();
        for (NoteInfo info : viewModel.getNoteInfo()) {
            BillSurfaceSub surfaceSub = new BillSurfaceSub();
            surfaceSub.setAdjustDays(info.getDays());
            surfaceSub.setBillNo(info.getId());
            surfaceSub.setOriginalMoney(info.getAmountPayable());
            billSurfaceList.add(surfaceSub);
        }
        sub.setBillSurfaceList(billSurfaceList);
        sub.setMobile(SharedInfo.getInstance().getEntity(OauthTokenRec.class).getMobile());
        sub.setServiceCharge(viewModel.getPaymentInfo().getFee());
        sub.setSettlementAmount(viewModel.getSettlementAmount());
        sub.setPayMobile(viewModel.getPaymentInfo().getMobile());
        sub.setPayBankAccount(viewModel.getPaymentInfo().getBankcard());
            //
        Call<HttpResult> call = RDClient.getService(ECommerceService.class).placeAnOrder(new JsonSub().setFormData(sub));
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                Log.e("onSuccess",response.body().getMsg());
                //跳转至交易成功界面
                ARouter.getInstance().build(RouterUrl.TRADE_SUCCESSFULLY)
                        .navigation();

                ToastUtil.toast(response.body().getMsg());
                ContextHolder.getContext().sendBroadcast(new Intent(BundleKeys.REFRESH_LIST));
                ActivityManage.finish();
            }
        });
    }

    public NotePurchaseVM getViewModel() {
        return viewModel;
    }
}