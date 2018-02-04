package com.rd.hnlf.module.pure.viewModel;

import android.databinding.ObservableBoolean;

import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.common.viewModel.bean.NoteInfo;
import com.rd.hnlf.module.pure.viewControl.NoteListCtrl;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/22 15:00
 * <p/>
 * Description: {@link NoteListCtrl}
 */
public class NoteVM extends NoteInfo {
    /** 票据ID */
    private String            noteId;
    /** 是否选中 */
    public  ObservableBoolean select;
    /** 是否显示下架、修改按钮 */
    private boolean           visible;

    /**
     * 持有中 - 70,已上架 - 71,历史订单 - 72
     */
    public NoteVM(String type) {
        super(Constant.NUMBER_2);
        visible = Constant.NOTE_PUT_ON.equals(type);
        select = new ObservableBoolean(false);
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public ObservableBoolean getSelect() {
        return select;
    }

    public void setSelect(ObservableBoolean select) {
        this.select = select;
    }

    public void setSelect() {
        select.set(!select.get());
    }

    public boolean isVisible() {
        return visible && isTransferState();
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}