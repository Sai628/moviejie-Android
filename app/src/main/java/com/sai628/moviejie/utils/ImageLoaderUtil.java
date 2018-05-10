package com.sai628.moviejie.utils;

import android.content.Context;
import android.support.annotation.IdRes;
import android.widget.ImageView;

import com.sai628.moviejie.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;


public class ImageLoaderUtil
{
    private static final int RESOURCE_ID_IMAGE_DEFAULT = R.drawable.icon_empty_image;


    public static void loadImage(Context context, ImageView imageView, String url)
    {
        loadImage(context, imageView, url, RESOURCE_ID_IMAGE_DEFAULT);
    }


    public static void loadImage(Context context, ImageView imageView, String url, int placeholderResID)
    {
        build(context, url, placeholderResID).centerInside().into(imageView);
    }


    public static void loadResourceImage(Context context, ImageView imageView, @IdRes int resourceId)
    {
        Picasso.with(context).load(resourceId).placeholder(RESOURCE_ID_IMAGE_DEFAULT).error(RESOURCE_ID_IMAGE_DEFAULT).fit().into(imageView);
    }


    public static void loadImageWithoutPlaceholder(Context context, ImageView imageView, String url)
    {
        build(context, url).centerInside().into(imageView);
    }


    public static RequestCreator build(Context context, String url, int defaultResId)
    {
        String notEmptyUrl = StringUtil.getNotEmptyUrl(url);
        return Picasso.with(context).load(notEmptyUrl).placeholder(defaultResId).error(defaultResId).fit();
    }


    public static RequestCreator build(Context context, String uri)
    {
        String notEmptyUrl = StringUtil.getNotEmptyUrl(uri);
        return Picasso.with(context).load(notEmptyUrl).fit();
    }
}
