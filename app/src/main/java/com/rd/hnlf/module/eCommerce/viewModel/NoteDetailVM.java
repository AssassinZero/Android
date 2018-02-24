package com.rd.hnlf.module.eCommerce.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import com.rd.hnlf.BR;
import com.rd.hnlf.R;
import com.rd.hnlf.module.eCommerce.viewControl.NoteDetailCtrl;
import com.rd.hnlf.module.user.UserLogic;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.ConverterUtil;
import com.rd.tools.utils.DateUtil;
import com.rd.tools.utils.StringFormat;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/1 17:21
 * <p/>
 * Description: {@link NoteDetailCtrl}
 */
public class NoteDetailVM extends BaseObservable {
    /** 票据票号 */
    private String       id;
    /** 承兑方名称 */
    private String       acceptName;
    /** 转让手数 */
    private String       transferCount;
    /** 回头背书 */
    private String       reendorsment;
    /** 票面金额 */
    private String       amount;
    /** 年利率 */
    private String       apr;
    /** 每十万 */
    private String       discount;
    /** 到期日 */
    private String       dueDate;
    /** 调整天数 */
    private String       days;
    /** 票据类型 */
    private String       type;
    /** 票据属性 */
    private String       property;
    /** 持票企业 */
    private String       holderEnterprise;
    /** 联系电话 */
    private String       phone;
    /** 上架时间 */
    private String       putOnTime;
    /** 票据图片 */
    private List<String> noteImages;
    /** 交易需知 */
    private String       prompt;
    /** 是否显示面议 10 - 是，20 - 否 */
    private boolean      discuss;
    /** 是否可转让 */
    private boolean      enable;
    /** 是否回头背书 */
    private boolean      endorsementVisible;
    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getAcceptName() {
        return acceptName;
    }

    public void setAcceptName(String acceptName) {
        this.acceptName = acceptName;
        notifyPropertyChanged(BR.acceptName);
    }

    @Bindable
    public String getTransferCount() {
        return transferCount;
    }

    public void setTransferCount(String transferCount) {
        this.transferCount = transferCount;
        notifyPropertyChanged(BR.transferCount);
    }

    @Bindable
    public String getReendorsment() {
        return reendorsment;
    }

    public void setReendorsment(String reendorsment) {
        this.reendorsment = reendorsment;
        notifyPropertyChanged(BR.reendorsment);
    }

    @Bindable
    public Spanned getAmount() {
        return Html.fromHtml(
                ContextHolder.getContext().getString(R.string.note_detail_amount_spanned, StringFormat.doubleFormat(ConverterUtil.getDouble(amount) / 10000)));
    }

    public void setAmount(String amount) {
        this.amount = amount;
        notifyPropertyChanged(BR.amount);
    }

    @Bindable
    public String getApr() {
        return apr;
    }

    @Bindable
    public Spanned getAprSpanned() {
        return Html.fromHtml(ContextHolder.getContext().getString(R.string.note_detail_apr_spanned,
                StringFormat.fourFormat(ConverterUtil.getDouble(apr) * 100)));
    }

    public void setApr(String apr) {
        this.apr = apr;
        notifyPropertyChanged(BR.apr);
        notifyPropertyChanged(BR.aprSpanned);
    }

    @Bindable
    public String getDiscount() {
        return discount;
    }

    @Bindable
    public Spanned getDiscountSpanned() {
        return Html.fromHtml(ContextHolder.getContext().getString(R.string.note_detail_discount_spanned, StringFormat.doubleFormat(discount)));
    }

    public void setDiscount(String discount) {
        this.discount = discount;
        notifyPropertyChanged(BR.discount);
        notifyPropertyChanged(BR.discountSpanned);
    }

    @Bindable
    public String getDueDate() {
        return DateUtil.formatter(DateUtil.Format.DATE, dueDate);
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
        notifyPropertyChanged(BR.dueDate);
    }

    @Bindable
    public String getDays() {
        return TextUtils.isEmpty(days) ? "" : ContextHolder.getContext().getString(R.string.days, days);
    }

    public void setDays(String days) {
        this.days = days;
        notifyPropertyChanged(BR.days);
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
        notifyPropertyChanged(BR.property);
    }

    @Bindable
    public String getHolderEnterprise() {
        return holderEnterprise;
    }

    public void setHolderEnterprise(String holderEnterprise) {
        this.holderEnterprise = holderEnterprise;
        notifyPropertyChanged(BR.holderEnterprise);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getPutOnTime() {
        return DateUtil.formatter(DateUtil.Format.SECOND, putOnTime);
    }

    public void setPutOnTime(String putOnTime) {
        this.putOnTime = putOnTime;
        notifyPropertyChanged(BR.putOnTime);
    }

    @Bindable
    public List<String> getNoteImages() {
        return noteImages;
    }

    public void setNoteImages(List<String> noteImages) {
        this.noteImages = noteImages;
        notifyPropertyChanged(BR.noteImages);
    }

    @Bindable
    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
        notifyPropertyChanged(BR.prompt);
    }

    @Bindable
    public boolean isDiscuss() {
        return discuss;
    }

    public void setDiscuss(String discuss) {
        this.discuss = "10".equals(discuss);
        notifyPropertyChanged(BR.discuss);
    }

    @Bindable
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        notifyPropertyChanged(BR.enable);
    }

    @Bindable
    public boolean isEndorsementVisible() {
        return endorsementVisible;
    }

    public void setEndorsementVisible(boolean endorsementVisible) {
        this.endorsementVisible = endorsementVisible;
        notifyPropertyChanged(BR.endorsementVisible);
    }

    @Bindable
    public boolean isLand() {
        return UserLogic.isLand();
    }
}