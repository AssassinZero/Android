package com.rd.hnlf.router;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/6/13 17:52
 * <p/>
 * Description:
 */
public class RouterUrl {
    private static final String SCHEME                   = "/hnlf";
    /** 自定义全局降级策略 */
    public static final  String DEGRADE                  = SCHEME + "/degrade";
    /** 自定义全局降级策略 */
    public static final  String PATH_REPLACE             = SCHEME + "/pathReplace";
    /** 传递自定义对象时序列化策略 */
    public static final  String SERIALIZATION            = SCHEME + "/serialization";
    /** 主页 */
    public static final  String MAIN                     = SCHEME + "/main";

    ///////////////////////////////////////////////////////////////////////////
    // COMMON
    ///////////////////////////////////////////////////////////////////////////
    /** H5页面 */
    public static final  String WEB_VIEW                 = SCHEME + "/common/webView";
    /** PDF预览 */
    public static final  String PDF_VIEWER               = SCHEME + "/common/pdfViewer";
    /** 内容预览 */
    public static final  String CONTENT_PREVIEW          = SCHEME + "/common/contentPreview";

    ///////////////////////////////////////////////////////////////////////////
    // 用户模块
    ///////////////////////////////////////////////////////////////////////////
    /** 登录 */
    public static final  String USER_LOGIN               = SCHEME + "/user/login";
    /** 注册 */
    public static final  String USER_REGISTER            = SCHEME + "/user/register";
    /** 注册成功 */
    public static final  String USER_REGISTER_SUCCEED    = SCHEME + "/user/registerSucceed";
    /** 忘记密码 */
    public static final  String USER_FORGOT_PASSWORD     = SCHEME + "/user/forgotPassword";
    /** 重置密码 */
    public static final  String USER_RESET_PASSWORD      = SCHEME + "/user/resetPassword";
    /** 个人中心 */
    public static final  String USER_PERSONAL_CENTER     = SCHEME + "/user/personalCenter";
    /** 账户信息 */
    public static final  String USER_ACCOUNT_INFO        = SCHEME + "/user/accountInfo";
    /** 账户信息 - 会员用户 */
    public static final  String USER_ACCOUNT_INFO_VIP    = SCHEME + "/user/accountInfoVIP";
    /** 安全中心 */
    public static final  String USER_SECURITY_CENTER     = SCHEME + "/user/securityCenter";
    /** 手机绑定 */
    public static final  String USER_MODIFY_PHONE        = SCHEME + "/user/modifyPhone";
    /** 修改密码 */
    public static final  String USER_MODIFY_PASSWORD     = SCHEME + "/user/modifyPassword";
    /** 银行卡列表 */
    public static final  String USER_BANKCARD_LIST       = SCHEME + "/user/bankcardList";
    /** 新增、修改银行卡 */
    public static final  String USER_BANKCARD_EDIT       = SCHEME + "/user/bankcardEdit";
    /** 银行选择 */
    public static final  String USER_BANK_CHOOSE         = SCHEME + "/user/bankChoose";

    ///////////////////////////////////////////////////////////////////////////
    // 交易订单
    ///////////////////////////////////////////////////////////////////////////
    /** 票据交易 - 票面信息 */
    public static final  String TRADE_NOTE_INFO          = SCHEME + "/trade/noteInfo";
    /** 票据交易 - 添加票据 */
    public static final  String TRADE_ADD_NOTE           = SCHEME + "/trade/addNote";
    /** 票据交易 - 收票方/持票方信息 */
    public static final  String TRADE_NOTE_ACCOUNT       = SCHEME + "/trade/noteAccount";
    /** 票据交易 - 报价信息 */
    public static final  String TRADE_NOTE_QUOTATION     = SCHEME + "/trade/noteQuotation";
    /** 会员用户 - 我是买家 */
    public static final  String TRADE_BUYER_VIP          = SCHEME + "/trade/buyerVIP";
    /** 普通用户/会员用户 - 我是卖家 */
    public static final  String TRADE_SELLER             = SCHEME + "/trade/seller";
    /** 代理商 - 票据交易 */
    public static final  String TRADE_NOTE_TRANSACTION   = SCHEME + "/trade/noteTransaction";
    /** 订单交易详情 */
    public static final  String TRADE_DETAIL             = SCHEME + "/trade/detail";
    /** 订单交易成功 */
    public static final  String TRADE_SUCCESSFULLY             = SCHEME + "/trade/successfully";

    ///////////////////////////////////////////////////////////////////////////
    // 电商订单
    ///////////////////////////////////////////////////////////////////////////
    /** 票据商城 */
    public static final  String E_COMMERCE_NOTE_MALL     = SCHEME + "/eCommerce/noteMal";
    /** 票据商城 - 票据详情 */
    public static final  String E_COMMERCE_NOTE_DETAIL   = SCHEME + "/eCommerce/noteDetail";
    /** 票据商城 - 票据购买 */
    public static final  String E_COMMERCE_NOTE_PURCHASE = SCHEME + "/eCommerce/notePurchase";
    /** 普通用户 - 我是买家 */
    public static final  String E_COMMERCE_BUYER_NORMAL  = SCHEME + "/eCommerce/buyerNormal";
    /** 电商订单 */
    public static final  String E_COMMERCE_ORDER         = SCHEME + "/eCommerce/order";
    /** 电商订单详情 */
    public static final  String E_COMMERCE_DETAIL        = SCHEME + "/eCommerce/detail";


    ///////////////////////////////////////////////////////////////////////////
    // 纯票订单
    ///////////////////////////////////////////////////////////////////////////
    /** 纯票订单详情 */
    public static final  String PURE_PURE_DETAIL         = SCHEME + "/pure/pureDetail";
    /** 历史票据 */
    public static final  String PURE_HISTORY_NOTE        = SCHEME + "/pure/historyNote";
    /** 票据详情 */
    public static final  String MY_NOTE_DETAIL           = SCHEME + "/pure/myNoteDetail";
    /** 纯票订单 */
    public static final  String PURE_PURE_ORDER          = SCHEME + "/pure/pureOrder";
    /** 我的票据 */
    public static final  String PURE_MY_NOTE             = SCHEME + "/pure/myNote";
    /** 纯票交易 */
    public static final  String PURE_TRANSACTION         = SCHEME + "/pure/transaction";
    /** 票据上架 */
    public static final  String PURE_NOTE_PUT_ON         = SCHEME + "/pure/notePutOn";
    /** 票据修改 */
    public static final  String PURE_MODIFY_NOTE         = SCHEME + "/pure/modifyNote";
}