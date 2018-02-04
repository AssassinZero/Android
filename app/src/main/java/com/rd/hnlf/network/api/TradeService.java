package com.rd.hnlf.network.api;

import com.rd.hnlf.module.common.dataModel.receive.OrderRec;
import com.rd.hnlf.module.common.dataModel.submit.JsonSub;
import com.rd.hnlf.module.trade.dataModel.receive.DictionaryRec;
import com.rd.hnlf.module.trade.dataModel.receive.TradeDetailRec;
import com.rd.hnlf.module.user.dataModel.receive.BankcardRec;
import com.rd.hnlf.network.RequestParams;
import com.rd.network.entity.HttpResult;
import com.rd.network.entity.ListData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/15 14:42
 * <p/>
 * Description: URL 地址如果以"/"开头，则会拼接在BASE_URL中的第一个"/"之后
 * 例如
 * BASE_URL = http://127.0.0.1/app/
 * ① URL = /test
 * 则最终的请求接口会是 http://127.0.0.1/test
 * ② URL = test
 * 则最终的请求接口会是 http://127.0.0.1/app/test
 */
public interface TradeService {
    /**
     * 获取字典数据
     * 票据类型 - BILL_TYPE
     * 票据属性 - BILL_ATTRIBUTE
     */
    @FormUrlEncoded
    @POST("getDictsByParams.htm")
    Call<HttpResult<DictionaryRec>> getDictsByParams(@Field(RequestParams.TYPE) String type);

    /** 检测票据是否可以交易 */
    @FormUrlEncoded
    @POST("modules/business/action/directBusinessAction/checkBillIsOccupied.htm")
    Call<HttpResult> checkBillIsOccupied(@Field(RequestParams.BILL_NO) String billNo);

    /** 票据交易 - 获取指定签约卡 */
    @FormUrlEncoded
    @POST("modules/common/action/bizBaseAction/getSignBankAccountMsg.htm")
    Call<HttpResult<BankcardRec>> getSignBankAccount(@Field(RequestParams.COLLECTOR_ACCOUNT_ID) String mobile,
                                                     @Field(RequestParams.COLLECTING_BANK_ACCOUNT) String bankcard);

    /** 票据交易 - 获取帐号下所有卡 */
    @FormUrlEncoded
    @POST("modules/common/action/bizBaseAction/getSignBankByAccount.htm")
    Call<HttpResult<ListData<BankcardRec>>> getAllBankAccount(@Field(RequestParams.COLLECTOR_ACCOUNT_ID) String mobile);

    /** 获取票据交易 - 获取结算金额 */
    @POST("modules/common/action/bizBaseAction/getTotalSettleAmount.htm")
    Call<HttpResult<TradeDetailRec>> getTotalSettleAmount(@Body JsonSub jsonSub);

    /** 获取票据交易 - 发起交易 */
    @POST("modules/business/action/directBusinessAction/initiateBusiness.htm")
    Call<HttpResult> initiateBusiness(@Body JsonSub jsonSub);

    /**
     * 获取交易订单列表
     *
     * @param type
     *         普通客户   - 我是卖家    1001
     *         会员用户   - 我是卖家    1003
     *         会员用户   - 我是买家    1004
     *         代理商用户 - 票据交易    1005
     * @param state
     *         默认查询全部订单
     *         普通用户 - 我是卖家   待确认 0012  待修改 0022
     *         会员用户 - 我是卖家   待确认 0012  待修改 0022
     *         会员用户 - 我是买家   待付款 0040  待确认 0011 待复核 0041
     */
    @FormUrlEncoded
    @POST("modules/business/action/directBusinessAction/getBusinessOrderToApp.htm")
    Call<HttpResult<ListData<OrderRec>>> getTradeList(@Field(RequestParams.SEARCH_TYPE) String type, @Field(RequestParams.BUSINESS_STATE) String state,
                                                      @Field(RequestParams.CURRENT_PAGE) int currentPage);

    /** 订单确认 */
    @FormUrlEncoded
    @POST("modules/business/action/directBusinessAction/confirmOrder.htm")
    Call<HttpResult> confirmOrder(@Field(RequestParams.ORDER_ID) String orderId, @Field(RequestParams.COMMENT) String comment);

    /** 退回订单 */
    @FormUrlEncoded
    @POST("modules/business/action/directBusinessAction/returnOrder.htm")
    Call<HttpResult> returnOrder(@Field(RequestParams.ORDER_ID) String orderId);

    /** 撤回订单 */
    @FormUrlEncoded
    @POST("modules/business/action/directBusinessAction/recallOrder.htm")
    Call<HttpResult> recallOrder(@Field(RequestParams.ORDER_ID) String orderId);

    /** 关闭订单 */
    @FormUrlEncoded
    @POST("modules/business/action/directBusinessAction/closeOrder.htm")
    Call<HttpResult> closeOrder(@Field(RequestParams.ORDER_ID) String orderId);

    /** 订单支付 */
    @FormUrlEncoded
    @POST("modules/business/action/directBusinessAction/paymentOrder.htm")
    Call<HttpResult> paymentOrder(@Field(RequestParams.ORDER_ID) String orderId);

    /** 订单支付复核 */
    @FormUrlEncoded
    @POST("modules/business/action/directBusinessAction/reviewOrder.htm")
    Call<HttpResult> reviewOrder(@Field(RequestParams.ORDER_ID) String orderId);

    /** 交易订单详情 */
    @FormUrlEncoded
    @POST("modules/business/action/directBusinessAction/queryOrderDetail.htm")
    Call<HttpResult<TradeDetailRec>> getTradeDetailInfo(@Field(RequestParams.ORDER_ID) String orderId);
}