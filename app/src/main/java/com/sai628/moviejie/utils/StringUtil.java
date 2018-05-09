package com.sai628.moviejie.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Sai
 * @ClassName: StringUtil
 * @Description: 字符串工具类
 * @date 2013-9-27 下午3:49:17
 */
public class StringUtil
{
    /**
     * 判断是否为合法的URL地址
     *
     * @param urlString 待检测的字符串
     * @return boolean
     */
    public static boolean isLegalURL(String urlString)
    {
        if (TextUtils.isEmpty(urlString))
        {
            return false;
        }
        else
        {
            String regex = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            Pattern patt = Pattern.compile(regex);
            Matcher matcher = patt.matcher(urlString);

            return matcher.matches();
        }
    }


    /**
     * 判断两个字符串值是否相等
     *
     * @param str1 参数1
     * @param str2 参数2
     * @return boolean 字符串值相等时返回 true，否则返回 false
     */
    public static boolean equals(String str1, String str2)
    {
        return str1 == str2 || (str1 != null && str1.equals(str2));
    }


    /**
     * 判断两个字符串值是否相等（忽略大小写）
     *
     * @param str1 参数1
     * @param str2 参数2
     * @return boolean 字符串值相等时返回 true，否则返回 false
     */
    public static boolean equalsIgnoreCase(String str1, String str2)
    {
        return str1 != null && str1.equalsIgnoreCase(str2);
    }


    /**
     * 判断源字符串中是否包含指定的字符串
     *
     * @param srcStr  源字符串
     * @param destStr 指定的字符串
     * @return boolean 源字符串包含指定字符串时返回 true,否则返回 false
     */
    public static boolean contains(String srcStr, String destStr)
    {
        return srcStr != null && srcStr.contains(destStr);
    }


    /**
     * 替换字符串,当srcStr为空时,返回原始字符串内容
     *
     * @param srcStr      源字符串
     * @param target      待替换的目标字符串
     * @param replacement 用于替换的字符串
     * @return String
     */
    public static String replace(String srcStr, CharSequence target, CharSequence replacement)
    {
        if (!TextUtils.isEmpty(srcStr))
        {
            return srcStr.replace(target, replacement);
        }

        return srcStr;
    }


    /**
     * 去掉字符串中的空格
     *
     * @param str 待处理的字符串
     * @return String 去掉空格后的字符串或 null(当 str 为 null 时)
     */
    public static String removeblank(String str)
    {
        if (str == null)
        {
            return null;
        }
        else
        {
            return str.replace(" ", "");
        }
    }


    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
    public static String escapeExprSpecialWord(String keyword)
    {
        if (!TextUtils.isEmpty(keyword))
        {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr)
            {
                if (keyword.contains(key))
                {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }

        return keyword;
    }


    /**
     * 截取字符串前面给定长度的字符
     *
     * @param str    待处理的字符串
     * @param length 截取的长度
     * @return String 若给定的长度 length 大于待处理的字符串长度，即返回原字符串；<br/>
     * 否则截取原字符串前面的 length 个字符后再添加"..."字符串返回
     */
    public static String truncateWithLength(String str, int length)
    {
        if (str != null && length > 0 && str.length() > length)
        {
            return str.substring(0, length) + "...";
        }

        return str;
    }


    /**
     * 缩短包含了某关键字符串的字符串(主要用于搜索结果的匹配显示)
     *
     * @param str         待处理的字符串
     * @param keyword     关键字符串
     * @param offset      关键字符串的前缀字符串偏移量.当其真实的前缀字符串长度超过offset的值时,将会缩短为"...abc"的型式
     * @param limitLength 返回的字符串的最大限定长度
     */
    public static String truncateWithKeyword(String str, String keyword, int offset, int limitLength)
    {
        if (TextUtils.isEmpty(str))
        {
            return str;
        }

        if (TextUtils.isEmpty(keyword))
        {
            return str.substring(0, Math.min(str.length(), limitLength));
        }

        int index = str.toLowerCase().indexOf(keyword.toLowerCase());
        if (index > offset)
        {
            int start = index - offset;
            int end = Math.min(str.length(), start + limitLength);
            return "..." + str.substring(start, end);
        }
        else
        {
            return str.substring(0, Math.min(str.length(), limitLength));
        }
    }


    /**
     * 获取obj对象的toString()字符串.当obj==null时,返回""
     *
     * @param obj
     * @return String
     */
    public static String getString(Object obj)
    {
        return (obj != null ? obj.toString() : "");
    }


    /**
     * 获取非空的URL. 若 url 参数为 null 或者空字符串时, 会返回 "http://"
     * @param url
     * @return
     */
    public static String getNotEmptyUrl(String url)
    {
        if (url == null || url.trim().length() == 0)
        {
            return "http://";
        }

        return url;
    }


    /**
     * 返回长度为 strLength 的随机数，在前面补0
     *
     * @param strLength 随机数字的长度,最大支持长度为9
     * @return String
     */
    public static String getFixLenthRandomString(int strLength)
    {
        Random rm = new Random();

        int pross = (int) ((1 + rm.nextDouble()) * Math.pow(10, strLength)); // 获得随机数
        String fixLenthString = String.valueOf(pross); // 将获得的获得随机数转化为字符串

        return fixLenthString.substring(1, strLength + 1); // 返回固定的长度的随机数
    }


    /**
     * 获取两个数的百分比（保留两位小数）
     *
     * @param num   分子
     * @param total 分母
     * @return String 型如："13.34%" 的字符串
     */
    public static String getPercent(int num, int total)
    {
        DecimalFormat fnum = new DecimalFormat("##0.00");
        return fnum.format(num / (float) total * 100) + "%";
    }
}
