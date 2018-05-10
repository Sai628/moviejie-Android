package com.sai628.moviejie.model;

/**
 * @author Sai
 * @ClassName: ResourceInfo
 * @Description: 资源信息model. 对应于首页的"更新/热门"资源数据结构
 * @date 09/05/2018 17:47
 */
public class ResourceInfo implements Model
{
    public enum Type
    {
        NEWS, HOT
    }

    private String title;  // 名称
    private String movie_link;  // 电影/电视剧页面链接
    private String link;  // 下载页面链接
    private String size;  // 大小
    private String rating;  // 评分
    private String category;  // 类别名称
    private Type resourceType; // 资源类型


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


    public String getLink()
    {
        return link != null ? link : "";
    }


    public void setLink(String link)
    {
        this.link = link;
    }


    public String getSize()
    {
        return size != null ? size : "";
    }


    public void setSize(String size)
    {
        this.size = size;
    }


    public String getRating()
    {
        return rating != null ? rating : "";
    }


    public void setRating(String rating)
    {
        this.rating = rating;
    }


    public String getCategory()
    {
        return category != null ? category : "";
    }


    public void setCategory(String category)
    {
        this.category = category;
    }


    public Type getResourceType()
    {
        return resourceType;
    }


    public void setResourceType(Type resourceType)
    {
        this.resourceType = resourceType;
    }


    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + ":[" +
                "title=" + getTitle() +
                ",movie_link=" + getMovie_link() +
                ",link=" + getLink() +
                ",size=" + getSize() +
                ",rating" + getRating() +
                ",category" + getCategory() +
                ",resourceType" + getResourceType() +
                "]";
    }
}
