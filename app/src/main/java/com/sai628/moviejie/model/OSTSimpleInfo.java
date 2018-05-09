package com.sai628.moviejie.model;

/**
 * @author Sai
 * @ClassName: OSTSimpleInfo
 * @Description: 原声大碟简略信息model. 对应URL: /new/ost/ 页面中的数据结构
 * @date 09/05/2018 18:07
 */
public class OSTSimpleInfo implements Model
{
    private String movie_name;  // 电影名称
    private String ost_link;  // 原声大碟详情页面链接
    private String banner;  // 封面图URL
    private String res_name;  // 资源名称
    private String res_size;  // 资源大小
    private String country;  // 地区/语言
    private String publish_time;  // 发行时间
    private String file_type;  // 资源格式


    public String getMovie_name()
    {
        return movie_name != null ? movie_name : "";
    }


    public void setMovie_name(String movie_name)
    {
        this.movie_name = movie_name;
    }


    public String getOst_link()
    {
        return ost_link != null ? ost_link : "";
    }


    public void setOst_link(String ost_link)
    {
        this.ost_link = ost_link;
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


    public String getRes_size()
    {
        return res_size != null ? res_size : "";
    }


    public void setRes_size(String res_size)
    {
        this.res_size = res_size;
    }


    public String getCountry()
    {
        return country != null ? country : "";
    }


    public void setCountry(String country)
    {
        this.country = country;
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


    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + ":[" +
                "movie_name=" + getMovie_name() +
                ",ost_link=" + getOst_link() +
                ",banner=" + getBanner() +
                ",res_name=" + getRes_name() +
                ",res_size=" + getRes_size() +
                ",country" + getCountry() +
                ",publish_time" + getPublish_time() +
                ",file_type" + getFile_type() +
                "]";
    }
}
