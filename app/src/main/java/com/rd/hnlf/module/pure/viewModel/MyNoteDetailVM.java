package com.rd.hnlf.module.pure.viewModel;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.module.logic.ButtonOperateVM;
import com.rd.hnlf.module.pure.viewControl.MyNoteDetailCtrl;
import com.rd.hnlf.module.pure.viewModel.bean.BasicInfo;
import com.rd.hnlf.module.pure.viewModel.bean.NegativeInfo;
import com.rd.hnlf.module.pure.viewModel.bean.TraderInfo;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.StringFormat;

import java.util.ArrayList;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/22 16:49
 * <p/>
 * Description: {@link MyNoteDetailCtrl}
 */
public class MyNoteDetailVM extends ButtonOperateVM {
    /** 基本信息 */
    private BasicInfo               basicInfo;
    /** 出票人信息 */
    private TraderInfo              drawerInfo;
    /** 收款人信息 */
    private TraderInfo              payeeInfo;
    /** 承兑人信息 */
    private TraderInfo              acceptorInfo;
    /** 票据背面信息 */
    private ArrayList<NegativeInfo> negativeInfo;
    /** 结算金额 */
    private String                  settlementAmount;

    public MyNoteDetailVM() {
        basicInfo = new BasicInfo();
        drawerInfo = new TraderInfo();
        payeeInfo = new TraderInfo();
        acceptorInfo = new TraderInfo();
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public TraderInfo getDrawerInfo() {
        return drawerInfo;
    }

    public void setDrawerInfo(TraderInfo drawerInfo) {
        this.drawerInfo = drawerInfo;
    }

    public TraderInfo getPayeeInfo() {
        return payeeInfo;
    }

    public void setPayeeInfo(TraderInfo payeeInfo) {
        this.payeeInfo = payeeInfo;
    }

    public TraderInfo getAcceptorInfo() {
        return acceptorInfo;
    }

    public void setAcceptorInfo(TraderInfo acceptorInfo) {
        this.acceptorInfo = acceptorInfo;
    }

    @Bindable
    public ArrayList<NegativeInfo> getNegativeInfo() {
        return negativeInfo;
    }

    public void setNegativeInfo(ArrayList<NegativeInfo> negativeInfo) {
        this.negativeInfo = negativeInfo;
        notifyPropertyChanged(BR.negativeInfo);
    }

    public String getSettlementAmount() {
        if (!TextUtils.isEmpty(getButton2())) {
            return ContextHolder.getContext().getString(R.string.settlement_amount2, StringFormat.doubleFormat(settlementAmount));
        } else {
            return ContextHolder.getContext().getString(R.string.settlement_amount1, StringFormat.doubleFormat(settlementAmount));
        }
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }
}