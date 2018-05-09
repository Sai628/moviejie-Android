package com.sai628.moviejie.utils;

import android.util.Log;

import com.sai628.moviejie.BuildConfig;


public class LogUtil
{
    private static final boolean LOG_DEBUG = BuildConfig.LOG_DEBUG;


    /**
     * send a INFO log message
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg)
    {
        if (LOG_DEBUG)
        {
            tag = wrapTag(tag);
            Log.i(tag, msg);
        }
    }


    /**
     * send a DEBUG log message
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg)
    {
        if (LOG_DEBUG)
        {
            tag = wrapTag(tag);
            Log.d(tag, msg);
        }
    }


    /**
     * send a ERROR log message
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg)
    {
        if (LOG_DEBUG)
        {
            tag = wrapTag(tag);
            Log.e(tag, msg);
        }
    }


    /**
     * send a VERBOSE log message
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg)
    {
        if (LOG_DEBUG)
        {
            tag = wrapTag(tag);
            Log.v(tag, msg);
        }
    }


    /**
     * send a WARN log message
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg)
    {
        if (LOG_DEBUG)
        {
            tag = wrapTag(tag);
            Log.w(tag, msg);
        }
    }


    /**
     * send a INFO log message
     *
     * @param object Used to identify the source of a log message.
     * @param msg    The message you would like logged.
     */
    public static void i(Object object, String msg)
    {
        i(object.getClass().getSimpleName(), msg);
    }


    /**
     * send a DEBUG log message
     *
     * @param object Used to identify the source of a log message.
     * @param msg    The message you would like logged.
     */
    public static void d(Object object, String msg)
    {
        d(object.getClass().getSimpleName(), msg);
    }


    /**
     * send a ERROR log message
     *
     * @param object Used to identify the source of a log message.
     * @param msg    The message you would like logged.
     */
    public static void e(Object object, String msg)
    {
        e(object.getClass().getSimpleName(), msg);
    }


    /**
     * send a VERBOSE log message
     *
     * @param object Used to identify the source of a log message.
     * @param msg    The message you would like logged.
     */
    public static void v(Object object, String msg)
    {
        v(object.getClass().getSimpleName(), msg);
    }


    /**
     * send a WARN log message
     *
     * @param object Used to identify the source of a log message.
     * @param msg    The message you would like logged.
     */
    public static void w(Object object, String msg)
    {
        w(object.getClass().getSimpleName(), msg);
    }


    // 对Log输出的tag进行包装处理, 添加应用的包名
    private static String wrapTag(String tag)
    {
        return BuildConfig.APPLICATION_ID + " LOG " + (tag != null ? tag : "");
    }
}
