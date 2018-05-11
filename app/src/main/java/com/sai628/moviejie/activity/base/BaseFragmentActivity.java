package com.sai628.moviejie.activity.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.sai628.moviejie.utils.DisplayUtil;


/**
 * 基础FragmentActivity
 */
public class BaseFragmentActivity extends FragmentActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        DisplayUtil.setupStatusBar(this, Color.WHITE);
        super.onCreate(savedInstanceState);
    }


    protected FragmentActivity getThis()
    {
        return this;
    }
}
