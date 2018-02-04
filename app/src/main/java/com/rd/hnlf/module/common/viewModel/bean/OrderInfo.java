package com.rd.hnlf.module.common.viewModel.bean;

import com.rd.tools.utils.DateUtil;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/23 17:56
 * <p/>
 * Description: 订单信息
 */
public class OrderInfo {
    /** 订单号 */
    private String id;
    /** 下单时间 */
    private String time;
    /** 下单完成时间 */
    private String completeTime;
    /** 订单状态 */
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return DateUtil.formatter(DateUtil.Format.SECOND, time);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCompleteTime() {
        return DateUtil.formatter(DateUtil.Format.SECOND, completeTime);
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}