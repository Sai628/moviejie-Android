package com.sai628.moviejie.utils;

import java.util.Map;


public class MapUtil
{
    /**
     * 判断 map 是否为 null 或没有元素
     *
     * @param map
     * @return 当 map==null 或为空时返回 true, 否则返回 false.
     */
    public static boolean isNullOrEmpty(Map<?, ?> map)
    {
        return (map == null || map.isEmpty());
    }


    /**
     * 获取 map 中 key 对应的 value 值,当 map 为null或为空时,返回null
     *
     * @param map
     * @param key
     * @return
     */
    public static <K, V> V getValue(Map<K, V> map, Object key)
    {
        return ((isNullOrEmpty(map) || key == null) ? null : map.get(key));
    }


    /**
     * 判断 map 中是否存在指定的 key
     *
     * @param map
     * @param key
     * @return
     */
    public static <K, V> boolean containsKey(Map<K, V> map, Object key)
    {
        return ((isNullOrEmpty(map) || key == null) ? false : map.containsKey(key));
    }


    /**
     * 获取 Map 的长度
     *
     * @param map
     * @return 当 map==null 时返回0, 否则返回map.size();
     */
    public static <K, V> int getSize(Map<K, V> map)
    {
        return (map != null ? map.size() : 0);
    }
}
