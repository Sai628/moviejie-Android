package com.sai628.moviejie.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CollectionUtil
{
    /**
     * 判断Collection是否为null或没有元素
     *
     * @param collection
     * @return 当collection==null或为空时返回 true, 否则返回 false.
     */
    public static boolean isEmpty(Collection<?> collection)
    {
        return (collection == null || collection.isEmpty());
    }


    /**
     * 获取Collection的长度
     *
     * @param collection
     * @return 当collection==null时返回0, 否则返回collection.size();
     */
    public static int getSize(Collection<?> collection)
    {
        return (collection != null ? collection.size() : 0);
    }


    /**
     * 判断Collection中是否存在指定的 obj
     *
     * @param collection
     * @param obj
     * @return
     */
    public static boolean contains(Collection<?> collection, Object obj)
    {
        return (isEmpty(collection) || obj == null) ? false : collection.contains(obj);
    }


    public static void reverse(List<?> list)
    {
        if (getSize(list) > 0)
        {
            Collections.reverse(list);
        }
    }


    public static ArrayList<String> toArrayList(String[] array)
    {
        ArrayList<String> list = new ArrayList<String>();
        if (array != null && array.length > 0)
        {
            for (String str : array)
            {
                list.add(str);
            }
        }
        return list;
    }


    public static List<?> deepCopy(List<?> src) throws IOException, ClassNotFoundException
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);

        return (List<?>)in.readObject();
    }


    /**
     * 获取两个集合的交集的大小
     *
     * @param set1 集合1
     * @param set2 集合2
     * @return int 两个集合的交集的大小
     */
    public static <E> int getIntersectionSize(Set<E> set1, Set<E> set2)
    {
        Set<E> result = new HashSet<>();
        result.addAll(set1);
        result.retainAll(set2);

        return result.size();
    }


    /**
     * 获取两个集合的交集的差集.
     * 如set1=["A", "B", "C"], set2=["B", "E"], 则set1与set2的差集为["A", "C"], set2与set1的差集为["E"]
     *
     * @param set1 集合1
     * @param set2 集合2
     * @return Set&lt;E&gt; 两个集合的交集
     */
    public static <E> Set<E> getSubtractSet(Set<E> set1, Set<E> set2)
    {
        Set<E> result = new HashSet<E>();
        result.addAll(set1);
        result.removeAll(set2);

        return result;
    }
}
