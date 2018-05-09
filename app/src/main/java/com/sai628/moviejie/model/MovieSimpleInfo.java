package com.sai628.moviejie.model;

/**
 * @author Sai
 * @ClassName: MovieSimpleInfo
 * @Description: 电影/电视剧简略信息model. 对应URL: /new/movie/ 与 /new/tv/ 页面中的数据结构
 * @date 09/05/2018 18:07
 */
public class MovieSimpleInfo implements Model
{
    private String title;  // 标题
    private String movie_link;  // 电影详情页面链接
    private String banner;  // 封面图URL
    private String genres;  // 类型
    private String country;  // 国家/地区
    private String star;  // 评分


    public String getTitle()
    {
        return title != null ? title : "";
    }


    public void setTitle(String title)
    {
        this.title = title;
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


    public String getStar()
    {
        return star != null ? star : "";
    }


    public void setStar(String star)
    {
        this.star = star;
    }


    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + ":[" +
                "title=" + getTitle() +
                ",movie_link=" + getMovie_link() +
                ",banner=" + getBanner() +
                ",genres=" + getGenres() +
                ",country" + getCountry() +
                ",star" + getStar() +
                "]";
    }
}
