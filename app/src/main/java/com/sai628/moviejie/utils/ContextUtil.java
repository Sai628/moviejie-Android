package com.sai628.moviejie.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;

import com.sai628.moviejie.activity.info.MovieInfoActivity;


public class ContextUtil
{
    // 跳转到"电影/电视剧"信息视图
    public static void readMovieInfo(Activity activity, String movieLink)
    {
        Intent intent = new Intent(activity, MovieInfoActivity.class);
        intent.putExtra(MovieInfoActivity.EXTRA_MOVIE_LINK, movieLink);
        activity.startActivity(intent);
    }


    // 注销广播接收器
    public static void unregisterReceiver(Context context, BroadcastReceiver receiver)
    {
        if (context != null && receiver != null)
        {
            context.unregisterReceiver(receiver);
        }
    }


    //回系统主页
    public static void goToHomeCategory(Context context)
    {
        Intent ntent = new Intent(Intent.ACTION_MAIN);
        ntent.addCategory(Intent.CATEGORY_HOME);
        ntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(ntent);
    }


    // 打开URL(调用系统组件)
    public static void openURL(Context context, String url)
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
            context.startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
