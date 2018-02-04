package com.rd.hnlf.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 10:30
 * <p/>
 * Description:
 */
public class InputCheck {
    /**
     * 检验长度
     */
    public static boolean checkLength(String str, int length) {
        return !TextUtils.isEmpty(str) && str.length() == length;
    }

    /**
     * 验证密码规则，是否由8-16为数字+字母组成
     *
     * @param password
     *         待检测密码
     *
     * @return <p />
     * true - 符合密码规则; <p />
     * false - 不符合密码规则 <p />
     */
    public static boolean checkPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        Pattern regex   = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$).{8,16}$");
        Matcher matcher = regex.matcher(password);
        return matcher.matches();
    }

    /**
     * 检验验证码是否符合规则
     *
     * @param code
     *         待检测验证码
     *
     * @return <p />
     * true - 符合验证码规则 <p />
     * false - 不符合验证码规则 <p />
     */
    public static boolean checkCode(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        Pattern regex   = Pattern.compile("^\\d{4,6}$");
        Matcher matcher = regex.matcher(code);
        return matcher.matches();
    }

    /**
     * 校验银行卡号是否符合规则
     *
     * @param bankcard
     *         待检测银行卡号
     *
     * @return <p />
     * true - 符合验证码规则 <p />
     * false - 不符合验证码规则 <p />
     */
    public static boolean checkBankcard(String bankcard) {
        return !TextUtils.isEmpty(bankcard) && bankcard.length() >= 8 && bankcard.length() <= 35;
    }
}