package com.sai628.moviejie.activity.base;

import android.support.multidex.MultiDexApplication;

import com.sai628.moviejie.service.net.UnsafeOkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;


public class AppContext extends MultiDexApplication
{
    private static AppContext instance;


    public void onCreate()
    {
        super.onCreate();
        doInit();
    }


    public synchronized static AppContext getInstance()
    {
        return instance;
    }


    private void doInit()
    {
        instance = this;
        initPicasso();
    }


    private void initPicasso()
    {
        try
        {
            // 用自定义的下载组件, 替换掉 Picasso 的下载组件. 用于忽略 HTTPS 证书的安全验证问题
            Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
            builder.downloader(new OkHttpDownloader(UnsafeOkHttpClient.getUnsafeOkHttpClient()));
            Picasso picasso = builder.build();

            Picasso.setSingletonInstance(picasso);
        }
        catch (IllegalStateException e)
        {
            e.printStackTrace();
        }
    }
}
