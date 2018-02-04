package com.rd.hnlf.module.common.viewModel.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rd.hnlf.R;
import com.rd.hnlf.common.BundleKeys;
import com.rd.hnlf.router.RouterUrl;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.ConverterUtil;
import com.rd.tools.utils.DateUtil;
import com.rd.tools.utils.StringFormat;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 17:33
 * <p/>
 * Description: 票面信息
 */
public class NoteInfo implements Parcelable {
    /** 票据票号 */
    private String  id;
    /** 票面金额 */
    private String  amount;
    /** 调整天数 */
    private String  days;
    /** 到期日 */
    private String  dueDate;
    /** 年利率 */
    private String  apr;
    /** 每十万 */
    private String  discount;
    /** 票据类型 */
    private String  type;
    /** 票据属性 */
    private String  property;
    /** 应付金额 */
    private String  amountPayable;
    /** 票据状态 10 - 可转让，20 - 转让中 */
    private String  transferState;
    /** 票据是否到期 10 - 到期，20 - 未到期 */
    private String  overdue;
    /**
     * 订单类型
     * 0 - 电商订单(显示：票据票号、票面金额、调整天数、到期日、年利率/每十万、票据类型、票据属性、应付金额)
     * 1 - 交易订单(显示：票据票号、票面金额、调整天数、到期日)
     * 2 - 纯票订单(显示：票据票号、票面金额、调整天数、到期日、票据类型、票据属性)
     */
    private int     mode;
    /** 是否可点击 */
    private boolean clickable;

    public NoteInfo(int mode) {
        this.mode = mode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return StringFormat.doubleMoney(amount);
    }

    public String getOriginAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDays() {
        return TextUtils.isEmpty(days) ? "0" : days;
    }

    public String getDaysStr() {
        return ContextHolder.getContext().getString(R.string.days, ConverterUtil.getInteger(days) + "");
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDueDate() {
        return DateUtil.formatter(DateUtil.Format.DATE, dueDate);
    }

    public String getOriginDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getApr() {
        return ContextHolder.getContext().getString(R.string.percent, StringFormat.doubleFormat(ConverterUtil.getDouble(apr) * 100));
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getDiscount() {
        return ContextHolder.getContext().getString(R.string.yuan, StringFormat.doubleFormat(discount));
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getAmountPayable() {
        return amountPayable;
    }

    public String getAmountPayableStr() {
        return StringFormat.doubleMoney(amountPayable);
    }

    public void setAmountPayable(String amountPayable) {
        this.amountPayable = amountPayable;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isTransferState() {
        return "10".equals(transferState);
    }

    public void setTransferState(String transferState) {
        this.transferState = transferState;
    }

    public boolean isOverdue() {
        return "10".equals(overdue);
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

    public void itemClick(View view) {
        if (isClickable()) {
            ARouter.getInstance().build(RouterUrl.MY_NOTE_DETAIL)
                    .withString(BundleKeys.ID, id)
                    .navigation();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.amount);
        dest.writeString(this.days);
        dest.writeString(this.dueDate);
        dest.writeString(this.apr);
        dest.writeString(this.discount);
        dest.writeString(this.type);
        dest.writeString(this.property);
        dest.writeString(this.amountPayable);
        dest.writeString(this.transferState);
        dest.writeString(this.overdue);
        dest.writeInt(this.mode);
        dest.writeByte(this.clickable ? (byte) 1 : (byte) 0);
    }

    protected NoteInfo(Parcel in) {
        this.id = in.readString();
        this.amount = in.readString();
        this.days = in.readString();
        this.dueDate = in.readString();
        this.apr = in.readString();
        this.discount = in.readString();
        this.type = in.readString();
        this.property = in.readString();
        this.amountPayable = in.readString();
        this.transferState = in.readString();
        this.overdue = in.readString();
        this.mode = in.readInt();
        this.clickable = in.readByte() != 0;
    }

    public static final Creator<NoteInfo> CREATOR = new Creator<NoteInfo>() {
        @Override
        public NoteInfo createFromParcel(Parcel source) {
            return new NoteInfo(source);
        }

        @Override
        public NoteInfo[] newArray(int size) {
            return new NoteInfo[size];
        }
    };
}