package com.rd.hnlf.module.trade.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.rd.hnlf.BR;
import com.rd.hnlf.module.trade.viewControl.AddNoteCtrl;
import com.rd.hnlf.module.trade.viewControl.NoteInfoCtrl;
import com.rd.tools.utils.ConverterUtil;
import com.rd.tools.utils.DateUtil;
import com.rd.tools.utils.StringFormat;

import java.io.Serializable;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/29 11:31
 * <p/>
 * Description: 票面信息
 * {@link NoteInfoCtrl}{@link AddNoteCtrl}
 */
public class NoteDealingVM extends BaseObservable implements Serializable {
    /** 票号 */
    private String id;
    /** 票面金额（元） */
    private String amount;
    /** 到期日 */
    private long   dueDate;
    /** 调整天数（天） */
    private String days;
    /** 在列表中的位置，-1表示新增 */
    private int    position;

    public NoteDealingVM() {
        position = -1;
    }

    public String getShortId() {
        if (TextUtils.isEmpty(id)) {
            return "";
        } else {
            int length = id.length();
            return id.substring(length - 4);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public String getAmountStr() {
        return StringFormat.doubleMoney(amount);
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public long getDueDate() {
        return dueDate;
    }

    @Bindable
    public String getDueDateStr() {
        return DateUtil.formatter(DateUtil.Format.DATE, dueDate == 0 ? null : dueDate);
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
        notifyPropertyChanged(BR.dueDateStr);
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 乘以 10000
     */
    public NoteDealingVM multiplication() {
        setAmount(ConverterUtil.getDouble(amount) * 10000 + "");
        return this;
    }

    /**
     * 除以 10000
     */
    public NoteDealingVM division() {
        setAmount(ConverterUtil.getDouble(amount) / 10000 + "");
        return this;
    }
}