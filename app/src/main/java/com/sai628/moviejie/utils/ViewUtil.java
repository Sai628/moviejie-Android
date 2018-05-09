package com.sai628.moviejie.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;



public class ViewUtil
{
    public static final long SCROLL_DELAY_TIME = 100L;


    public static Fragment getItem(ViewPager viewPager, int index)
    {
        if (viewPager != null)
        {
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter != null && adapter instanceof FragmentPagerAdapter)
            {
                FragmentPagerAdapter fragmentPagerAdapter = (FragmentPagerAdapter) adapter;
                if (index >= 0 && index < fragmentPagerAdapter.getCount())
                {
                    return fragmentPagerAdapter.getItem(index);
                }
            }
        }

        return null;
    }


    public static void setMargins(View view, int left, int top, int right, int bottom)
    {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)
        {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }


    public static int setListViewHeightBasedOnChildren(ListView listview)
    {
        ListAdapter listAdapter = listview.getAdapter();

        if (listAdapter == null)
        {
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            View listItem = listAdapter.getView(i, null, listview);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listview.getLayoutParams();
        params.height = totalHeight + (listview.getDividerHeight() * (listAdapter.getCount() - 1));
        listview.setLayoutParams(params);

        return params.height;
    }


    public static int setCutViewHeightBasedOnChildrenNew1(View view, ListView listview)
    {
        ListAdapter listAdapter = listview.getAdapter();

        if (listAdapter == null)
        {
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            View listItem = listAdapter.getView(i, null, listview);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = 1;
        params.height = totalHeight + (listview.getDividerHeight() + (listAdapter.getCount() - 1)) + 15;
        view.setLayoutParams(params);

        return params.height;
    }


    public static void showAnimationBaseOnChildView(ViewGroup viewGroup, Animation animation)
    {
        if (viewGroup != null)
        {
            for (int i = 0, count = viewGroup.getChildCount(); i < count; i++)
            {
                viewGroup.getChildAt(i).setAnimation(animation);
                animation.startNow();
            }
        }
    }


    public static void showView(View view, Animation animation)
    {
        if (view.getVisibility() == View.VISIBLE)
        {
            return;
        }

        view.setVisibility(View.VISIBLE);
        view.setAnimation(animation);
        animation.startNow();
    }


    public static void hiddenView(View view, Animation animation)
    {
        if (view.getVisibility() == View.INVISIBLE)
        {
            return;
        }

        view.setAnimation(animation);
        animation.startNow();
        view.setVisibility(View.INVISIBLE);
    }


    public static void dismissView(View view, Animation animation)
    {
        if (view.getVisibility() == View.GONE)
        {
            return;
        }

        view.setAnimation(animation);
        animation.startNow();
        view.setVisibility(View.GONE);
    }


    public static void scrollToTopDelayed(final ScrollView scrollView)
    {
        scrollToTopDelayed(scrollView, SCROLL_DELAY_TIME);
    }


    public static void scrollToTopDelayed(final ScrollView scrollView, long delayMillis)
    {
        if (scrollView != null)
        {
            scrollView.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    scrollView.scrollTo(0, 0);
                }
            }, delayMillis);
        }
    }
}
