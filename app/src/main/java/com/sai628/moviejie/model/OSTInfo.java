package com.sai628.moviejie.model;

import com.sai628.moviejie.utils.StringUtil;

import java.util.List;


/**
 * @author Sai
 * @ClassName: OSTInfo
 * @Description: 原声大碟信息model. 对应URL: /ost/<ost_id>/ 页面中的数据结构
 * @date 09/05/2018 18:44
 */
public class OSTInfo implements Model
{
    private String movie_name;  // 电影名称
    private String movie_link;  // 电影详情页面链接
    private String banner;  // 封面图URL
    private String res_name;  // 资源名称
    private String country;  // 地区
    private String language;  // 语言
    private String publish_time;  // 发行时间
    private String file_type;  // 资源格式
    private List<String> track_list;  // 专辑曲目列表
    private List<LinkInfo> links;  // 下载页面链接列表


    public String getMovie_name()
    {
        return movie_name != null ? movie_name : "";
    }


    public void setMovie_name(String movie_name)
    {
        this.movie_name = movie_name;
    }


    public String getMovie_link()
    {
        return movie_link != null ? movie_link : "";
    }


    public void setMovie_link(String movie_link)
    {
        this.movie_link = movie_link;
    }


    public String getBanner()
    {
        return banner != null ? banner : "";
    }


    public void setBanner(String banner)
    {
        this.banner = banner;
    }


    public String getRes_name()
    {
        return res_name != null ? res_name : "";
    }


    public void setRes_name(String res_name)
    {
        this.res_name = res_name;
    }


    public String getCountry()
    {
        return country != null ? country : "";
    }


    public void setCountry(String country)
    {
        this.country = country;
    }


    public String getLanguage()
    {
        return language != null ? language : "";
    }


    public void setLanguage(String language)
    {
        this.language = language;
    }


    public String getPublish_time()
    {
        return publish_time != null ? publish_time : "";
    }


    public void setPublish_time(String publish_time)
    {
        this.publish_time = publish_time;
    }


    public String getFile_type()
    {
        return file_type != null ? file_type : "";
    }


    public void setFile_type(String file_type)
    {
        this.file_type = file_type;
    }


    public List<String> getTrack_list()
    {
        return track_list;
    }


    public void setTrack_list(List<String> track_list)
    {
        this.track_list = track_list;
    }


    public List<LinkInfo> getLinks()
    {
        return links;
    }


    public void setLinks(List<LinkInfo> links)
    {
        this.links = links;
    }


    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + ":[" +
                "movie_name=" + getMovie_name() +
                ",movie_link=" + getMovie_link() +
                ",banner=" + getBanner() +
                ",res_name=" + getRes_name() +
                ",country" + getCountry() +
                ",language=" + getLanguage() +
                ",publish_time" + getPublish_time() +
                ",file_type" + getFile_type() +
                ",track_list" + StringUtil.getString(track_list) +
                ",links" + StringUtil.getString(links) +
                "]";
    }
}
