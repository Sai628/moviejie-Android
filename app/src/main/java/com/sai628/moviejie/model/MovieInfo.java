package com.sai628.moviejie.model;

import com.sai628.moviejie.utils.StringUtil;

import java.util.HashMap;
import java.util.List;


/**
 * @author Sai
 * @ClassName: MovieInfo
 * @Description: 电影/电视剧信息model. 对应URL: /movie/<movie_id>/ 页面中的数据结构
 * @date 09/05/2018 18:28
 */
public class MovieInfo implements Model
{
    private String title;  // 标题
    private String banner;  // 封面图URL
    private String directors;  // 导演
    private String writers;  // 编剧
    private String stars;  // 主演
    private String genres;  // 类型
    private String country;  // 国家/地区
    private String release_date;  // 上映日期
    private String runtime;  // 片长
    private String akaname;  // 又名
    private String star;  // 评分
    private String story;  // 剧情简介
    private List<HashMap<String, String>> episode_filters;  // 分集查看过滤列表
    private List<LinkInfo> links;  // 下载页面链接列表
    private List<ResourceInfo> related_resources;  // 相关资源列表
    private List<ResourceInfo> recommended_resources;  // 推荐资源列表


    public String getTitle()
    {
        return title != null ? title : "";
    }


    public void setTitle(String title)
    {
        this.title = title;
    }


    public String getBanner()
    {
        return banner != null ? banner : "";
    }


    public void setBanner(String banner)
    {
        this.banner = banner;
    }


    public String getDirectors()
    {
        return directors != null ? directors : "";
    }


    public void setDirectors(String directors)
    {
        this.directors = directors;
    }


    public String getWriters()
    {
        return writers != null ? writers : "";
    }


    public void setWriters(String writers)
    {
        this.writers = writers;
    }


    public String getStars()
    {
        return stars != null ? stars : "";
    }


    public void setStars(String stars)
    {
        this.stars = stars;
    }


    public String getGenres()
    {
        return genres != null ? genres : "";
    }


    public void setGenres(String genres)
    {
        this.genres = genres;
    }


    public String getCountry()
    {
        return country != null ? country : "";
    }


    public void setCountry(String country)
    {
        this.country = country;
    }


    public String getRelease_date()
    {
        return release_date != null ? release_date : "";
    }


    public void setRelease_date(String release_date)
    {
        this.release_date = release_date;
    }


    public String getRuntime()
    {
        return runtime != null ? runtime : "";
    }


    public void setRuntime(String runtime)
    {
        this.runtime = runtime;
    }


    public String getAkaname()
    {
        return akaname != null ? akaname : "";
    }


    public void setAkaname(String akaname)
    {
        this.akaname = akaname;
    }


    public String getStar()
    {
        return star != null ? star : "";
    }


    public void setStar(String star)
    {
        this.star = star;
    }


    public String getStory()
    {
        return story != null ? story : "";
    }


    public void setStory(String story)
    {
        this.story = story;
    }


    public List<HashMap<String, String>> getEpisode_filters()
    {
        return episode_filters;
    }


    public void setEpisode_filters(List<HashMap<String, String>> episode_filters)
    {
        this.episode_filters = episode_filters;
    }


    public List<LinkInfo> getLinks()
    {
        return links;
    }


    public void setLinks(List<LinkInfo> links)
    {
        this.links = links;
    }


    public List<ResourceInfo> getRelated_resources()
    {
        return related_resources;
    }


    public void setRelated_resources(List<ResourceInfo> related_resources)
    {
        this.related_resources = related_resources;
    }


    public List<ResourceInfo> getRecommended_resources()
    {
        return recommended_resources;
    }


    public void setRecommended_resources(List<ResourceInfo> recommended_resources)
    {
        this.recommended_resources = recommended_resources;
    }


    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + ":[" +
                "title=" + getTitle() +
                ",banner=" + getBanner() +
                ",directors=" + getDirectors() +
                ",writers=" + getWriters() +
                ",stars=" + getStars() +
                ",genres=" + getGenres() +
                ",country" + getCountry() +
                ",release_date=" + getRelease_date() +
                ",runtime=" + getRuntime() +
                ",akaname=" + getAkaname() +
                ",star" + getStar() +
                ",story=" + getStory() +
                ",episode_filters=" + StringUtil.getString(episode_filters) +
                ",links=" + StringUtil.getString(links) +
                ",related_resources=" + StringUtil.getString(related_resources) +
                ",recommended_resources=" + StringUtil.getString(recommended_resources) +
                "]";
    }
}
