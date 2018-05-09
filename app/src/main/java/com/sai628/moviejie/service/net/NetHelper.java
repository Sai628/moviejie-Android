package com.sai628.moviejie.service.net;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.sai628.moviejie.config.network.ErrorInfo;
import com.sai628.moviejie.utils.LogUtil;
import com.sai628.moviejie.utils.NetUtil;
import com.sai628.moviejie.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class NetHelper
{
    private static final String TAG = NetHelper.class.getSimpleName();

    private static final String RESULT_STATUS = "status";
    private static final String RESULT_ERROR = "error";
    private static final String RESULT_ERROR_CODE = "errorCode";

    private static final int RESULT_SUCCESS = 1;


    private static boolean isSuccess(JSONObject jsonObject)
    {
        return (jsonObject.optInt(RESULT_STATUS, 0) == RESULT_SUCCESS);
    }


    // 尝试处理错误.当不存在错误时,返回false. 否则将对错误进行处理并返回true.
    private static boolean tryHandleError(JSONObject jsonObject, NetCallback netCallback) throws JSONException
    {
        if (isSuccess(jsonObject))
        {
            return false;
        }

        if (!jsonObject.has(RESULT_ERROR_CODE))
        {
            dealWithError(ErrorInfo.CODE_UNKNOWN_ERROR, ErrorInfo.MSG_UNKNOWN_ERROR, netCallback);
            return true;
        }

        int errorCode = jsonObject.getInt(RESULT_ERROR_CODE);
        String errorMessage = ErrorInfo.ERROR_INFO_ARRAY.get(errorCode, null);
        if (errorMessage == null)
        {
            errorMessage = jsonObject.optString(RESULT_ERROR, null);  // 当错误码对应的本地错误提示不存在时，获取后台返回的错误信息
        }

        if (TextUtils.isEmpty(errorMessage))
        {
            errorMessage = ErrorInfo.MSG_UNKNOWN_ERROR; // 当错误信息不存在时，显示“未知错误”
        }

        dealWithError(errorCode, errorMessage, netCallback);
        return true;
    }


    public static void dealWithSuccess(Object data, NetCallback netCallback)
    {
        if (netCallback != null)
        {
            netCallback.onSuccess(data);
        }
    }


    private static void dealWithError(int errorCode, String errorMessage, NetCallback netCallback)
    {
        if (netCallback != null)
        {
            netCallback.onError(errorCode, errorMessage);
        }
    }


    private static void dealWithFailure(VolleyError volleyError, NetCallback netCallback)
    {
        if (netCallback != null)
        {
            String msg = ErrorInfo.MSG_UNKNOWN_ERROR;
            if (volleyError instanceof NetworkError)
            {
                msg = ErrorInfo.MSG_NETWORK_ERROR;
            }
            else if (volleyError instanceof ServerError)
            {
                msg = ErrorInfo.MSG_SERVER_ERROR;
            }
            else if (volleyError instanceof ParseError)
            {
                msg = ErrorInfo.PARSE_ERROR_MSG;
            }
            else if (volleyError instanceof TimeoutError)
            {
                msg = ErrorInfo.MSG_TIMEOUT_ERROR;
            }

            netCallback.onFailure(msg);
        }
    }


    private static void printSuccessResponse(String url, String result)
    {
        String msg = "onResponse url: " + StringUtil.getString(url) +
                "\nonResponse result: " + StringUtil.getString(result);
        LogUtil.d(TAG, msg);
    }


    private static void printErrorResponse(String url, VolleyError volleyError)
    {
        String msg = "onResponse url: " + StringUtil.getString(url) +
                "\nonErrorResponse: " + StringUtil.getString(volleyError) +
                "\nonErrorResponse statusCode: " + StringUtil.getString(parseResponseStatusCode(volleyError)) +
                "\nonErrorResponse body: " + StringUtil.getString(parseResponseData(volleyError));
        LogUtil.d(TAG, msg);

    }


    private static String parseResponseStatusCode(VolleyError volleyError)
    {
        if (volleyError != null && volleyError.networkResponse != null && volleyError.networkResponse.data != null)
        {
            return volleyError.networkResponse.statusCode + "";
        }

        return null;
    }


    private static String parseResponseData(VolleyError volleyError)
    {
        if (volleyError != null && volleyError.networkResponse != null && volleyError.networkResponse.data != null)
        {
            try
            {
                return new String(volleyError.networkResponse.data, "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }


    public static void get(final Context context, final String url, final NetCallback netCallback, final SuccessHandler handler)
    {
        get(context, url, null, netCallback, handler);
    }


    public static void get(final Context context, final String url, final ContentValues values,
                           final NetCallback netCallback, final SuccessHandler handler)
    {
        NetUtil.get(context, url, values, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String result)
            {
                printSuccessResponse(url, result);
                try
                {
                    JSONObject jsonObject = new JSONObject(result);
                    boolean hasError = tryHandleError(jsonObject, netCallback);
                    if (!hasError && handler != null)
                    {
                        handler.onSuccess(context, values, jsonObject);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    LogUtil.e(TAG, "JSONException:" + e.toString());

                    dealWithFailure(new ParseError(), netCallback);
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                printErrorResponse(url, volleyError);
                dealWithFailure(volleyError, netCallback);
            }
        });
    }


    public static void post(final Context context, final String url, final ContentValues values,
                            final NetCallback netCallback, final SuccessHandler handler)
    {
        NetUtil.post(context, url, values, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String result)
            {
                printSuccessResponse(url, result);
                try
                {
                    JSONObject jsonObject = new JSONObject(result);
                    boolean hasError = tryHandleError(jsonObject, netCallback);
                    if (!hasError && handler != null)
                    {
                        handler.onSuccess(context, values, jsonObject);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    LogUtil.e(TAG, "JSONException: " + e.toString());

                    dealWithFailure(new ParseError(), netCallback);
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                printErrorResponse(url, volleyError);
                dealWithFailure(volleyError, netCallback);
            }
        });
    }


    /**
     * 网络访问服务模块回调接口，通过该接口分别获取数据、错误提示信息以及请求失败原因
     */
    public interface NetCallback
    {
        /**
         * 网络访问结束时将调用该方法，调用者实现接口的该方法用于获取请求的数据
         *
         * @param data 数据结果，一般为HashMap&lt;String, Object&gt;对象
         */
        void onSuccess(Object data);


        /**
         * 网络访问结束时将调用该方法，调用者实现接口的该方法用于获取请求过程中的错误提示信息(服务器原因)
         *
         * @param errorCode 错误提示码,参见ErrorInfo类
         * @param errorMsg  错误提示字符串
         */
        void onError(int errorCode, String errorMsg);


        /**
         * 网络访问出错时将调用该方法，调用者实现接口的该方法用于获取请求过程中的失败提示信息（网络原因）
         *
         * @param failureMsg 失败提示字符串
         */
        void onFailure(String failureMsg);
    }


    public interface SuccessHandler
    {
        void onSuccess(Context context, ContentValues values, JSONObject jsonObject) throws JSONException;
    }
}
