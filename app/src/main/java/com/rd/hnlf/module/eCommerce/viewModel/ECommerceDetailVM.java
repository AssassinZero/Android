package com.rd.hnlf.module.eCommerce.viewModel;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.common.viewModel.bean.OrderInfo;
import com.rd.hnlf.module.eCommerce.viewControl.ECommerceDetailCtrl;
import com.rd.hnlf.module.eCommerce.viewModel.bean.EndorseeInfo;
import com.rd.hnlf.module.eCommerce.viewModel.bean.PaymentInfo;
import com.rd.hnlf.module.logic.ButtonOperateVM;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.StringFormat;

import java.util.ArrayList;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/24 17:30
 * <p/>
 * Description: {@link ECommerceDetailCtrl}
 */
public class ECommerceDetailVM extends ButtonOperateVM {
    /** 票面信息 */
    private ArrayList<NoteInfo> noteInfo;
    /** 被背书账户信息 */
    private EndorseeInfo        endorseeInfo;
    /** 支付信息 */
    private PaymentInfo         paymentInfo;
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
    public EndorseeInfo getEndorseeInfo() {
        return endorseeInfo;
    }

    public void setEndorseeInfo(EndorseeInfo endorseeInfo) {
        this.endorseeInfo = endorseeInfo;
        notifyPropertyChanged(BR.endorseeInfo);
    }

    @Bindable
    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
        notifyPropertyChanged(BR.paymentInfo);
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