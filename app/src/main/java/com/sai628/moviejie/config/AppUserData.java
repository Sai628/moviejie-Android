package com.sai628.moviejie.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.sai628.moviejie.utils.JSONUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Sai
 * @ClassName: AppUserData
 * @Description: 应用用户数据操作类
 * @date 12/06/2018 11:55
 */
public class AppUserData
{
    // 搜索历史键名
    private static final String KEY_SEARCH_HISTORY = "key_search_history";

    // 存放用户数据的文件名
    private static final String PRES_FILE_NAME = "app_user_data";


    private static SharedPreferences getSharedPreferences(Context context)
    {
        return context.getApplicationContext().getSharedPreferences(PRES_FILE_NAME, Context.MODE_PRIVATE);
    }


    /**
     * 获取应用的搜索历史字符串列表. 若没有搜索历史时, 返回一个空列表
     *
     * @param context
     * @return
     */
    public static ArrayList<String> getSearchHistories(Context context)
    {
        ArrayList<String> histories = new ArrayList<>();
        String result = getSharedPreferences(context).getString(KEY_SEARCH_HISTORY, null);
        if (!TextUtils.isEmpty(result))
        {
            ArrayList<String> cache = JSONUtil.readModels(result, String[].class);
            if (cache != null && cache.size() > 0)
            {
                histories.addAll(cache);
            }
        }

        return histories;
    }


    /**
     * 保存应用的搜索历史字符串列表
     *
     * @param context
     * @param searchHistories
     */
    public static void saveSearchHistories(Context context, ArrayList<String> searchHistories)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        if (searchHistories == null || searchHistories.size() == 0)
        {
            editor.remove(KEY_SEARCH_HISTORY).apply();
            return;
        }

        int cacheSize = Math.min(searchHistories.size(), Constants.SEARCH_HISTORY_CACHE_MAX);
        List<String> subHistories = searchHistories.subList(0, cacheSize);
        String result = JSONUtil.writeModelToJSON(subHistories);
        editor.putString(KEY_SEARCH_HISTORY, result).apply();
    }
}
