package com.rd.hnlf.network.api;

import com.rd.hnlf.module.common.dataModel.receive.OrderRec;
import com.rd.hnlf.module.common.dataModel.submit.JsonSub;
import com.rd.hnlf.module.eCommerce.dataModel.receive.ConditionRec;
import com.rd.hnlf.module.eCommerce.dataModel.receive.ECommerceDetailRec;
import com.rd.hnlf.module.eCommerce.dataModel.receive.NoteMallRec;
import com.rd.hnlf.module.eCommerce.dataModel.receive.NotePurchaseRec;
import com.rd.hnlf.module.eCommerce.dataModel.submit.ConditionSub;
import com.rd.hnlf.module.eCommerce.dataModel.submit.NoteDetailRec;
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
 * Date: 2017/8/29 11:02
 * <p/>
 * Description:URL 地址如果以"/"开头，则会拼接在BASE_URL中的第一个"/"之后
 * 例如
 * BASE_URL = http://127.0.0.1/app/
 * ① URL = /test
 * 则最终的请求接口会是 http://127.0.0.1/test
 * ② URL = test
 * 则最终的请求接口会是 http://127.0.0.1/app/test
 */
public interface ECommerceService {
    /** 获取商城列表查询条件 */
    @POST("modules/billorder/action/bizBillCurrentMsgMarketAction/getCountEnterpriseToApp.htm")
    Call<HttpResult<ConditionRec>> getConditions();

    /** 获取票据商城列表 */
    @POST("modules/billorder/action/bizBillCurrentMsgMarketAction/marketListToApp.htm")
    Call<HttpResult<ListData<NoteMallRec>>> getNoteMallList(@Body ConditionSub sub);

    /** 获取票据详情 */
    @FormUrlEncoded
    @POST("modules/billorder/action/bizBillCurrentMsgMarketAction/detailsToApp.htm")
    Call<HttpResult<NoteDetailRec>> getNoteDetail(@Field(RequestParams.BILL_NO) String billNo);

    /** 获取票据购买信息 */
    @FormUrlEncoded
    @POST("modules/billorder/action/bizBillCurrentMsgMarketAction/preBillBatchTwoToApp.htm")
    Call<HttpResult<NotePurchaseRec>> getPurchaseDetail(@Field(RequestParams.BILL_NOS) String billNos, @Field(RequestParams.MOBILE) String mobile);

    /** 提交订单 */
    @POST("modules/billorder/action/bizBillCurrentMsgMarketAction/placeAnOrderToAndroid.htm")
    Call<HttpResult> placeAnOrder(@Body JsonSub sub);

    /**
     * 获取电商订单列表
     *
     * @param type
     *         普通用户 - 我是买家      1006
     *         会员用户 - 买入票据      1007
     *         会员用户 - 卖出票据      1008
     */
    @FormUrlEncoded
    @POST("modules/business/action/directBusinessAction/getBusinessOrderToApp.htm")
    Call<HttpResult<ListData<OrderRec>>> getECommerceList(@Field(RequestParams.SEARCH_TYPE) String type, @Field(RequestParams.CURRENT_PAGE) int currentPage);

    /** 电商订单详情 */
    @FormUrlEncoded
    @POST("modules/billorder/action/bizOnlineOrderAction/getDetailsToApp.htm")
    Call<HttpResult<ECommerceDetailRec>> getECommerceDetailInfo(@Field(RequestParams.ORDER_NO) String orderNo);

    /** 撤销订单 */
    @FormUrlEncoded
    @POST("modules/billorder/action/bizOnlineOrderAction/closeToApp.htm")
    Call<HttpResult> cancelOrder(@Field(RequestParams.ORDER_NO) String orderNo);

    /** 背书 */
    @FormUrlEncoded
    @POST("modules/billorder/action/bizOnlineOrderAction/recite.htm")
    Call<HttpResult> reciteOrder(@Field(RequestParams.ORDER_NO) String orderNo);
}