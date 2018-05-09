package com.sai628.moviejie.config.network;

import android.util.SparseArray;


/**
 * @author Sai
 * @ClassName: ErrorInfo
 * @Description: 错误代码配置类
 * @date 2013-9-26 下午3:22:04
 */
public class ErrorInfo
{
    /**
     * 网络错误提示文字
     */
    public static final String MSG_NETWORK_ERROR = "网络不佳，请重试";
    /**
     * 网络连接超时提示文字
     */
    public static final String MSG_TIMEOUT_ERROR = "网络连接超时，请重试";
    /**
     * 服务器内部错误提示文字(状态码为5xx等)
     */
    public static final String MSG_SERVER_ERROR = "服务器内部出错";
    /**
     * 数据解析错误提示文字
     */
    public static final String PARSE_ERROR_MSG = "数据解析出错";
    /**
     * 未知错误提示文字
     */
    public static final String MSG_UNKNOWN_ERROR = "发生了未知错误";

    /**
     * 接口调用失败（无具体信息）
     */
    public static final int CODE_UNKNOWN_ERROR = 9999;

    /**-------------------------------  common 模块错误代号  ---------------------------------------**/
    /**
     * 系统错误
     */
    public static final int CODE_COMMON_SYS_ERROR = 10000;


    /**
     * 错误代码稀疏数组，以 int-String 的形式存放错误代码以及对应的错误提示信息，<br/>
     * 可以根据错误代码获取对应的错误提示信息.
     */
    public static final SparseArray<String> ERROR_INFO_ARRAY = initArray();


    private static SparseArray<String> initArray()
    {
        SparseArray<String> array = new SparseArray<>();
        array.put(CODE_COMMON_SYS_ERROR, "系统错误");
        return array;
    }
}
