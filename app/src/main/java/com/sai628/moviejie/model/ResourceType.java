package com.sai628.moviejie.model;

import com.sai628.moviejie.utils.StringUtil;

import java.util.List;


/**
 * @author Sai
 * @ClassName: ResourceType
 * @Description: 资源类型
 * @date 09/05/2018 17:53
 */
public class ResourceType
{
    protected String category;  // 类别名称
    protected List<ResourceInfo> resources;  // 资源列表


    public String getCategory()
    {
        return category != null ? category : "";
    }


    public void setCategory(String category)
    {
        this.category = category;
    }


    public List<ResourceInfo> getResources()
    {
        return resources;
    }


    public void setResources(List<ResourceInfo> resources)
    {
        this.resources = resources;
    }


    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + ":[" +
                "category=" + getCategory() +
                ",resources=" + StringUtil.getString(resources) +
                "]";
    }
}
