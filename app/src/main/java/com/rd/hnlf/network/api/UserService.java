package com.rd.hnlf.network.api;

import com.rd.hnlf.module.common.dataModel.submit.JsonSub;
import com.rd.hnlf.module.common.viewModel.bean.KVPBean;
import com.rd.hnlf.module.user.dataModel.receive.AccountInfoRec;
import com.rd.hnlf.module.user.dataModel.receive.AccountInfoVIPRec;
import com.rd.hnlf.module.user.dataModel.receive.BankcardRec;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.hnlf.module.user.dataModel.receive.SecurityCenterInitRec;
import com.rd.hnlf.module.user.dataModel.submit.AccountInfoVIPSub;
import com.rd.hnlf.module.user.dataModel.submit.LoginSub;
import com.rd.hnlf.module.user.dataModel.submit.ModifyPasswordSub;
import com.rd.hnlf.module.user.dataModel.submit.ModifyPhoneSub;
import com.rd.hnlf.module.user.dataModel.submit.RegisterSub;
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
 * Date: 2017/8/17 16:18
 * <p/>
 * Description: URL 地址如果以"/"开头，则会拼接在BASE_URL中的第一个"/"之后
 * 例如
 * BASE_URL = http://127.0.0.1/app/
 * ① URL = /test
 * 则最终的请求接口会是 http://127.0.0.1/test
 * ② URL = test
 * 则最终的请求接口会是 http://127.0.0.1/app/test
 */
public interface UserService {
    /** 获取验证码 */
    @FormUrlEncoded
    @POST("sendVerifyCode.htm")
    Call<HttpResult> sendVerifyCode(@Field(RequestParams.MOBILE) String mobile, @Field(RequestParams.TYPE) String type);

    /** 注册 */
    @POST("modules/customer/customerAction/register.htm")
    Call<HttpResult<OauthTokenRec>> register(@Body RegisterSub sub);

    /** 登录 */
    @POST("modules/customer/customerAction/login.htm")
    Call<HttpResult<OauthTokenRec>> login(@Body LoginSub sub);

    /** 刷新token */
    @FormUrlEncoded
    @POST("modules/customer/customerAction/refreshToken.htm")
    Call<HttpResult<OauthTokenRec>> refreshToken(@Field(RequestParams.REFRESH_TOKEN) String refreshToken);

    /** 安全中心初始化 */
    @POST("modules/customer/customerAction/getAccountCount.htm")
    Call<HttpResult<SecurityCenterInitRec>> securityCenterInit();

    /** 修改手机号 */
    @POST("modules/customer/customerAccountInfoAction/updateMobile.htm")
    Call<HttpResult> modifyPhone(@Body ModifyPhoneSub sub);

    /** 修改密码 */
    @POST("modules/customer/customerAccountInfoAction/updatePassword.htm")
    Call<HttpResult> modifyPassword(@Body ModifyPasswordSub sub);

    /** 验证手机号是否存在 */
    @FormUrlEncoded
    @POST("modules/customer/customerAction/checkMobile.htm")
    Call<HttpResult> checkMobile(@Field(RequestParams.MOBILE) String mobile, @Field(RequestParams.CODE) String code);

    /** 重设密码 */
    @POST("modules/customer/customerAction/setNewPassword.htm")
    Call<HttpResult> resetPassword(@Body ModifyPasswordSub sub);

    /** 获取账户信息 */
    @POST("modules/customer/customerAction/getAccountInfo.htm")
    Call<HttpResult<AccountInfoRec>> getAccountInfo();

    /** 保存或更新账户信息 */
    @POST("modules/customer/customerAction/saveOrUpdateAccountInfo.htm")
    Call<HttpResult> saveOrUpdateAccountInfo(@Body JsonSub sub);

    /** 获取账户信息 - 会员用户 */
    @POST("modules/customer/customerAction/getAccountInfo.htm")
    Call<HttpResult<AccountInfoVIPRec>> getAccountInfoVIP();

    /** 保存或更新账户信息 - 会员用户 */
    @POST("modules/customer/customerAction/saveOrUpdateAccountInfo.htm")
    Call<HttpResult> saveOrUpdateAccountInfoVIP(@Body AccountInfoVIPSub sub);

    /** 获取银行卡列表 */
    @FormUrlEncoded
    @POST("modules/customer/customerAccountInfoAction/getCustomerBankAccountInfoList.htm")
    Call<HttpResult<ListData<BankcardRec>>> getBankcardList(@Field(RequestParams.COLLECTOR_ACCOUNT_ID) String mobile);

    /** 删除银行卡 */
    @FormUrlEncoded
    @POST("modules/customer/customerAccountInfoAction/deleteCustomerBankAccountInfo.htm")
    Call<HttpResult> deleteBankcard(@Field(RequestParams.ID) String id);

    /** 获取开户银行列表 */
    @FormUrlEncoded
    @POST("modules/common/action/bizBaseAction/getBankList.htm")
    Call<HttpResult<ListData<KVPBean>>> getBankList(@Field(RequestParams.BANK_NAME) String bankName, @Field(RequestParams.CURRENT_PAGE) int currentPage);

    /** 获取开户支行列表 */
    @FormUrlEncoded
    @POST("modules/common/action/bizBaseAction/getBankDetailsPaging.htm")
    Call<HttpResult<ListData<KVPBean>>> getBranchList(@Field(RequestParams.BANK_CODE) String bankCode,
                                                      @Field(RequestParams.BANK_NAME) String bankName,
                                                      @Field(RequestParams.CURRENT_PAGE) int currentPage,
                                                      @Field(RequestParams.PAGE_SIZE) int pageSize);

    /** 新增或修改银行卡账户信息 */
    @POST("modules/customer/customerAccountInfoAction/saveOrUpdate.htm")
    Call<HttpResult> saveOrUpdateBankcard(@Body JsonSub sub);
}