package com.rd.hnlf.network;

import android.text.TextUtils;
import android.util.Log;

import com.rd.hnlf.common.BaseParams;
import com.rd.hnlf.common.Constant;
import com.rd.hnlf.module.user.dataModel.receive.OauthTokenRec;
import com.rd.logic.info.SharedInfo;
import com.rd.tools.encryption.MDUtil;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.DeviceInfoUtil;

import java.io.File;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/12/20 15:04
 * <p/>
 * Description: 网络请求 - 验签工具类
 */
public class SignUtil {
    /** 通用参数 - H5请求时需要用到 ,正常接口请求无需使用 */
    private TreeMap<String, String> commonParamsTreeMap;

    private SignUtil() {
        commonParamsTreeMap = new TreeMap<>();
        commonParamsTreeMap.put(Constant.APP_KEY, BaseParams.APP_KEY);
        commonParamsTreeMap.put(Constant.MOBILE_TYPE, BaseParams.MOBILE_TYPE);
        commonParamsTreeMap.put(Constant.VERSION_NUMBER, DeviceInfoUtil.getVersionName(ContextHolder.getContext()));
    }

    public static SignUtil getInstance() {
        return SignUtilInstance.instance;
    }

    private static class SignUtilInstance {
        static SignUtil instance = new SignUtil();
    }

    /**
     * 添加动态参数
     */
    public Map<String, String> addParams() {
        Map<String, String> map    = new TreeMap<>();
        String              token  = getToken();
        String              userId = getUserId();
        if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(userId)) {
            map.put(Constant.TOKEN, token);
            map.put(Constant.USER_ID, userId);
        }
        String ts = String.valueOf(System.currentTimeMillis() / 1000);
        map.put(Constant.TS, ts);
        map.put(Constant.SIGNA, getSigna(ts));
        return map;
    }

    /**
     * 往 header 中添加动态参数（可以做全参数验签）
     */
    public Map<String, String> signParams(Map<String, String> paramsMap) {
        TreeMap<String, String> map = new TreeMap<>();
        map.putAll(paramsMap);
        map.put(Constant.SIGNA, getSigna(map));
        return paramsMap;
    }

    /**
     * 对 TreeMap 数据进行签名，H5请求使用
     */
    public String getCommonParams(TreeMap<String, String> treeMap) {
        treeMap.putAll(commonParamsTreeMap);
        String ts = String.valueOf(System.currentTimeMillis() / 1000);
        treeMap.put(Constant.TS, ts);
        treeMap.put(Constant.SIGNA, getSigna(ts));
        return getPostParamsStr(treeMap);
    }

    /**
     * 将 Map 拼装成请求字符串
     *
     * @return 返回请求参数
     */
    private String getPostParamsStr(TreeMap map) {
        Iterator      it = map.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        try {
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                sb.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sb.toString().length() > 1) {
            return sb.toString().substring(0, sb.length() - 1);
        } else {
            return sb.toString();
        }
    }

    /**
     * 普通验签
     */
    private String getSigna(String ts) {
        String signa = "";
        try {
            // 请求密钥+时间戳 MD5签名
            signa = MDUtil.encode(MDUtil.TYPE.MD5, (BaseParams.APP_SECRET + ts));
            // 得到的MD5串拼接appkey再次MD5
            signa = MDUtil.encode(MDUtil.TYPE.MD5, signa + BaseParams.APP_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signa;
    }

    /**
     * 对所有参数进行验签
     *
     * @param map
     *         有序请求参数map
     */
    private String getSigna(TreeMap map) {
        String signa = "";
        try {
            Iterator      it = map.entrySet().iterator();
            StringBuilder sb = new StringBuilder();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (entry.getValue() instanceof File)
                    continue;// URLEncoder.encode(, "UTF-8")
                sb.append(entry.getKey()).append("=").append(URLDecoder.decode(entry.getValue().toString(), "UTF-8")).append("|");
            }
            // 所有请求参数排序后的字符串后进行MD5（32）
            // signa = MDUtil.encode(MDUtil.ENCRYPTION_MODE.MD5, sb.toString());
            // 得到的MD5串拼接appsecret再次MD5，所得结果转大写
            String sign;
            if (sb.toString().length() > 1) {
                sign = sb.toString().substring(0, sb.length() - 1);
            } else {
                sign = sb.toString();
            }
            Log.i("待签原文", sign);
            signa = MDUtil.encode(MDUtil.TYPE.MD5, BaseParams.APP_SECRET + getToken() + sign).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signa;
    }

    /**
     * 获取 token
     */
    private String getToken() {
        OauthTokenRec mo = SharedInfo.getInstance().getEntity(OauthTokenRec.class);
        if (mo != null) {
            return mo.getToken();
        }
        return "";
    }

    /**
     * 获取 userId
     */
    private String getUserId() {
        OauthTokenRec mo = SharedInfo.getInstance().getEntity(OauthTokenRec.class);
        if (mo != null) {
            return mo.getUserId();
        }
        return "";
    }
}