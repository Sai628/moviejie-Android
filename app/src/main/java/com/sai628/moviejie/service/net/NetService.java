package com.sai628.moviejie.service.net;

import android.content.ContentValues;
import android.content.Context;

import com.sai628.moviejie.config.network.APIAddress;
import com.sai628.moviejie.model.HotInfo;
import com.sai628.moviejie.model.LinkDetailInfo;
import com.sai628.moviejie.model.MovieInfo;
import com.sai628.moviejie.model.MovieSimpleInfo;
import com.sai628.moviejie.model.NewInfo;
import com.sai628.moviejie.model.OSTInfo;
import com.sai628.moviejie.model.OSTSimpleInfo;
import com.sai628.moviejie.utils.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Sai
 * @ClassName: NetService
 * @Description: 网络服务类
 * @date 09/05/2018 19:14
 */
public class NetService
{
    /**
     * 获取"首页"信息
     * @param context
     * @param netCallback
     */
    public static void getIndexInfo(Context context, final NetHelper.NetCallback netCallback)
    {
        NetHelper.get(context, APIAddress.API_DOMAIN, netCallback, new NetHelper.SuccessHandler()
        {
            @Override
            public void onSuccess(Context context, ContentValues values, JSONObject jsonObject) throws JSONException
            {
                ArrayList<NewInfo> news = JSONUtil.readModels(jsonObject, "news", NewInfo.class);
                ArrayList<HotInfo> hots = JSONUtil.readModels(jsonObject, "hots", HotInfo.class);

                HashMap<String, Object> data = new HashMap<>();
                data.put("news", news);
                data.put("hots", hots);
                NetHelper.dealWithSuccess(data, netCallback);
            }
        });
    }


    /**
     * 获取"电影/电视剧"详情信息
     * @param context
     * @param movieLink 电影/电视剧链接. 如: "/movie/1c7f93/"
     * @param netCallback
     */
    public static void getMovieInfo(Context context, String movieLink, final NetHelper.NetCallback netCallback)
    {
        NetHelper.get(context, APIAddress.API_DOMAIN + movieLink, netCallback, new NetHelper.SuccessHandler()
        {
            @Override
            public void onSuccess(Context context, ContentValues values, JSONObject jsonObject) throws JSONException
            {
                MovieInfo movieInfo = JSONUtil.readModel(jsonObject, "movie", MovieInfo.class);
                NetHelper.dealWithSuccess(movieInfo, netCallback);
            }
        });
    }


    /**
     * 获取下载链接详情信息
     * @param context
     * @param link 下载详情链接. 如: "/link/AQpkZmx5AP50qQR2ZGHmAmDhZwNhAGD1ZmR=/"
     * @param netCallback
     */
    public static void getLinkDetailInfo(Context context, String link, final NetHelper.NetCallback netCallback)
    {
        NetHelper.get(context, APIAddress.API_DOMAIN + link, netCallback, new NetHelper.SuccessHandler()
        {
            @Override
            public void onSuccess(Context context, ContentValues values, JSONObject jsonObject) throws JSONException
            {
                LinkDetailInfo linkDetailInfo = JSONUtil.readModel(jsonObject, "link_detail", LinkDetailInfo.class);
                NetHelper.dealWithSuccess(linkDetailInfo, netCallback);
            }
        });
    }


    /**
     * 获取原声大碟详情信息
     * @param context
     * @param ostLink 原声大碟详情链接. 如: "/ost/c54d92/"
     * @param netCallback
     */
    public static void getOSTInfo(Context context, String ostLink, final NetHelper.NetCallback netCallback)
    {
        NetHelper.get(context, APIAddress.API_DOMAIN + ostLink, netCallback, new NetHelper.SuccessHandler()
        {
            @Override
            public void onSuccess(Context context, ContentValues values, JSONObject jsonObject) throws JSONException
            {
                OSTInfo ostInfo = JSONUtil.readModel(jsonObject, "ost", OSTInfo.class);
                NetHelper.dealWithSuccess(ostInfo, netCallback);
            }
        });
    }


    /**
     * 获取最新电影列表
     * @param context
     * @param page 分页. 如"p1"表示第1页, "p2"表示第2页. 从"p1"开始索引.
     * @param netCallback
     */
    public static void getNewMovie(Context context, String page, final NetHelper.NetCallback netCallback)
    {
        NetHelper.get(context, APIAddress.NEW_MOVIE + page + "/", netCallback, new NetHelper.SuccessHandler()
        {
            @Override
            public void onSuccess(Context context, ContentValues values, JSONObject jsonObject) throws JSONException
            {
                ArrayList<MovieSimpleInfo> movies = JSONUtil.readModels(jsonObject, "movies", MovieSimpleInfo.class);
                NetHelper.dealWithSuccess(movies, netCallback);
            }
        });
    }


    /**
     * 获取最新电视剧列表
     * @param context
     * @param page 分页. 如"p1"表示第1页, "p2"表示第2页. 从"p1"开始索引.
     * @param netCallback
     */
    public static void getNewTv(Context context, String page, final NetHelper.NetCallback netCallback)
    {
        NetHelper.get(context, APIAddress.NEW_TV + page + "/", netCallback, new NetHelper.SuccessHandler()
        {
            @Override
            public void onSuccess(Context context, ContentValues values, JSONObject jsonObject) throws JSONException
            {
                ArrayList<MovieSimpleInfo> movies = JSONUtil.readModels(jsonObject, "movies", MovieSimpleInfo.class);
                NetHelper.dealWithSuccess(movies, netCallback);
            }
        });
    }


    /**
     * 获取最新原声大碟列表
     * @param context
     * @param page 分页. 如"p1"表示第1页, "p2"表示第2页. 从"p1"开始索引.
     * @param netCallback
     */
    public static void getNewOST(Context context, String page, final NetHelper.NetCallback netCallback)
    {
        NetHelper.get(context, APIAddress.NEW_OST + page + "/", netCallback, new NetHelper.SuccessHandler()
        {
            @Override
            public void onSuccess(Context context, ContentValues values, JSONObject jsonObject) throws JSONException
            {
                ArrayList<OSTSimpleInfo> ostInfos = JSONUtil.readModels(jsonObject, "ost_infos", OSTSimpleInfo.class);
                NetHelper.dealWithSuccess(ostInfos, netCallback);
            }
        });
    }


    public static void search(Context context, String keyword, String page, final NetHelper.NetCallback netCallback)
    {
        String url = String.format("%sq_%s/%s", APIAddress.SEARCH, keyword, page);
        NetHelper.get(context, url, netCallback, new NetHelper.SuccessHandler()
        {
            @Override
            public void onSuccess(Context context, ContentValues values, JSONObject jsonObject) throws JSONException
            {
                ArrayList<MovieSimpleInfo> movies = JSONUtil.readModels(jsonObject, "movies", MovieSimpleInfo.class);
                NetHelper.dealWithSuccess(movies, netCallback);
            }
        });
    }
}
