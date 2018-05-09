package com.sai628.moviejie.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;


public class MyBroadcastReceiver extends BroadcastReceiver
{
    private Handler handler;


    public MyBroadcastReceiver(Handler handler)
    {
        this.handler = handler;
    }


    @Override
    public void onReceive(Context context, Intent intent)
    {
        Message msg = handler.obtainMessage();
        msg.obj = intent;
        handler.sendMessage(msg);
    }
}
