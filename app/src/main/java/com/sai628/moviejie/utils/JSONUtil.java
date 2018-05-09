package com.sai628.moviejie.utils;

import com.sai628.moviejie.model.Model;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;


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
     * 将JSON数据转换为实体类对象
     *
     * @param jsonObject 原始JSONObject对象
     * @param name       待转换的对象对应的JSONObject键名
     * @param clazz      实体类的类名.需为继承 Model 的类
     * @return T 实体类对象
     */
    public static <T extends Model> T readModel(JSONObject jsonObject, String name, Class<T> clazz)
    {
        String json = null;
        try
        {
            json = jsonObject.getJSONObject(name).toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.d(TAG, "readModel:" + e.toString());
            return null;
        }

        return readModel(json, clazz);
    }


    /**
     * 将JSON数据转换为实体类对象
     *
     * @param json  待转换的JSON数据
     * @param clazz 实体类的类名.需为继承 Model 的类
     * @return T 实体类对象
     */
    public static <T extends Model> T readModel(String json, Class<T> clazz)
    {
        try
        {
            ObjectMapper objectMapper = createObjectMapper();
            T t = objectMapper.readValue(json, clazz);

            return t;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.d(TAG, "readModel:" + e.toString());
        }

        return null;
    }


    /**
     * 将JSON数据转换为实体类，以实体类对象数组的形式返回
     *
     * @param jsonObject 原始JSONObject对象
     * @param name       待转换的数组对象对应的JSONObject键名
     * @param clazz      实体类的类名.需为继承 Model 的类
     * @return ArrayList&lt;T&gt; 实体类对象数组
     */
    public static <T extends Model> ArrayList<T> readModels(JSONObject jsonObject, String name, Class<T> clazz)
    {
        JSONArray array = null;
        try
        {
            array = jsonObject.getJSONArray(name);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.d(TAG, "readModels:" + e.toString());
            return null;
        }

        return readModels(array, clazz);
    }


    /**
     * 将JSON数据转换为实体类，以实体类对象数组的形式返回
     *
     * @param jsonArray 待转换的JSON数据
     * @param clazz     实体类的类名.需为继承 Model 的类
     * @return ArrayList&lt;T&gt; 实体类对象数组
     */
    public static <T extends Model> ArrayList<T> readModels(String jsonArray, Class<T> clazz)
    {
        JSONArray array = null;
        try
        {
            array = new JSONArray(jsonArray);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.d(TAG, "readModels:" + e.toString());
            return null;
        }

        return readModels(array, clazz);
    }


    /**
     * 将JSON数据转换为实体类，以实体类对象数组的形式返回
     *
     * @param array 待转换的JSONArray数据
     * @param clazz 实体类的类名.需为继承 Model 的类
     * @return ArrayList&lt;T&gt; 实体类对象数组
     */
    public static <T extends Model> ArrayList<T> readModels(JSONArray array, Class<T> clazz)
    {
        try
        {
            int length = array.length();
            ArrayList<T> list = new ArrayList<T>(length);

            ObjectMapper objectMapper = createObjectMapper();
            for (int i = 0; i < length; i++)
            {
                list.add(objectMapper.readValue(array.getString(i), clazz));
            }

            return list;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.d(TAG, "readModels:" + e.toString());
        }

        return null;
    }


    /**
     * 将JSON字符串数组转换为实体类，以实体类对象数组的形式返回
     *
     * @param jsonArray 待转换的JSON字符串数组
     * @param clazz     实体类的类名.需为继承 Model 的类
     * @return ArrayList&lt;T&gt; 实体类对象数组
     */
    public static <T extends Model> ArrayList<T> readModels(ArrayList<String> jsonArray, Class<T> clazz)
    {
        try
        {
            ObjectMapper objectMapper = createObjectMapper();
            ArrayList<T> list = new ArrayList<T>(jsonArray.size());
            for (String json : jsonArray)
            {
                T t = null;
                try
                {
                    t = objectMapper.readValue(json, clazz);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                if (t != null)
                {
                    list.add(t);
                }
            }

            return list;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.d(TAG, "readModels:" + e.toString());
        }

        return null;
    }


    /**
     * 将JSON数据转换为HashMap
     *
     * @param json 待转换的JSON数据
     * @return LinkedHashMap&lt;String, Object&gt;
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, Object> readLinkedHashMap(String json)
    {
        try
        {
            ObjectMapper objectMapper = createObjectMapper();
            LinkedHashMap<String, Object> map = objectMapper.readValue(json, LinkedHashMap.class);

            return map;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.d(TAG, "readLinkedHashMap:" + e.toString());
        }

        return null;
    }


    /**
     * 将JSON数据转换为List&lt;Map&gt;集合
     *
     * @param jsonArray 待转换的JSON数据
     * @return ArrayList&lt;LinkedHashMap&lt;String, Object&gt;&gt;
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<LinkedHashMap<String, Object>> readLinkedHashMapArray(String jsonArray)
    {
        try
        {
            ObjectMapper objectMapper = createObjectMapper();
            ArrayList<LinkedHashMap<String, Object>> list = objectMapper.readValue(jsonArray, ArrayList.class);

            return list;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.d(TAG, "readLinkedHashMapArray:" + e.toString());
        }

        return null;
    }


    /**
     * 将实体对象转换为JSON格式数据
     *
     * @param object 要转换的实体对象
     * @return String JSON格式数据
     */
    public static String writeModelToJSON(Object object)
    {
        try
        {
            ObjectMapper objectMapper = createObjectMapper();
            String json = objectMapper.writeValueAsString(object);

            return json;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.d(TAG, "writeModelToJSON:" + e.toString());
        }

        return null;
    }


    private static ObjectMapper createObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 设置忽略未知的属性

        return objectMapper;
    }
}
