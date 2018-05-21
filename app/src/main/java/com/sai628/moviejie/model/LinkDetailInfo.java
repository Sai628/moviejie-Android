package com.sai628.moviejie.model;

/**
 * @author Sai
 * @ClassName: LinkDetailInfo
 * @Description: 下载链接详情信息model. 对应URL: /link/<link_id>/ 页面中的数据结构
 * @date 09/05/2018 18:19
 */
public class LinkDetailInfo
{
    private String movie_title;  // 电影标题
    private String movie_link;  // 电影/电视剧页面链接
    private String name;  // 资源名称
    private String size;  // 资源大小
    private String download_link;  // 资源下载链接


    public String getMovie_title()
    {
        return movie_title != null ? movie_title : "";
    }


    public void setMovie_title(String movie_title)
    {
        this.movie_title = movie_title;
    }


    public String getMovie_link()
    {
        return movie_link != null ? movie_link : "";
    }


    public void setMovie_link(String movie_link)
    {
        this.movie_link = movie_link;
    }


    public String getName()
    {
        return name != null ? name : "";
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public String getSize()
    {
        return size != null ? size : "";
    }


    public void setSize(String size)
    {
        this.size = size;
    }


    public String getDownload_link()
    {
        return download_link != null ? download_link : "";
    }


    public void setDownload_link(String download_link)
    {
        this.download_link = download_link;
    }


    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + ":[" +
                "movie_title=" + getMovie_title() +
                ",movie_link=" + getMovie_link() +
                ",name=" + getName() +
                ",size=" + getSize() +
                ",download_link=" + getDownload_link() +
                "]";
    }
}
