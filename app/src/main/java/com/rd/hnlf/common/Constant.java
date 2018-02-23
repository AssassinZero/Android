package com.rd.hnlf.common;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/18 16:23
 * <p/>
 * Description: 常量类
 */
public class Constant {
    /** number */
    public static final int    NUMBER__2            = -2;
    public static final int    NUMBER__1            = -1;
    public static final int    NUMBER_0             = 0;
    public static final int    NUMBER_1             = 1;
    public static final int    NUMBER_2             = 2;
    public static final int    NUMBER_3             = 3;
    public static final int    NUMBER_4             = 4;
    public static final int    NUMBER_5             = 5;
    public static final int    NUMBER_6             = 6;
    public static final int    NUMBER_7             = 7;
    public static final int    NUMBER_8             = 8;
    public static final int    NUMBER_9             = 9;
    /** status */
    public static final String STATUS__2            = "-2";
    public static final String STATUS__1            = "-1";
    public static final String STATUS_0             = "0";
    public static final String STATUS_1             = "1";
    public static final String STATUS_2             = "2";
    public static final String STATUS_3             = "3";
    public static final String STATUS_4             = "4";
    public static final String STATUS_5             = "5";
    public static final String STATUS_6             = "6";
    public static final String STATUS_7             = "7";
    public static final String STATUS_8             = "8";
    public static final String STATUS_9             = "9";
    public static final String STATUS_10            = "10";
    /**
     * 交易订单列表
     * 普通用户 - 我是卖家 10 - 全部,11 - 待确认,12 - 待修改
     * 会员用户 - 我是卖家 10 - 全部,11 - 待确认,12 - 待修改
     * 代理商   - 票据交易 20
     * 会员用户 - 我是买家 30 - 全部,31 - 待经办,32 - 待复核,33 - 待支付
     */
    public static final String TRADE_SELLER_ALL     = "10";
    public static final String TRADE_SELLER_CONFIRM = "11";
    public static final String TRADE_SELLER_MODIFY  = "12";
    public static final String TRADE_NOTE_DEALING   = "20";
    public static final String TRADE_BUYER_ALL      = "30";
    public static final String TRADE_BUYER_HANDLE   = "31";
    public static final String TRADE_BUYER_REVIEW   = "32";
    public static final String TRADE_BUYER_PAYMENT  = "33";
    /**
     * 电商订单列表
     * 普通用户 - 我是买家 40
     * 会员用户 - 电商订单 50 - 买入票据,51 - 卖出票据
     */
    public static final String E_COMMERCE_BUYER     = "40";
    public static final String E_COMMERCE_BUY       = "50";
    public static final String E_COMMERCE_SELL      = "51";
    /**
     * 纯票订单列表
     */
    public static final String PURE_LIST            = "60";
    /**
     * 票据列表
     * 普通用户 - 历史订单 72
     * 会员用户 - 持有中 - 70,已上架 - 71,历史订单 - 72
     */
    public static final String NOTE_HOLDING         = "70";
    public static final String NOTE_PUT_ON          = "71";
    public static final String NOTE_HISTORY         = "72";
    /** 选择银行卡 requestCode */
    public static final int    CHOOSE_BANK          = 0X77;
    public static final int    CHOOSE_BRANCH        = 0X88;
    /** network params */
    // 公共参数
    public static final String APP_KEY              = "appkey";
    public static final String SIGNA                = "signMsg";
    public static final String TS                   = "subtime";
    public static final String MOBILE_TYPE          = "mobileType";
    public static final String VERSION_NUMBER       = "versionNumber";
    // 登录参数
    public static final String TOKEN                = "token";
    public static final String USER_ID              = "userId";
    // SP 字段
    public static final String IS_LAND              = "isLand";
    public static final String IS_FIRST_IN          = "isFirstIn";
    public static final String IS_FINGERPRINT          = "isFingerprint";
}