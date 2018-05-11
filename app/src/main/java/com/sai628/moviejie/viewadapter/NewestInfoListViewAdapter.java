package com.sai628.moviejie.viewadapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sai628.moviejie.R;
import com.sai628.moviejie.model.ResourceInfo;
import com.sai628.moviejie.utils.CollectionUtil;
import com.sai628.moviejie.utils.StringUtil;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


/**
 * @author Sai
 * @ClassName: NewestInfoListViewAdapter
 * @Description: 更新列表适配器
 * @date 11/05/2018 10:05
 */
public class NewestInfoListViewAdapter extends BaseAdapter implements StickyListHeadersAdapter
{
    private Context context;
    private List<ResourceInfo> resourceInfos;


    public NewestInfoListViewAdapter(Context context, List<ResourceInfo> resourceInfos)
    {
        this.context = context;
        this.resourceInfos = resourceInfos;
    }


    @Override
    public int getCount()
    {
        return CollectionUtil.getSize(resourceInfos);
    }


    @Override
    public Object getItem(int position)
    {
        return resourceInfos.get(position);
    }


    @Override
    public long getItemId(int position)
    {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ResourceInfo resourceInfo = (ResourceInfo) getItem(position);
        switch (resourceInfo.getResourceType())
        {
            case NEWS:
                return getNewResourceView(resourceInfo, position, convertView, parent);
            case HOT:
                return getHotResource(resourceInfo, position, convertView, parent);
            default:
                return null;
        }
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent)
    {
        ViewHolderHelper helper = ViewHolderHelper.get(context, convertView, parent, R.layout.listview_header, position);
        ResourceInfo resourceInfo = (ResourceInfo) getItem(position);
        helper.setText(R.id.listview_header_textview, resourceInfo.getCategory());

        return helper.getConvertView();
    }


    @Override
    public long getHeaderId(int position)
    {
        return resourceInfos.get(position).getCategory().hashCode();
    }


    private View getNewResourceView(ResourceInfo resource, int position, View convertView, ViewGroup parent)
    {
        ViewHolderHelper helper = ViewHolderHelper.get(context, convertView, parent, R.layout.listview_item_new_resource, position);
        int titleColor = context.getResources().getColor(!TextUtils.isEmpty(resource.getMovie_link()) ? R.color._333 : R.color.link);

        helper.setText(R.id.listview_item_title_textview, resource.getTitle());  // 标题
        helper.setText(R.id.listview_item_size_textview, resource.getSize());  // 大小
        helper.setTextColor(R.id.listview_item_title_textview, titleColor);  // 标题颜色

        return helper.getConvertView();
    }


    private View getHotResource(ResourceInfo resource, int position, View convertView, ViewGroup parent)
    {
        ViewHolderHelper helper = ViewHolderHelper.get(context, convertView, parent, R.layout.listview_item_hot_resource, position);
        int titleColor = context.getResources().getColor(!TextUtils.isEmpty(resource.getMovie_link()) ? R.color._333 : R.color.link);

        TextView titleTv = helper.getView(R.id.listview_item_title_textview);
        MaterialRatingBar ratingBar = helper.getView(R.id.listview_item_ratingbar);
        TextView ratingBarTv = helper.getView(R.id.listview_item_ratingbar_textview);
        TextView starEmptyTipTv = helper.getView(R.id.listview_item_start_empty_tip_textview);

        titleTv.setText(resource.getTitle());  // 标题
        titleTv.setTextColor(titleColor);  // 标题颜色
        if (StringUtil.isNumber(resource.getRating()))  // 有评分时
        {
            starEmptyTipTv.setVisibility(View.INVISIBLE);
            ratingBarTv.setText(resource.getRating());
            ratingBar.setRating(Float.valueOf(resource.getRating()) / 2.0f);
            ratingBar.setVisibility(View.VISIBLE);
        }
        else  // 无评分时
        {
            starEmptyTipTv.setVisibility(View.VISIBLE);
            ratingBarTv.setText(null);
            ratingBar.setVisibility(View.INVISIBLE);
        }

        return helper.getConvertView();
    }
}
