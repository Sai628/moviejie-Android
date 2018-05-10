package com.sai628.moviejie;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.sai628.moviejie.service.net.NetHelper;
import com.sai628.moviejie.service.net.NetService;
import com.sai628.moviejie.utils.LogUtil;
import com.sai628.moviejie.utils.StringUtil;
import com.sai628.moviejie.utils.ThreadUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * @author Sai
 * @ClassName: NetServiceTest
 * @Description: 网络服务测试类
 * @date 10/05/2018 00:29
 */
@RunWith(AndroidJUnit4.class)
public class NetServiceTest
{
    private Context context;


    @Before
    public void setUp() throws Exception
    {
        context = InstrumentationRegistry.getTargetContext();
    }


    @After
    public void waitForMe() throws Exception
    {
        ThreadUtil.safeSleep(3000);
    }


    @Test
    public void getIndexInfo()
    {
        NetService.getIndexInfo(context, netCallback);
    }


    @Test
    public void getMovieInfo()
    {
        NetService.getMovieInfo(context, "/movie/1c7f93/", netCallback);
    }


    @Test
    public void getLinkDetailInfo()
    {
        NetService.getLinkDetailInfo(context, "/link/AQpkZmx5AP50qQR2ZGHmAmDhZwNhAGD1ZmR=/", netCallback);
    }


    @Test
    public void getOSTInfo()
    {
        NetService.getOSTInfo(context, "/ost/c54d92/", netCallback);
    }


    @Test
    public void getNewMovie()
    {
        NetService.getNewMovie(context, "p1", netCallback);
    }


    @Test
    public void getNewTv()
    {
        NetService.getNewTv(context, "p1", netCallback);
    }


    @Test
    public void getNewOST()
    {
        NetService.getNewOST(context, "p1", netCallback);
    }


    @Test
    public void search()
    {
        NetService.search(context, "你好", "p1", netCallback);
    }


    private NetHelper.NetCallback netCallback = new NetHelper.NetCallback()
    {
        @Override
        public void onSuccess(Object data)
        {
            LogUtil.d("onSuccess", StringUtil.getString(data));
        }


        @Override
        public void onError(int errorCode, String errorMsg)
        {
            LogUtil.d("onError", errorCode + ": " + StringUtil.getString(errorMsg));
        }


        @Override
        public void onFailure(String failureMsg)
        {
            LogUtil.d("onFailure", StringUtil.getString(failureMsg));
        }
    };
}
