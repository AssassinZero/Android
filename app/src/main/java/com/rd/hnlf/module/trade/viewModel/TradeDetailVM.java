package com.rd.hnlf.module.trade.viewModel;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.module.common.viewModel.bean.BillToPartyInfo;
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.common.viewModel.bean.OrderInfo;
import com.rd.hnlf.module.common.viewModel.bean.QuotationInfo;
import com.rd.hnlf.module.logic.ButtonOperateVM;
import com.rd.hnlf.module.trade.viewControl.TradeDetailCtrl;
import com.rd.hnlf.module.trade.viewModel.bean.BearerInfo;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.StringFormat;

import java.util.ArrayList;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 14:46
 * <p/>
 * Description: {@link TradeDetailCtrl}
 */
public class TradeDetailVM extends ButtonOperateVM {
    /** 票面信息 */
    private ArrayList<NoteInfo> noteInfo;
    /** 报价信息 */
    private QuotationInfo       quotationInfo;
    /** 收票方信息 */
    private BillToPartyInfo     billToPartyInfo;
    /** 持票方信息 */
    private BearerInfo          bearerInfo;
    /** 订单信息 */
    private OrderInfo           orderInfo;
    /** 结算金额 */
    private String              settlementAmount;

    @Bindable
    public ArrayList<NoteInfo> getNoteInfo() {
        return noteInfo;
    }

    public void setNoteInfo(ArrayList<NoteInfo> noteInfo) {
        this.noteInfo = noteInfo;
        notifyPropertyChanged(BR.noteInfo);
    }

    @Bindable
    public QuotationInfo getQuotationInfo() {
        return quotationInfo;
    }

    public void setQuotationInfo(QuotationInfo quotationInfo) {
        this.quotationInfo = quotationInfo;
        notifyPropertyChanged(BR.quotationInfo);
    }

    @Bindable
    public BillToPartyInfo getBillToPartyInfo() {
        return billToPartyInfo;
    }

    public void setBillToPartyInfo(BillToPartyInfo billToPartyInfo) {
        this.billToPartyInfo = billToPartyInfo;
        notifyPropertyChanged(BR.billToPartyInfo);
    }

    @Bindable
    public BearerInfo getBearerInfo() {
        return bearerInfo;
    }

    public void setBearerInfo(BearerInfo bearerInfo) {
        this.bearerInfo = bearerInfo;
        notifyPropertyChanged(BR.bearerInfo);
    }

    @Bindable
    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        notifyPropertyChanged(BR.orderInfo);
    }

    @Bindable
    public String getSettlementAmount() {
        if (!TextUtils.isEmpty(getButton2())) {
            return ContextHolder.getContext().getString(R.string.settlement_amount2, StringFormat.doubleFormat(settlementAmount));
        } else {
            return ContextHolder.getContext().getString(R.string.settlement_amount1, StringFormat.doubleFormat(settlementAmount));
        }
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
        notifyPropertyChanged(BR.settlementAmount);
    }
}