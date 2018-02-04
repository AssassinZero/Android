package com.rd.hnlf.module.eCommerce.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.eCommerce.viewControl.NotePurchaseCtrl;
import com.rd.hnlf.module.eCommerce.viewModel.bean.PaymentInfo;
import com.rd.hnlf.module.trade.viewModel.bean.EndorseeEditInfo;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.StringFormat;

import java.util.ArrayList;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/2 14:50
 * <p/>
 * Description: {@link NotePurchaseCtrl}
 */
public class NotePurchaseVM extends BaseObservable {
    /** 票面信息 */
    private ArrayList<NoteInfo> noteInfo;
    /** 被背书账户信息 */
    private EndorseeEditInfo    endorseeInfo;
    /** 支付信息 */
    private PaymentInfo         paymentInfo;
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
    public EndorseeEditInfo getEndorseeInfo() {
        return endorseeInfo;
    }

    public void setEndorseeInfo(EndorseeEditInfo endorseeInfo) {
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

    public String getSettlementAmount() {
        return settlementAmount;
    }

    @Bindable
    public String getSettlementAmountStr() {
        return ContextHolder.getContext().getString(R.string.settlement_amount1, StringFormat.doubleFormat(settlementAmount));
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
        notifyPropertyChanged(BR.settlementAmountStr);
    }
}