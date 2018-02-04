package com.rd.network.utils;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.rd.network.annotation.EncryptionType;
import com.rd.network.annotation.SerializedEncryption;
import com.rd.network.annotation.SerializedIgnore;
import com.rd.network.annotation.SerializedTrim;
import com.rd.tools.encryption.Base64;
import com.rd.tools.encryption.MDUtil;
import com.rd.tools.encryption.RSAUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.security.Key;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/8/10 13:57
 * <p/>
 * Description: 将请求内容，转换成服务器可解析的内容
 * 如果使用RSA加密，则需要在Application中调用 SerializedUtil.init(...)方法
 */
@SuppressWarnings("unused")
public class SerializedUtil {
    // RSA 加密密钥
    private static Key RSA_KEY = null;

    /**
     * 密钥初始化
     */
    public static void init(Key key) {
        RSA_KEY = key;
    }

    /**
     * 转换成服务器可解析的请求
     *
     * @param args
     *         请求内容
     */
    public static Object[] convertToRequestContent(Object args, Field field) throws Exception {
        SerializedIgnore serializedIgnore = field.getAnnotation(SerializedIgnore.class);
        if (null != serializedIgnore) {
            return null;
        }

        SerializedName serializedName = field.getAnnotation(SerializedName.class);
        String         key;
        if (null != serializedName) {
            key = serializedName.value();
        } else {
            key = field.getName();
        }

        Object obj = field.get(args);
        if (TextUtils.isEmpty(key) || null == obj) {
            return null;
        }

        String value = obj.toString();
        if (TextUtils.isEmpty(value)) {
            return null;
        }

        if (obj instanceof File) {
            return new Object[]{key, obj};
        } else {
            SerializedTrim serializedTrim = field.getAnnotation(SerializedTrim.class);
            if (null != serializedTrim) {
                value = value.replaceAll("\\s*", "");
            }

            SerializedEncryption serializedEncryption = field.getAnnotation(SerializedEncryption.class);
            if (null != serializedEncryption) {
                if (null != RSA_KEY && serializedEncryption.type() == EncryptionType.RSA_BASE_64) {
                    value = RSAUtil.Base64Encrypt(RSA_KEY, value);
                } else if (null != RSA_KEY && serializedEncryption.type() == EncryptionType.RSA_BCD) {
                    value = RSAUtil.BCDEncrypt(RSA_KEY, value);
                } else if (serializedEncryption.type() == EncryptionType.MD5) {
                    value = MDUtil.encode(MDUtil.TYPE.MD5, value).toUpperCase();
                } else if (serializedEncryption.type() == EncryptionType.MD5_2) {
                    value = MDUtil.encode(MDUtil.TYPE.MD5, MDUtil.encode(MDUtil.TYPE.MD5, value));
                } else if (serializedEncryption.type() == EncryptionType.BASE_64) {
                    value = Base64.encode(value.getBytes());
                }
            }
            return new Object[]{key, value};
        }
    }
}