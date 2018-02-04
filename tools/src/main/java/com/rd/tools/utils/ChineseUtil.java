package com.rd.tools.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/7/28 11:49
 * <p>
 * Description: 中文相关的操作方法
 */
@SuppressWarnings("unused")
public class ChineseUtil {
    /**
     * 检查name是否是中文名
     *
     * @param name
     *         待检测姓名
     */
    public static boolean isChineseName(String name) {
        if (TextUtil.isEmpty(name)) {
            return false;
        } else if (isChinese(name, "^[\\u4e00-\\u9fa5]{2,10}$")) {
            return true;
        }
        return false;
    }

    /**
     * 正则判断中文汉字
     */
    public static boolean isChinese(String strName, String regEx) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(strName);
        return m.find();
    }

    /**
     * 完整的判断中文汉字和符号
     */
    public static boolean isChinese(String strName) {
        for (char c : strName.toCharArray()) {
            if (!isChinese(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判定输入字符是否是汉字
     *
     * @param c
     *         待检测字符
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    /**
     * 获取一个字符串中中文字符的个数
     */
    public static int ChineseLength(String str) {
        Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");
        Matcher m = p.matcher(str);
        int     i = 0;
        while (m.find()) {
            String temp = m.group(0);
            i += temp.length();
        }
        return i;
    }

    /**
     * 判断是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        Pattern p        = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m        = p.matcher(strName);
        String  after    = m.replaceAll("");
        String  temp     = after.replaceAll("\\p{P}", "");
        char[]  ch       = temp.trim().toCharArray();
        float   chLength = 0;
        float   count    = 0;

        for (char c : ch) {
            if (!Character.isLetterOrDigit(c)) {
                if (!ChineseUtil.isChinese(c)) {
                    count = count + 1;
                }
                chLength++;
            }
        }

        float result = count / chLength;
        return result > 0.4;
    }
}