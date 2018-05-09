package com.sai628.moviejie.listener;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.sai628.moviejie.utils.ImeUtil;


public class HiddenKeyboardOnTouchListener implements OnTouchListener
{
    private Activity activity;


    public HiddenKeyboardOnTouchListener(Activity activity)
    {
        this.activity = activity;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        try
        {
            ImeUtil.hideIme(activity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
