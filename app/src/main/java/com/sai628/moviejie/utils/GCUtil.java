package com.sai628.moviejie.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;


/**
 * @author Sai
 * @ClassName: GCUtil
 * @Description: 内存回收工具类
 * @date Oct 23, 2014 6:43:38 PM
 */
public class GCUtil
{
    /**
     * 回收 ImageView 对象中的图片资源内存占用
     *
     * @param imageView
     */
    public static void recycle(ImageView imageView)
    {
        if (imageView != null)
        {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            if (bitmapDrawable != null)
            {
                recycle(bitmapDrawable.getBitmap());
            }
        }
    }


    /**
     * 回收 Bitmap 对象的内存资源占用
     *
     * @param bitmap
     */
    public static void recycle(Bitmap bitmap)
    {
        if (bitmap != null && !bitmap.isRecycled())
        {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
