package com.sai628.moviejie.utils;

import android.content.ContentValues;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.sai628.moviejie.service.net.VolleyManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @author Sai
 * @ClassName: NetUtil
 * @Description: 网络访问工具类
 * @date Apr 14, 20153:15:37 PM
 */
public class NetUtil
{
    private static final String TAG = NetUtil.class.getSimpleName();

    private static final int TIMEOUT_MS = 5000; // 请求超时时间
    private static final int NUM_RETRIES = 1; // 请求重试次数
    private static final float BACKOFF_MULT = 1.0f; // 补偿倍数


    public static void get(Context context, String url, Response.Listener<String> responseListener, Response.ErrorListener errorListener)
    {
        get(context, url, null, responseListener, errorListener);
    }


    public static void get(Context context, String url, ContentValues values,
                           Response.Listener<String> responseListener, Response.ErrorListener errorListener)
    {
        printLog(url, values, null);

        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        if (values != null)
        {
            LogUtil.d(TAG, "values:" + values.toString());
            for (Entry<String, Object> entry : values.valueSet())
            {
                try
                {
                    Object objectValue = entry.getValue();
                    if (objectValue != null)
                    {
                        String key = URLEncoder.encode(entry.getKey(), "UTF-8");
                        String value = URLEncoder.encode(objectValue.toString(), "UTF-8");
                        sb.append(key).append("=").append(value).append("&");
                    }
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
        }

        sb.deleteCharAt(sb.length() - 1);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, sb.toString(), responseListener, errorListener);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, NUM_RETRIES, BACKOFF_MULT));
        VolleyManager.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public static void post(Context context, String url, final ContentValues values,
                            Response.Listener<String> responseListener, Response.ErrorListener errorListener)
    {
        post(context, url, values, null, responseListener, errorListener);
    }


    public static void post(Context context, String url, final ContentValues values, final Map<String, String> headers,
                            Response.Listener<String> responseListener, Response.ErrorListener errorListener)
    {
        printLog(url, values, headers);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseListener, errorListener)
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> map = new HashMap<>();
                if (values != null)
                {
                    for (Entry<String, Object> entry : values.valueSet())
                    {
                        Object objectValue = entry.getValue();
                        if (objectValue != null)
                        {
                            map.put(entry.getKey(), objectValue.toString());
                        }
                    }
                }
                return map;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                return (headers != null ? headers : super.getHeaders());
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, NUM_RETRIES, BACKOFF_MULT));
        VolleyManager.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public static void postWithListValue(Context context, String url, final ContentValues values,
                                         final HashMap<String, ArrayList<Object>> listValues, Response.Listener<String> responseListener,
                                         Response.ErrorListener errorListener)
    {
        LogUtil.d(TAG, "url:" + StringUtil.getString(url));
        LogUtil.d(TAG, "values:" + StringUtil.getString(values));
        LogUtil.d(TAG, "listValues:" + StringUtil.getString(listValues));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseListener, errorListener)
        {
            @Override
            public byte[] getBody() throws AuthFailureError
            {
                if (values != null || listValues != null)
                {
                    String paramsEncoding = getParamsEncoding();
                    StringBuilder sb = new StringBuilder("");

                    try
                    {
                        if (values != null)
                        {
                            for (Entry<String, Object> entry : values.valueSet())
                            {
                                String key = URLEncoder.encode(entry.getKey(), paramsEncoding);

                                Object objectValue = entry.getValue();
                                if (objectValue != null)
                                {
                                    String value = URLEncoder.encode(objectValue.toString(), paramsEncoding);
                                    sb.append("&").append(key).append("=").append(value);
                                }
                            }
                        }

                        if (listValues != null)
                        {
                            for (Entry<String, ArrayList<Object>> entry : listValues.entrySet())
                            {
                                String key = URLEncoder.encode(entry.getKey(), paramsEncoding);

                                ArrayList<Object> valueList = entry.getValue();
                                if (valueList != null && valueList.size() > 0)
                                {
                                    for (Object v : valueList)
                                    {
                                        String value = URLEncoder.encode(v.toString(), paramsEncoding);
                                        sb.append("&").append(key).append("=").append(value);
                                    }
                                }
                            }
                        }

                        if (sb.length() > 0)
                        {
                            sb.deleteCharAt(0);
                        }

                        LogUtil.d(TAG, "body:" + sb.toString());
                        return sb.toString().getBytes(paramsEncoding);
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                        throw new RuntimeException("Encoding not supported:" + paramsEncoding);
                    }
                }

                return null;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, NUM_RETRIES, BACKOFF_MULT));
        VolleyManager.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
    }


    private static void printLog(String url, ContentValues values, Map<String, String> headers)
    {
        String msg = "url: " + StringUtil.getString(url) +
                "\nvalues: " + StringUtil.getString(values) +
                "\nheaders: " + StringUtil.getString(headers);
        LogUtil.d(TAG, msg);
    }
}
