package com.sai628.moviejie.viewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


public abstract class CommonAdapter<T> extends BaseAdapter
{
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected int mItemLayoutId;
    protected List<T> mDatas;

    private OnViewInflateListener mOnViewInflateListener;


    public interface OnViewInflateListener
    {
        void onInflate(View inflated);
    }


    public OnViewInflateListener getOnViewInflateListener()
    {
        return mOnViewInflateListener;
    }


    public void setOnViewInflateListener(OnViewInflateListener onViewInflateListener)
    {
        this.mOnViewInflateListener = onViewInflateListener;
    }


    public CommonAdapter(Context context, int itemLayoutId)
    {
        this(context, itemLayoutId, null);
    }


    public CommonAdapter(Context context, int itemLayoutId, List<T> mDatas)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mItemLayoutId = itemLayoutId;

        this.mDatas = (mDatas == null ? new ArrayList<T>() : new ArrayList<T>(mDatas));
    }


    @Override
    public int getCount()
    {
        return mDatas.size();
    }


    @Override
    public T getItem(int position)
    {
        return mDatas.get(position);
    }


    @Override
    public long getItemId(int position)
    {
        return position;
    }


    public int getItemPosition(T item)
    {
        return mDatas.indexOf(item);
    }


    public void add(T item)
    {
        mDatas.add(item);
        notifyDataSetChanged();
    }


    public void addAll(List<T> list)
    {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }


    public void set(T oldItem, T newItem)
    {
        setItem(mDatas.indexOf(oldItem), newItem);
    }


    public void setItem(int index, T item)
    {
        mDatas.set(index, item);
        notifyDataSetChanged();
    }


    public void removeItem(T item)
    {
        mDatas.remove(item);
        notifyDataSetChanged();
    }


    public void removeItem(int index)
    {
        mDatas.remove(index);
        notifyDataSetChanged();
    }


    public void replaceAll(List<T> list)
    {
        mDatas.clear();
        if (list != null && list.size() > 0)
        {
            mDatas.addAll(list);
        }

        notifyDataSetChanged();
    }


    public boolean containsItem(T item)
    {
        return mDatas.contains(item);
    }


    public List<T> getItems()
    {
        return mDatas;
    }


    public void clear()
    {
        mDatas.clear();
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolderHelper helper = ViewHolderHelper.get(mContext, convertView, parent, mItemLayoutId, position, mOnViewInflateListener);
        convert(helper, getItem(position), position);

        return helper.getConvertView();
    }


    public abstract void convert(ViewHolderHelper helper, T object, int position);
}
