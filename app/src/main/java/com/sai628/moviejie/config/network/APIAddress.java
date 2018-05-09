package com.sai628.moviejie.config.network;

import com.sai628.moviejie.BuildConfig;


/**
 * API 地址配置信息
 */
public class APIAddress
{
    public static final String API_DOMAIN = "http://" + BuildConfig.API_DOMAIN;  // API域名


    /**
     * 最新电影
     */
    public static final String NEW_MOVIE = API_DOMAIN + "/new/movie/";
    /**
     * 最新电视剧
     */
    public static final String NEW_TV = API_DOMAIN + "/new/tv/";
    /**
     * 最新原声大碟
     */
    public static final String NEW_OST = API_DOMAIN + "/new/ost/";
    /**
     * 搜索电影
     */
    public static final String SEARCH = API_DOMAIN + "/search/";
}
