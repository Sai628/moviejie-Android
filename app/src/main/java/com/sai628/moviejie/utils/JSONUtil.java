package com.sai628.moviejie.utils;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * @author Sai
 * @ClassName: JSONUtil
 * @Description: JSON处理工具类
 * @date 2013-9-27 下午4:53:27
 */
public class JSONUtil
{
    private static final String TAG = JSONUtil.class.getSimpleName();


    /**
     * 将JSON转换为实体类对象
     *
     * @param jsonObject 原始JSONObject对象
     * @param name       待转换的对象对应的JSONObject键名
     * @param clazz      实体类的类名
     * @return T 实体类对象
     */
    public static <T> T readModel(JSONObject jsonObject, String name, Class<T> clazz)
    {
        String json = jsonObject.optString(name);
        return readModel(json, clazz);
    }


    /**
     * 将JSON转换为实体类对象
     *
     * @param json  待转换的JSON数据
     * @param clazz 实体类的类名
     * @return T 实体类对象
     */
    public static <T> T readModel(String json, Class<T> clazz)
    {
        try
        {
            Gson gson = createGson();
            return gson.fromJson(json, clazz);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.d(TAG, "readModel:" + e.toString());
            return null;
        }
    }


    /**
     * 将JSON转换为实体类，以实体类对象列表的形式返回
     *
     * @param jsonObject 原始JSONObject对象
     * @param name       待转换的数组对象对应的JSONObject键名
     * @param clazz      实体类列表的类名
     * @return ArrayList&lt;T&gt; 实体类对象数组
     */
    public static <T> ArrayList<T> readModels(JSONObject jsonObject, String name, Class<T[]> clazz)
    {
        String jsonArray = jsonObject.optString(name);
        return readModels(jsonArray, clazz);
    }


    /**
     * 将JSON转换为实体类，以实体类对象列表的形式返回
     *
     * @param jsonArray 待转换的JSONArray字体串
     * @param clazz     实体类列表的类名
     * @return ArrayList&lt;T&gt; 实体类对象数组
     */
    public static <T> ArrayList<T> readModels(String jsonArray, Class<T[]> clazz)
    {
        try
        {
            Gson gson = createGson();
            T[] objects = gson.fromJson(jsonArray, clazz);
            return CollectionUtil.toArrayList(objects);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.d(TAG, "readModels:" + e.toString());
            return null;
        }
    }


    /**
     * 将JSON字符串数组转换为实体类，以实体类对象列表的形式返回
     *
     * @param jsonArray 待转换的JSON字符串数组
     * @param clazz     实体类的类名
     * @return ArrayList&lt;T&gt; 实体类对象数组
     */
    public static <T> ArrayList<T> readModels(ArrayList<String> jsonArray, Class<T> clazz)
    {
        if (jsonArray == null || jsonArray.size() == 0)
        {
            return null;
        }

        try
        {
            Gson gson = createGson();
            ArrayList<T> list = new ArrayList<>(jsonArray.size());
            for (String json : jsonArray)
            {
                T t = gson.fromJson(json, clazz);
                list.add(t);
            }
            return list;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.d(TAG, "readModels:" + e.toString());
            return null;
        }
    }


    /**
     * 将实体对象转换为JSON格式数据
     *
     * @param object 要转换的实体对象
     * @return String JSON格式数据
     */
    public static String writeModelToJSON(Object object)
    {
        return new Gson().toJson(object);
    }


    private static Gson createGson()
    {
        return new Gson();
    }
}
