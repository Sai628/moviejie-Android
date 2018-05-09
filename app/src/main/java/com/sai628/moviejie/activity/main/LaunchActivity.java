package com.sai628.moviejie.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.sai628.moviejie.R;
import com.sai628.moviejie.activity.base.BaseActivity;
import com.sai628.moviejie.config.Constants;
import com.sai628.moviejie.utils.ThreadUtil;

import java.lang.ref.WeakReference;


public class LaunchActivity extends BaseActivity
{
    private MyHandler handler;


    static class MyHandler extends Handler
    {
        WeakReference<LaunchActivity> reference;


        MyHandler(LaunchActivity activity)
        {
            reference = new WeakReference<>(activity);
        }


        @Override
        public void handleMessage(Message msg)
        {
            LaunchActivity activity = reference.get();
            if (activity != null && !activity.isFinishing())
            {
                activity.goToMainActivity();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_view);

        handler = new MyHandler(this);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        new Thread()
        {
            @Override
            public void run()
            {
                ThreadUtil.safeSleep(Constants.FLASH_TIME);
                handler.sendEmptyMessage(0);
            }
        }.start();
    }


    private void goToMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
