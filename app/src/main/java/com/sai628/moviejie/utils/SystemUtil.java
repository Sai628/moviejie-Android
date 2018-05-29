package com.sai628.moviejie.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.WindowManager;

import java.util.List;

import es.dmoral.toasty.Toasty;


/**
 * @author Sai
 * @ClassName: SystemUtil
 * @Description: 系统工具类
 * @date 2013-10-21 下午12:06:21
 */
public class SystemUtil
{
    /**
     * 根据权限permission查询provider获取真实的content的uri前缀
     *
     * @param context    上下文
     * @param permission 权限
     * @return content uri前缀字符串，失败时返回 null
     */
    public static String getAuthorityFromPermission(Context context, String permission)
    {
        if (permission == null)
        {
            return null;
        }

        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
        if (packs != null)
        {
            for (PackageInfo pack : packs)
            {
                ProviderInfo[] providers = pack.providers;
                if (providers != null)
                {
                    for (ProviderInfo provider : providers)
                    {
                        if (permission.equals(provider.readPermission) || permission.equals(provider.writePermission))
                        {
                            return provider.authority;
                        }
                    }
                }
            }
        }

        return null;
    }


    /**
     * 获取栈顶部的Activity
     *
     * @param context
     * @return String
     */
    public static String getTopActivity(Context context)
    {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();

        List<RunningTaskInfo> taskInfos = manager.getRunningTasks(Integer.MAX_VALUE);
        if (CollectionUtil.getSize(taskInfos) > 0)
        {
            for (RunningTaskInfo runningTaskInfo : taskInfos)
            {
                if (TextUtils.equals(packageName, runningTaskInfo.topActivity.getPackageName()))
                {
                    return runningTaskInfo.topActivity.getClassName();
                }
            }
        }

        return null;
    }


    /**
     * 设置窗口背影透明度
     *
     * @param activity
     * @param bgAlpha(1.0为100%)
     */
    public static void setWindowBackgroundAlpha(Activity activity, float bgAlpha)
    {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }


    /**
     * 检测系统是否已安装某个应用包
     *
     * @param context     上下文
     * @param packageName 应用的包名
     * @return boolean 已安装时返回true，否则返回false
     */
    public static boolean isInstallPackage(Context context, String packageName)
    {
        if (TextUtils.isEmpty(packageName))
        {
            return false;
        }

        try
        {
            context.getApplicationContext().getPackageManager().getPackageInfo(packageName, PackageManager.PERMISSION_GRANTED);

            return true;
        }
        catch (NameNotFoundException e)
        {
            return false;
        }
    }


    /**
     * 检测应用是否在前台
     *
     * @param context
     * @return boolean
     */
    public static boolean isAppOnForeground(Context context)
    {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> taskInfos = manager.getRunningTasks(1);

        if (CollectionUtil.getSize(taskInfos) > 0 && TextUtils.equals(context.getPackageName(), taskInfos.get(0).topActivity.getPackageName()))
        {
            return true;
        }

        return false;
    }


    public static boolean isInKeyguardRestrictedInputMode(Context context)
    {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }


    /**
     * 在市场上搜索应用
     *
     * @param context     上下文
     * @param packageName 应用包名
     */
    public static void searchApp(Context context, String packageName)
    {
        try
        {
            Uri uri = Uri.parse("market://search?q=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            e.printStackTrace();
            Toasty.info(context, "没有找到适合的市场应用,请自行下载").show();
        }
    }


    /**
     * 复制文本到系统剪贴板
     *
     * @param context
     * @param text
     */
    public static void copyToClipboard(Context context, String text)
    {
        ClipboardManager cbm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cbm.setPrimaryClip(ClipData.newPlainText(null, text));
    }
}
