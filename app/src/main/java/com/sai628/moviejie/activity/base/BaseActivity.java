package com.sai628.moviejie.activity.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sai628.moviejie.utils.DisplayUtil;


/**
 * 基础Activity
 */
public class BaseActivity extends Activity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        DisplayUtil.setupStatusBar(this, Color.WHITE);
        super.onCreate(savedInstanceState);
    }


    protected Activity getThis()
    {
        return this;
    }
}
