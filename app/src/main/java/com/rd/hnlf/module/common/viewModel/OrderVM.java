package com.rd.hnlf.module.common.viewModel;

import android.text.TextUtils;

import com.rd.hnlf.R;
import com.rd.hnlf.module.eCommerce.viewControl.ECommerceListCtrl;
import com.rd.hnlf.module.logic.ButtonOperateVM;
import com.rd.hnlf.module.pure.viewControl.PureListCtrl;
import com.rd.hnlf.module.trade.viewControl.TradeListCtrl;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.DateUtil;
import com.rd.tools.utils.StringFormat;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 10:13
 * <p/>
 * Description: {@link ECommerceListCtrl}{@link TradeListCtrl}{@link PureListCtrl}
 */
public class OrderVM extends ButtonOperateVM {
    /** 订单ID */
    private String orderId;
    /** 账户名称 */
    private String accountName;
    /**
     * 交易类型
     * 0100 - 会员发起收票交易
     * 0101 - 会员发起卖票交易
     * 0102 - 普通用户发起卖票交易
     * 0200 - 代理商发起撮合交易
     */
    private String type;
    /** 交易状态 */
    private String status;
    /** 票面总金额 */
    private String totalAmount;
    /** 结算金额 */
    private String settlementAmount;
    /** 票据张数 */
    private String noteCount;
    /** 下单时间 */
    private String orderTime;
    /** 订单号 / 交易号 */
    private String orderNo;
    /**
     * 订单类型
     * 0 - 电商订单
     * 1 - 交易订单
     * 2 - 纯票订单
     */
    private int    mode;

    public OrderVM(int mode) {
        this.mode = mode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 交易类型
     * 0100 - 会员发起收票交易
     * 0101 - 会员发起卖票交易
     * 0102 - 普通用户发起卖票交易
     * 0200 - 代理商发起撮合交易
     * PS:
     * 0100、0101、0102 都是直接交易
     * 0200 是撮合交易
     */
    public String getType() {
        if (TextUtils.isEmpty(type)) {
            return "";
        } else if ("0200".equals(type)) {
            return "撮合交易";
        } else {
            return "直接交易";
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalAmount() {
        return StringFormat.doubleMoney(totalAmount);
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSettlementAmount() {
        return StringFormat.doubleMoney(settlementAmount);
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getNoteCount() {
        return ContextHolder.getContext().getString(R.string.piece, noteCount);
    }

    public void setNoteCount(String noteCount) {
        this.noteCount = noteCount;
    }

    public String getOrderTime() {
        return DateUtil.formatter(DateUtil.Format.SECOND, orderTime);
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 交易号 : 1 - 交易订单
     * 订单号 : 0 - 电商订单 / 2 - 纯票订单
     */
    public String getOrderNoString() {
        // if (mode == Constant.NUMBER_1) {
        //     return ContextHolder.getContext().getString(R.string.seller_transaction_no, orderNo);
        // } else {
        return ContextHolder.getContext().getString(R.string.seller_order_no, orderNo);
        // }
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}