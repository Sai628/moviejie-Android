package com.sai628.moviejie.model;

/**
 * @author Sai
 * @ClassName: LinkInfo
 * @Description: 下载链接信息model. 对应URL: /movie/<movie_id>/ 页面中下载链接列表每项的数据结构
 * @date 09/05/2018 18:22
 */
public class LinkInfo
{
    private String name;  // 资源名称
    private String size;  // 资源大小
    private String dimen;  // 资源尺寸
    private String format;  // 资源格式
    private String link;  // 资源下载页面链接
    private String episode;  // 资源所属分集


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


    public String getDimen()
    {
        return dimen != null ? dimen : "";
    }


    public void setDimen(String dimen)
    {
        this.dimen = dimen;
    }


    public String getFormat()
    {
        return format != null ? format : "";
    }


    public void setFormat(String format)
    {
        this.format = format;
    }


    public String getLink()
    {
        return link != null ? link : "";
    }


    public void setLink(String link)
    {
        this.link = link;
    }


    public String getEpisode()
    {
        return episode != null ? episode : "";
    }


    public void setEpisode(String episode)
    {
        this.episode = episode;
    }


    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + ":[" +
                "name=" + getName() +
                ",size=" + getSize() +
                ",dimen=" + getDimen() +
                ",format=" + getFormat() +
                ",link=" + getLink() +
                ",episode=" + getEpisode() +
                "]";
    }
}
