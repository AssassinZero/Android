package com.rd.hnlf.network.api;

import com.rd.hnlf.module.common.dataModel.receive.OrderRec;
import com.rd.hnlf.module.common.dataModel.submit.JsonSub;
import com.rd.hnlf.module.eCommerce.dataModel.submit.NoteDetailBean;
import com.rd.hnlf.module.eCommerce.dataModel.submit.NoteDetailRec;
import com.rd.hnlf.module.pure.dataModel.receive.MyNoteRec;
import com.rd.hnlf.module.pure.dataModel.receive.NoteModifyRec;
import com.rd.hnlf.module.pure.dataModel.receive.PureDetailRec;
import com.rd.hnlf.module.pure.dataModel.submit.MyNoteSub;
import com.rd.hnlf.network.RequestParams;
import com.rd.network.entity.HttpResult;
import com.rd.network.entity.ListData;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 15:33
 * <p/>
 * Description: URL 地址如果以"/"开头，则会拼接在BASE_URL中的第一个"/"之后
 * 例如
 * BASE_URL = http://127.0.0.1/app/
 * ① URL = /test
 * 则最终的请求接口会是 http://127.0.0.1/test
 * ② URL = test
 * 则最终的请求接口会是 http://127.0.0.1/app/test
 */
public interface PureService {
    /** 获取纯票订单列表 */
    @FormUrlEncoded
    @POST("modules/nonRegulatory/nonRegulationAction/getOrderList.htm")
    Call<HttpResult<ListData<OrderRec>>> getPureList(@Field(RequestParams.TYPE) String type, @Field(RequestParams.CURRENT_PAGE) int currentPage);

    /** 获取票据列表 */
    @FormUrlEncoded
    @POST("modules/bills/action/billsAction/myBillsToApp.htm")
    Call<HttpResult<ListData<MyNoteRec>>> getNoteList(@Field(RequestParams.SEARCH_TYPE) String type, @Field(RequestParams.CURRENT_PAGE) int currentPage);

    /** 纯票复核 */
    @FormUrlEncoded
    @POST("modules/nonRegulatory/nonRegulationAction/reviewOrder.htm")
    Call<HttpResult> pureReviewOrder(@Field(RequestParams.ID) String orderId, @Field(RequestParams.RESULT) String result);

    /** 票据下架 */
    @FormUrlEncoded
    @POST("modules/billorder/action/bizBillCurrentMsgAction/lowerToApp.htm")
    Call<HttpResult> pullOffNote(@Field(RequestParams.BILL_NO) String billNo);

    /** 纯票订单详情 */
    @FormUrlEncoded
    @POST("modules/nonRegulatory/nonRegulationAction/getOrderInfoById.htm")
    Call<HttpResult<PureDetailRec>> getPureDetailInfo(@Field(RequestParams.ID) String orderId);

    /** 我的票据 - 持有中 - 上架详情 */
    @FormUrlEncoded
    @POST("modules/billorder/action/bizBillCurrentMsgAction/billShelvesQueryTwoToApp.htm")
    Call<HttpResult<ListData<NoteModifyRec>>> getPutOnDetail(@Field(RequestParams.BILL_NOS) String billNos);

    /** 纯票交易 */
    @POST("modules/nonRegulatory/nonRegulationAction/initiatedTransactions.htm")
    Call<HttpResult> doPureTransaction(@Body JsonSub sub);

    /** 我的票据 - 持有中 - 上架 */
    @POST("modules/billorder/action/bizBillCurrentMsgAction/batchShelvesToApp.htm")
    Call<HttpResult> putOnNote(@Body JsonSub sub);

    /** 我的票据 - 已上架 - 修改详情 */
    @FormUrlEncoded
    @POST("modules/billorder/action/bizBillCurrentMsgAction/queryShelvesToApp.htm")
    Call<HttpResult<NoteModifyRec>> getModifyDetail(@Field(RequestParams.BILL_NO) String billNo);

    /** 我的票据 - 已上架 - 修改 */
    @POST("modules/billorder/action/bizBillCurrentMsgAction/updateToApp.htm")
    Call<HttpResult> modifyMyNote(@Body MyNoteSub sub);

    /** 我的票据 - 详情 */
    @FormUrlEncoded
//    @POST("modules/common/action/bizBaseAction/showBillMsg.htm ")
    @POST("modules/billorder/action/bizBillCurrentMsgMarketAction/detailsToApp.htm ")
    Call<HttpResult<NoteDetailBean>> getNoteDetails(@Field(RequestParams.BILL_NO) String billNo);
    @FormUrlEncoded
    @POST("modules/billorder/action/bizBillCurrentMsgMarketAction/detailsToApp.htm ")
    Call<Response> getStringNoteDetails(@Field(RequestParams.BILL_NO) String billNo);
}