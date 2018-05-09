package com.sai628.moviejie.service.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleyManager
{
    private static VolleyManager volleyManager;
    private static Context mContext;
    private RequestQueue requestQueue;


    private VolleyManager(Context context)
    {
        mContext = context;
        requestQueue = getRequestQueue();
    }


    public static synchronized VolleyManager getInstance(Context context)
    {
        if (volleyManager == null)
        {
            volleyManager = new VolleyManager(context);
        }
        return volleyManager;
    }


    public RequestQueue getRequestQueue()
    {
        if (requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }


    public <T> void addToRequestQueue(Request<T> request)
    {
        getRequestQueue().add(request);
    }
}
