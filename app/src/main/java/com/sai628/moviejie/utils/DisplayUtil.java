package com.sai628.moviejie.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;


/**
 * @author Sai
 * @ClassName: DisplayUtil
 * @Description: 显示尺寸单位转换工具类
 * @date 2013-9-27 下午3:41:26
 */
public class DisplayUtil
{
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return int px值
     */
    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue px值
     * @return int dp值
     */
    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param spValue sp值
     * @return int px值
     */
    public static int sp2px(Context context, float spValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     *
     * @param context 上下文
     * @param pxValue px值
     * @return int sp值
     */
    public static int px2sp(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 获取设备的宽度分辨率[px单位]
     *
     * @param activity
     * @return int 设备宽度分辨率,单位px
     */
    public static int getDeviceWidthPixels(Activity activity)
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics.widthPixels;
    }


    /**
     * 获取设备的高度分辨率[px单位]
     *
     * @param activity
     * @return int 设备高度分辨率,单位px
     */
    public static int getDeviceHeightPixels(Activity activity)
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics.heightPixels;
    }


    /**
     * 获取设备的状态栏高度[px单位]
     *
     * @param activity
     * @return int 设备的状态栏高度,单位px
     */
    public static int getStatusBarHeight(Activity activity)
    {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }


    /**
     * 获取视图的高度（去除状态瘭高度）
     *
     * @param activity
     * @return int
     */
    public static int getViewHeightWithoutStatusBarPixels(Activity activity)
    {
        return getDeviceHeightPixels(activity) - getStatusBarHeight(activity);
    }
}
