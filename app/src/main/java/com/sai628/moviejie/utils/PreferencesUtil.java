package com.sai628.moviejie.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * @author Sai
 * @Description: SharedPreferences配置信息存取工具类
 * @ClassName: PreferencesUtil
 * @date 2013-9-27 下午4:09:25
 */
public class PreferencesUtil
{
    // 存取应用配置信息的文件名
    private static final String SETTINGS_FILE_NAME = "settings_config";


    private static SharedPreferences getPreferences(Context context)
    {
        return context.getApplicationContext().getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_MULTI_PROCESS);
    }


    /**
     * 根据配置键名获取应用配置的 String 值
     *
     * @param context  上下文
     * @param key      配置键名
     * @param defValue 默认值，不存在键名 key 时返回该值
     * @return String 配置键名 key 对应的值
     */
    public static String getString(Context context, String key, String defValue)
    {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getString(key, defValue);
    }


    /**
     * 根据配置键名获取应用配置的 boolean 值
     *
     * @param context  上下文
     * @param key      配置键名
     * @param defValue 默认值，不存在键名 key 时返回该值
     * @return boolean 配置键名 key 对应的值
     */
    public static boolean getBoolean(Context context, String key, Boolean defValue)
    {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getBoolean(key, defValue);
    }


    /**
     * 根据配置键名获取应用配置的 int 值
     *
     * @param context  上下文
     * @param key      配置键名
     * @param defValue 默认值，不存在键名 key 时返回该值
     * @return int 配置键名 key 对应的值
     */
    public static int getInt(Context context, String key, int defValue)
    {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getInt(key, defValue);
    }


    /**
     * 根据配置键名获取应用配置的 long 值
     *
     * @param context  上下文
     * @param key      配置键名
     * @param defValue 默认值，不存在键名 key 时返回该值
     * @return long 配置键名 key 对应的值
     */
    public static long getLong(Context context, String key, long defValue)
    {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getLong(key, defValue);
    }


    /**
     * 根据key-value存储应用的配置信息
     *
     * @param context 上下文
     * @param key     配置键名
     * @param value   配置值
     */
    public static void putString(Context context, String key, String value)
    {
        SharedPreferences prefs = getPreferences(context);
        prefs.edit().putString(key, value).commit();
    }


    /**
     * 根据key-value存储应用的配置信息
     *
     * @param context 上下文
     * @param key     配置键名
     * @param value   配置值
     */
    public static void putInt(Context context, String key, int value)
    {
        SharedPreferences prefs = getPreferences(context);
        prefs.edit().putInt(key, value).commit();
    }


    /**
     * 根据key-value存储应用的配置信息
     *
     * @param context 上下文
     * @param key     配置键名
     * @param value   配置值
     */
    public static void putBoolean(Context context, String key, Boolean value)
    {
        SharedPreferences prefs = getPreferences(context);
        prefs.edit().putBoolean(key, value).commit();
    }


    /**
     * 根据key-value存储应用的配置信息
     *
     * @param context 上下文
     * @param key     配置键名
     * @param value   配置值
     */
    public static void putLong(Context context, String key, Long value)
    {
        SharedPreferences prefs = getPreferences(context);
        prefs.edit().putLong(key, value).commit();
    }


    /**
     * 根据键名 key 去除应用配置中的某项配置
     *
     * @param context 上下文
     * @param key     待去除配置的键名
     */
    public static void remove(Context context, String key)
    {
        SharedPreferences prefs = getPreferences(context);
        prefs.edit().remove(key).commit();
    }
}
