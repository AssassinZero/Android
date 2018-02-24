package com.rd.hnlf.common;

import com.rd.hnlf.BuildConfig;
import com.rd.network.annotation.EncryptionType;
import com.rd.tools.utils.AndroidUtil;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/23 15:22
 * <p>
 * Description:
 */
public class BaseParams {
    /** 是否是debug模式 */
    public static final  boolean IS_DEBUG              = BuildConfig.DEBUG;
    /** scheme */
    private static final String  URI_SCHEME            = "http://";
    /** 测试服务器地址 */
    // private static final String  URI_AUTHORITY_BETA    = "10.10.1.28:8080"; // WLB
    // private static final String  URI_AUTHORITY_BETA    = "10.10.1.113:8080";// WDK
    // private static final String  URI_AUTHORITY_BETA    = "10.10.1.126:8084";// HWP
    // private static final String  URI_AUTHORITY_BETA    = "10.10.1.194:8080";//
    // private static final String  URI_AUTHORITY_BETA    = "172.16.90.103:8084";
    //private static final String  URI_AUTHORITY_BETA    = "118.31.11.234:8080";
    //private static final String URI_AUTHORITY_BETA = "172.16.0.103:8080";
     private static final String  URI_AUTHORITY_BETA    = "api.tankwang.com:8080";
    // private static final String  URI_AUTHORITY_BETA    = "202.107.227.242:10384";
    /** 正式服务器地址 */
    // private static final String  URI_AUTHORITY_RELEASE = "172.16.90.103:8084";
    //private static final String  URI_AUTHORITY_RELEASE = "172.16.0.103:8080";
//    private static final String  URI_AUTHORITY_RELEASE = "api.tankwang.com:8080";
    private static final String  URI_AUTHORITY_RELEASE = "192.168.200.108:8080";   //wangruixiang
    // private static final String  URI_AUTHORITY_RELEASE = "202.107.227.242:10384";
    /** 服务器地址 */
//    private static final String  URI_AUTHORITY         = IS_DEBUG ? URI_AUTHORITY_BETA : URI_AUTHORITY_RELEASE;
    private static final String  URI_AUTHORITY         = URI_AUTHORITY_RELEASE;
    /** 模块名称 */
    private static final String  URI_PATH              = "/";
    /** 请求地址(必须以"/"结尾) */
    public static final  String  URI                   = URI_SCHEME + URI_AUTHORITY + URI_PATH;
    /** app_key */
    public static final  String  APP_KEY               = "1d8428fd29ae487afd387df0d2747375";
    /** app_secret */
    public static final  String  APP_SECRET            = "b4eb85269431edf2503cce20d9ba5861";
    /** ios传“1”，安卓传“2” */
    public static final  String  MOBILE_TYPE           = "2";
    /** 根路径 */
    public static final  String  ROOT_PATH             = AndroidUtil.getSDPath() + "/hnlf";
    /** 照片文件文件保存路径 */
    public static final  String  PHOTO_PATH            = ROOT_PATH + "/photo";
    /** SP文件名 */
    public static final  String  SP_NAME               = "basic_params";
    /** 数据库名称 */
    public static final  String  DATABASE_NAME         = "hnlf_db";
    /** 密码加密规则 */
    public static final  int     ENCRYPTION_MODE       = EncryptionType.DEFAULT;
}