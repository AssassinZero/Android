package com.rd.hnlf.module.trade.viewControl;

import android.view.View;

import com.rd.hnlf.R;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.common.dataModel.receive.OrderBillRec;
import com.rd.hnlf.module.common.viewModel.bean.BillToPartyInfo;
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.common.viewModel.bean.OrderInfo;
import com.rd.hnlf.module.common.viewModel.bean.QuotationInfo;
import com.rd.hnlf.module.logic.ButtonOperateLogic;
import com.rd.hnlf.module.trade.dataModel.receive.TradeDetailRec;
import com.rd.hnlf.module.trade.ui.activity.TradeDetailAct;
import com.rd.hnlf.module.trade.viewModel.TradeDetailVM;
import com.rd.hnlf.module.trade.viewModel.bean.BearerInfo;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.TradeService;
import com.rd.logic.info.SharedInfo;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.ContextHolder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 14:46
 * <p/>
 * Description: {@link TradeDetailAct}
 */
public class TradeDetailCtrl {
    private TradeDetailVM viewModel;
    private String        orderId;
    private String        type;

    public TradeDetailCtrl(String orderId, String type) {
        viewModel = new TradeDetailVM();
        this.orderId = orderId;
        this.type = type;
        reqData();
    }

    /**
     * 网络请求
     */
    public void reqData() {
        Call<HttpResult<TradeDetailRec>> call = RDClient.getService(TradeService.class).getTradeDetailInfo(orderId);
        call.enqueue(new RequestCallBack<HttpResult<TradeDetailRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<TradeDetailRec>> call, Response<HttpResult<TradeDetailRec>> response) {
                converter(response.body().getData());
            }
        });
    }

    /**
     * 数据类型转换
     */
    private void converter(TradeDetailRec rec) {
        // 票面信息
        ArrayList<NoteInfo> noteInfo = new ArrayList<>();
        for (OrderBillRec billRec : rec.getOrderBills()) {
            NoteInfo info = new NoteInfo(Constant.NUMBER_1);
            info.setId(billRec.getBillNo());
            info.setAmount(billRec.getBillAmount());
            info.setDays(billRec.getAdjustDays());
            info.setDueDate(billRec.getDueDateStr());
            // 会员-直接交易-我是买家的订单详情  的票面信息 增加点击跳转
            switch (type) {
                case Constant.TRADE_BUYER_ALL:
                case Constant.TRADE_BUYER_HANDLE:
                case Constant.TRADE_BUYER_REVIEW:
                case Constant.TRADE_BUYER_PAYMENT:
                    info.setClickable(true);
                    break;
                default:
                    break;
            }
            noteInfo.add(info);
        }
        viewModel.setNoteInfo(noteInfo);
        // 报价信息
        QuotationInfo quotationInfo = new QuotationInfo();
        quotationInfo.setType(rec.getBillsTypeText());
        quotationInfo.setProperty(rec.getBillsAttributeText());
        quotationInfo.setQuotationMethod(rec.getQuotationMethod());
        quotationInfo.setDiscount(rec.getDiscount());
        quotationInfo.setApr(rec.getYearRate());
        quotationInfo.setFee(rec.getServiceFee());
        viewModel.setQuotationInfo(quotationInfo);
        // 收票方信息
        BillToPartyInfo billToPartyInfo = new BillToPartyInfo();
        billToPartyInfo.setAccount(rec.getCollectorAccountId());
        billToPartyInfo.setBankcard(rec.getCollectingBankAccount());
        billToPartyInfo.setAccountName(rec.getBankName());
        billToPartyInfo.setBranchName(rec.getCollectorOpenBankName());
        billToPartyInfo.setBranchNo(rec.getCollectorBankCode());
        viewModel.setBillToPartyInfo(billToPartyInfo);
        // 持票方信息
        BearerInfo bearerInfo = new BearerInfo();
        bearerInfo.setBankcard(rec.getBankAccount());
        bearerInfo.setAccountName(rec.getHoldAccountName());
        bearerInfo.setBranchName(rec.getHoldAccountSubbranchName());
        bearerInfo.setBranchNo(rec.getHoldAccountSubbranchCode());
        bearerInfo.setSame(rec.getUniformity());
        boolean isAgent = SharedInfo.getInstance().getEntity(OauthTokenRec.class).isAgent();
        if (!isAgent && rec.isMatch()) {
            // 撮合交易 且 非代理商，则显示代理商帐号
            bearerInfo.setName(ContextHolder.getContext().getString(R.string.bearer_info_agent_account));
            bearerInfo.setAccount(rec.getAgentAccount());
        } else {
            bearerInfo.setName(ContextHolder.getContext().getString(R.string.bearer_info_bearer_account));
            bearerInfo.setAccount(rec.getHoldAccountId());
        }
        viewModel.setBearerInfo(bearerInfo);
        // 订单信息
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(rec.getOrderNo());
        orderInfo.setTime(rec.getCreateTime());
        orderInfo.setCompleteTime(rec.getOrderFinishTime());
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
        ButtonOperateLogic.getInstance().execute(view.getContext(), viewModel.getOperate1(), orderId, type);
    }

    /**
     * 按钮2 点击
     */
    public void button2Click(View view) {
        ButtonOperateLogic.getInstance().execute(view.getContext(), viewModel.getOperate2(), orderId, type);
    }

    public TradeDetailVM getViewModel() {
        return viewModel;
    }
}