package com.sai628.moviejie.utils;

public class ThreadUtil
{
    public static void safeSleep(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
