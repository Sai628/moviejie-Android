package com.paging.listview;

import android.content.Context;
import android.widget.BaseAdapter;

import com.sai628.moviejie.utils.CollectionUtil;

import java.util.List;


public abstract class PagingBaseAdapter<T> extends BaseAdapter
{
    protected Context context;
    protected List<T> items;


    public PagingBaseAdapter()
    {
        this(null, null);
    }


    public PagingBaseAdapter(Context context, List<T> items)
    {
        this.context = context;
        this.items = items;
    }


    @Override
    public int getCount()
    {
        return CollectionUtil.getSize(items);
    }


    @Override
    public Object getItem(int position)
    {
        return items.get(position);
    }


    @Override
    public long getItemId(int position)
    {
        return position;
    }


    public void addMoreItems(List<T> newItems)
    {
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }


    public void addMoreItems(int location, List<T> newItems)
    {
        this.items.addAll(location, newItems);
        notifyDataSetChanged();
    }


    public void addMoreItem(T newItem)
    {
        this.items.add(newItem);
        notifyDataSetChanged();
    }


    public void addMoreItem(int location, T newItem)
    {
        this.items.add(location, newItem);
        notifyDataSetChanged();
    }


    public void removeAllItems()
    {
        this.items.clear();
        notifyDataSetChanged();
    }


    public void removeItem(int location)
    {
        this.items.remove(location);
        notifyDataSetChanged();
    }


    public void replaceAllItems(List<T> newItems)
    {
        this.items.clear();
        if (newItems != null && newItems.size() > 0)
        {
            this.items.addAll(newItems);
        }
        notifyDataSetChanged();
    }


    public void setItem(int location, T newItem)
    {
        this.items.set(location, newItem);
        notifyDataSetChanged();
    }
}
