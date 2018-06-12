package com.sai628.moviejie.utils;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Pattern;


/**
 * @author Sai
 * @ClassName: HtmlUtil
 * @Description: Html工具类
 * @date 12/06/2018 14:40
 */
public class HtmlUtil
{
    /**
     * 将字符串 originalText 中的 keyword 字符串格式化为 colorString 颜色的字体
     *
     * @param context
     * @param originalText  原始字符串
     * @param keyword       待转换颜色的关键字
     * @param colorStringID 颜色字符串值(型如"#FF000000")对应的资源id
     * @return Spanned
     */
    public static Spanned formatHtml(Context context, String originalText, String keyword, int colorStringID)
    {
        if (TextUtils.isEmpty(originalText))
        {
            return null;
        }

        if (TextUtils.isEmpty(keyword))
        {
            return Html.fromHtml(originalText);
        }

        String colorString = context.getString(colorStringID);
        keyword = StringUtil.escapeExprSpecialWord(keyword);

        String text = Pattern.compile("([" + keyword + "])", Pattern.CASE_INSENSITIVE).matcher(originalText).replaceAll("<font color=" +
                colorString + ">$1</font>");
        return Html.fromHtml(text);
    }
}
