package com.sai628.moviejie.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


/**
 * @author Sai
 * @ClassName: ImeUtil
 * @Description: 系统输入键盘工具类
 * @date Aug 5, 2015 11:19:59 PM
 */
public class ImeUtil
{
    /**
     * 使View执行点击事件(用于自动点击输入框弹出软键盘)
     *
     * @param view
     */
    public static void setKeyboardFocus(final View view)
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                view.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                view.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            }
        }, 100L);
    }


    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void hideIme(Activity context)
    {
        if (context != null)
        {
            final View view = context.getWindow().peekDecorView();
            if (view != null && view.getWindowToken() != null)
            {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    public static void hideImeDelayed(final Activity context, Handler handler)
    {
        if (context != null && handler != null)
        {
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    hideIme(context);
                }
            }, 100L);
        }
    }


    /**
     * 检测软键盘是否已显示
     *
     * @param context
     * @return boolean
     */
    public static boolean isImeShowing(Context context)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

}
