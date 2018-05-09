package com.sai628.moviejie.utils;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil
{
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;


    public static void show(Context context, int resId)
    {
        show(context, context.getString(resId));
    }


    public static void show(Context context, int resId, int duration)
    {
        show(context, context.getString(resId), duration);
    }


    public static void show(Context context, CharSequence text)
    {
        show(context, text, LENGTH_SHORT);
    }


    public static void show(Context context, CharSequence text, int duration)
    {
        Toast.makeText(context, text, duration).show();
    }
}
