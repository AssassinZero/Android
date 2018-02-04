package com.rd.hnlf.module.user;

import com.rd.hnlf.R;
import com.rd.hnlf.network.RDClient;
import com.rd.hnlf.network.RequestCallBack;
import com.rd.hnlf.network.api.UserService;
import com.rd.network.entity.HttpResult;
import com.rd.tools.utils.RegularUtil;
import com.rd.tools.utils.ToastUtil;
import com.rd.views.textView.TimeButton;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/3/24 16:28
 * <p/>
 * Description:
 */
public class CodeLogic {
    /** 注册 */
    public static final String REGISTER        = "register";
    /** 登录 */
    public static final String LOGIN           = "login";
    /** 忘记密码 */
    public static final String FORGOT_PASSWORD = "forgotPassword";
    /** 绑定手机 */
    public static final String BIND_MOBILE     = "bindMobile";

    /**
     * 获取验证码
     *
     * @param type
     *         注册         register
     *         登录         login
     *         忘记密码     forgotPassword
     *         绑定手机     bindMobile
     */
    public static void getCodeClick(final TimeButton view, String phone, String type) {
        if (!RegularUtil.isPhone(phone)) {
            ToastUtil.toast(R.string.validate_phone);
            view.reset();
        } else {
            Call<HttpResult> call = RDClient.getService(UserService.class).sendVerifyCode(phone, type);
            call.enqueue(new RequestCallBack<HttpResult>() {
                @Override
                public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                    view.countdown();
                    ToastUtil.toast(response.body().getMsg());
                }

                @Override
                public void onFailed(Call<HttpResult> call, Response<HttpResult> response) {
                    view.reset();
                    super.onFailed(call, response);
                }

                @Override
                public void onFailure(Call<HttpResult> call, Throwable t) {
                    view.reset();
                    super.onFailure(call, t);
                }
            });
        }
    }
}