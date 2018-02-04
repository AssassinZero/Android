package com.rd.hnlf.module.trade.viewModel;

import com.rd.hnlf.module.trade.viewControl.NoteAccountCtrl;
import com.rd.hnlf.module.trade.viewModel.bean.NoteAccountInfo;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/29 15:58
 * <p/>
 * Description: {@link NoteAccountCtrl}
 */
public class NoteAccountVM {
    /** 收票方信息 */
    private NoteAccountInfo receiverInfo;
    /** 持票方信息 */
    private NoteAccountInfo holderInfo;

    public NoteAccountVM() {
        receiverInfo = new NoteAccountInfo();
        holderInfo = new NoteAccountInfo();
    }

    public NoteAccountInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(NoteAccountInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public NoteAccountInfo getHolderInfo() {
        return holderInfo;
    }

    public void setHolderInfo(NoteAccountInfo holderInfo) {
        this.holderInfo = holderInfo;
    }
}