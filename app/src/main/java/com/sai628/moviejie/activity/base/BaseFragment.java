package com.sai628.moviejie.activity.base;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * 基础Fragment
 */
public abstract class BaseFragment extends Fragment
{
    protected View rootView;


    protected abstract void initChildView(View view);


    protected void initRootView(LayoutInflater inflater, int resource, ViewGroup container)
    {
        if (rootView != null)
        {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
            {
                parent.removeView(rootView);
            }
        }
        else
        {
            rootView = inflater.inflate(resource, container, false);
            initChildView(rootView);
        }
    }


    protected Fragment getThis()
    {
        return this;
    }
}
