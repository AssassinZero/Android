package com.rd.hnlf.module.eCommerce.viewControl;

import android.view.View;

import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.common.dataModel.receive.OrderBillRec;
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.common.viewModel.bean.OrderInfo;
import com.rd.hnlf.module.eCommerce.dataModel.receive.ECommerceDetailRec;
import com.rd.hnlf.module.eCommerce.ui.activity.ECommerceDetailAct;
import com.rd.hnlf.module.eCommerce.viewModel.ECommerceDetailVM;
import com.rd.hnlf.module.eCommerce.viewModel.bean.EndorseeInfo;
import com.rd.hnlf.module.eCommerce.viewModel.bean.PaymentInfo;
import com.rd.hnlf.module.logic.ButtonOperateLogic;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.ECommerceService;
import com.rd.network.entity.HttpResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/24 17:28
 * <p/>
 * Description: {@link ECommerceDetailAct}
 */
public class ECommerceDetailCtrl {
    private ECommerceDetailVM viewModel;
    private String            orderNo;
    private String            type;

    public ECommerceDetailCtrl(String orderNo, String type) {
        viewModel = new ECommerceDetailVM();
        this.orderNo = orderNo;
        this.type = type;
        reqData();
    }

    /**
     * ·
     * 网络请求
     */
    private void reqData() {
        Call<HttpResult<ECommerceDetailRec>> call = RDClient.getService(ECommerceService.class).getECommerceDetailInfo(orderNo);
        call.enqueue(new RequestCallBack<HttpResult<ECommerceDetailRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<ECommerceDetailRec>> call, Response<HttpResult<ECommerceDetailRec>> response) {
                converter(response.body().getData());
            }
        });
    }

    /**
     * 数据类型转换
     */
    private void converter(ECommerceDetailRec rec) {
        // 票面信息
        ArrayList<NoteInfo> noteInfo = new ArrayList<>();
        for (OrderBillRec billRec : rec.getBizOnlineOrderBills()) {
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
        // 被背书账户信息
        EndorseeInfo endorseeInfo = new EndorseeInfo();
        endorseeInfo.setCompanyName(rec.getEnterpriseNameEndorsed());
        endorseeInfo.setBankcard(rec.getBankAccountEndorsed());
        endorseeInfo.setAccountName(rec.getAccountNameEndorsed());
        endorseeInfo.setBranchName(rec.getSubbarnchBankNameEndorsed());
        endorseeInfo.setBranchNo(rec.getSubbranchBankCodeEndorsed());
        viewModel.setEndorseeInfo(endorseeInfo);
        // 支付信息
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCompanyName(rec.getEnterpriseNameTicket());
        paymentInfo.setBankcard(rec.getBankAccountTicket());
        paymentInfo.setAccountName(rec.getAccountNameTicket());
        paymentInfo.setBranchName(rec.getSubbarnchBankNameTicket());
        paymentInfo.setBranchNo(rec.getSubbranchBankCodeTicket());
        paymentInfo.setFee(rec.getServiceFee());
        viewModel.setPaymentInfo(paymentInfo);
        // 订单信息
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(rec.getOrderNo());
        orderInfo.setTime(rec.getCreateTime());
        orderInfo.setCompleteTime(rec.getOrderFinishTime());
        orderInfo.setStatus(rec.getBusinessStateName());
        viewModel.setOrderInfo(orderInfo);
        // 结算金额
        viewModel.setSettlementAmount(rec.getSettlementAmount());
        // 按钮
        ButtonOperateLogic.getInstance().initButtons(rec, viewModel, type);
    }

    /**
     * 按钮1 点击
     */
    public void button1Click(View view) {
        ButtonOperateLogic.getInstance().execute(view.getContext(), viewModel.getOperate1(), orderNo, type);
    }

    /**
     * 按钮2 点击
     */
    public void button2Click(View view) {
        ButtonOperateLogic.getInstance().execute(view.getContext(), viewModel.getOperate2(), orderNo, type);
    }

    public ECommerceDetailVM getViewModel() {
        return viewModel;
    }
}