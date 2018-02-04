package com.rd.hnlf.network.api;

import com.rd.hnlf.common.BaseParams;
import com.rd.hnlf.module.common.dataModel.receive.StatisticsRec;
import com.rd.network.entity.HttpResult;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/17 16:18
 * <p/>
 * Description:
 */
public interface CommonService {
    /** 获取 "累计交易金额" 和 "累计交易笔数" */
    @POST("modules/common/statisticsAction/getStatisticsInfo.htm")
    Call<HttpResult<StatisticsRec>> getStatisticsInfo();

    /** 注册协议 */
    String REGISTER_PROTOCOL = BaseParams.URI + "";
}