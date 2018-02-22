package com.rd.hnlf.module.eCommerce.viewModel;

import android.databinding.ObservableBoolean;
import android.text.Html;
import android.text.Spanned;

import com.rd.hnlf.R;
import com.rd.hnlf.module.eCommerce.viewControl.NoteMallCtrl;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.ConverterUtil;
import com.rd.tools.utils.DateUtil;
import com.rd.tools.utils.StringFormat;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/31 10:42
 * <p/>
 * Description: {@link NoteMallCtrl}
 */
public class NoteMallVM {
    /** 票据票号 */
    private String            id;
    /** 承兑方名称 */
    private String            acceptName;
    /** 票据类型（图片地址） */
    private String            type;
    /** 票面金额 */
    private String            amount;
    /** 票据属性（图片地址） */
    private String            property;
    /** 报价方式: 年利率 / 每十万 */
    private String            mode;
    /** 到期日 */
    private String            dueDate;
    /** 票据状态 */
    private String            status;
    /** 是否显示面议 10 - 是，20 - 否 */
    private String            isDiscussPersonally;
    /** 是否选中 */
    public  ObservableBoolean select;

    public NoteMallVM() {
        select = new ObservableBoolean(false);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcceptName() {
        return ContextHolder.getContext().getString(R.string.mall_accept_name, acceptName);
    }

    public void setAcceptName(String acceptName) {
        this.acceptName = acceptName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Spanned getAmount() {
        return Html.fromHtml(ContextHolder.getContext().getString(R.string.mall_amount, StringFormat.doubleFormat(ConverterUtil.getDouble(amount) / 10000)));
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Spanned getMode() {
        return Html.fromHtml(mode);
    }

    public void setMode(String mode, String apr, String discount) {
        if (isDiscuss()) {
            this.mode = ContextHolder.getContext().getString(R.string.mall_discuss);
        } else if ("10".equals(mode)) {
            this.mode = ContextHolder.getContext().getString(R.string.mall_apr, StringFormat.fourFormat(ConverterUtil.getDouble(apr) * 100));
        } else {
            this.mode = ContextHolder.getContext().getString(R.string.mall_discount, StringFormat.doubleFormat(discount));
        }
    }

    public String getDueDate() {
        return ContextHolder.getContext().getString(R.string.mall_due_date, DateUtil.formatter(DateUtil.Format.DATE, dueDate));
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDiscuss() {
        return "10".equals(isDiscussPersonally);
    }

    public void setIsDiscussPersonally(String isDiscussPersonally) {
        this.isDiscussPersonally = isDiscussPersonally;
    }

    public void setSelect() {
        select.set(!select.get());
    }

    public boolean isBusinessTicket() {
        return "商票".equals(type);
    }

    public boolean transferable() {
        return "10".equals(status);
    }
}