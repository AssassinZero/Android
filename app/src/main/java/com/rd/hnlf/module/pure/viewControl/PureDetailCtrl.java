package com.rd.hnlf.module.pure.viewControl;

import android.view.View;

import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.common.dataModel.receive.OrderBillRec;
import com.rd.hnlf.module.common.viewModel.bean.BillToPartyInfo;
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.common.viewModel.bean.QuotationInfo;
import com.rd.hnlf.module.logic.ButtonOperateLogic;
import com.rd.hnlf.module.pure.dataModel.receive.PureDetailRec;
import com.rd.hnlf.module.pure.ui.activity.PureDetailAct;
import com.rd.hnlf.module.pure.viewModel.PureDetailVM;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.PureService;
import com.rd.network.entity.HttpResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 14:46
 * <p/>
 * Description: {@link PureDetailAct}
 */
public class PureDetailCtrl {
    private PureDetailVM viewModel;
    private String       orderId;

    public PureDetailCtrl(String orderId) {
        viewModel = new PureDetailVM();
        this.orderId = orderId;
        reqData();
    }

    /**
     * 网络请求
     */
    public void reqData() {
        Call<HttpResult<PureDetailRec>> call = RDClient.getService(PureService.class).getPureDetailInfo(orderId);
        call.enqueue(new RequestCallBack<HttpResult<PureDetailRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<PureDetailRec>> call, Response<HttpResult<PureDetailRec>> response) {
                PureDetailRec rec = response.body().getData();
                // 票面信息
                ArrayList<NoteInfo> noteInfo = new ArrayList<>();
                for (OrderBillRec billRec : rec.getBills()) {
                    NoteInfo info = new NoteInfo(Constant.NUMBER_2);
                    info.setId(billRec.getBillNo());
                    info.setAmount(billRec.getBillAmount());
                    info.setDays(billRec.getAdjustDays());
                    info.setDueDate(billRec.getDueDateStr());
                    info.setType(billRec.getType());
                    info.setProperty(billRec.getBillAttribute());
                    noteInfo.add(info);
                }
                viewModel.setNoteInfo(noteInfo);
                // 报价信息
                QuotationInfo quotationInfo = new QuotationInfo();
                quotationInfo.setQuotationMethod(rec.getQuotationMethod());
                quotationInfo.setDiscount(rec.getDiscount());
                quotationInfo.setApr(rec.getYearRate());
                quotationInfo.setFee(rec.getServiceFee());
                viewModel.setQuotationInfo(quotationInfo);
                // 收票方信息
                BillToPartyInfo billToPartyInfo = new BillToPartyInfo();
                billToPartyInfo.setBankcard(rec.getAnalogueBankAccount());
                billToPartyInfo.setAccountName(rec.getAnalogueAccountName());
                billToPartyInfo.setBranchName(rec.getAnalogueOpeningBank());
                billToPartyInfo.setBranchNo(rec.getAnalogueBankNumber());
                viewModel.setBillToPartyInfo(billToPartyInfo);
                // 结算金额
                viewModel.setSettlementAmount(rec.getSettlementAmount());
                // 按钮
                if (rec.isReview()) {
                    viewModel.setButton1("复核");
                    viewModel.setOperate1(ButtonOperateLogic.PURE_REVIEW_ORDER);
                } else {
                    viewModel.reset();
                }
            }
        });
    }

    /**
     * 按钮1 点击
     */
    public void button1Click(View view) {
        ButtonOperateLogic.getInstance().execute(view.getContext(), viewModel.getOperate1(), orderId, "");
    }

    /**
     * 按钮2 点击
     */
    public void button2Click(View view) {
        ButtonOperateLogic.getInstance().execute(view.getContext(), viewModel.getOperate1(), orderId, "");
    }

    public PureDetailVM getViewModel() {
        return viewModel;
    }
}