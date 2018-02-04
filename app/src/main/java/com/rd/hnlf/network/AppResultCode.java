package com.rd.hnlf.network;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/10/31 13:53
 * <p/>
 * Description:
 */
public class AppResultCode {
    /** 401 用户未登录 */
    public static final int TOKEN_NOT_LOGIN               = 401;
    /** 403 请求密钥不匹配 */
    public static final int TOKEN_MISMATCHES              = 403;
    /** 406 验证签名失败 */
    public static final int TOKEN_VERIFY_SIGNATURE_FAILED = 406;
    /** 700 TOKEN 失效 + 顶号 */
    public static final int TOKEN_OTHER                   = 700;
    /** 900 不显示 TOAST 信息 */
    public static final int NOT_TOAST                     = 900;
}