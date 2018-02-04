package com.rd.hnlf.module.pure.viewModel;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.module.common.viewModel.bean.BillToPartyInfo;
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.common.viewModel.bean.QuotationInfo;
import com.rd.hnlf.module.logic.ButtonOperateVM;
import com.rd.hnlf.module.pure.viewControl.ModifyNoteCtrl;
import com.rd.hnlf.module.pure.viewControl.NotePutOnCtrl;
import com.rd.hnlf.module.pure.viewControl.PureDetailCtrl;
import com.rd.hnlf.module.pure.viewControl.PureTransactionCtrl;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.StringFormat;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 14:46
 * <p/>
 * Description: {@link PureDetailCtrl}{@link PureTransactionCtrl}{@link NotePutOnCtrl}{@link ModifyNoteCtrl}
 */
public class PureDetailVM extends ButtonOperateVM {
    /** 票面信息 */
    private List<NoteInfo>  noteInfo;
    /** 报价信息 */
    private QuotationInfo   quotationInfo;
    /** 收票方信息 */
    private BillToPartyInfo billToPartyInfo;
    /** 结算金额 */
    private String          settlementAmount;
    /** 票据状态 */
    private String          transactionState;
    /** 原结算金额 */
    private String          originalMoney;

    @Bindable
    public List<NoteInfo> getNoteInfo() {
        return noteInfo;
    }

    public void setNoteInfo(List<NoteInfo> noteInfo) {
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
    public boolean getTransactionState() {
        return "10".equals(transactionState);
    }

    public void setTransactionState(String transactionState) {
        this.transactionState = transactionState;
        notifyPropertyChanged(BR.transactionState);
    }

    @Bindable
    public String getSettlementAmount() {
        if (TextUtils.isEmpty(settlementAmount)) {
            return ContextHolder.getContext().getString(R.string.settlement_amount3);
        } else if (!TextUtils.isEmpty(getButton2()))
            return ContextHolder.getContext().getString(R.string.settlement_amount2, StringFormat.doubleFormat(settlementAmount));
        else {
            return ContextHolder.getContext().getString(R.string.settlement_amount1, StringFormat.doubleFormat(settlementAmount));
        }
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
        notifyPropertyChanged(BR.settlementAmount);
    }

    @Bindable
    public String getOriginalMoney() {
        return TextUtils.isEmpty(originalMoney) ? originalMoney : StringFormat.doubleFormat(originalMoney);
    }

    public void setOriginalMoney(String originalMoney) {
        this.originalMoney = originalMoney;
        notifyPropertyChanged(BR.originalMoney);
    }
}