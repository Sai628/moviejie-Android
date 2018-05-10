package com.sai628.moviejie.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sai628.moviejie.R;
import com.sai628.moviejie.activity.base.BaseFragment;
import com.sai628.moviejie.model.ResourceInfo;
import com.sai628.moviejie.service.net.NetHelper;
import com.sai628.moviejie.service.net.NetService;
import com.sai628.moviejie.utils.CollectionUtil;
import com.sai628.moviejie.utils.StringUtil;
import com.sai628.moviejie.view.LoadingMenu;
import com.sai628.moviejie.viewadapter.ViewHolderHelper;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


/**
 * @author Sai
 * @ClassName: NewestInfoFragment
 * @Description: "最新信息"视图
 * @date 09/05/2018 16:58
 */
public class NewestInfoFragment extends BaseFragment implements LoadingMenu.OnRetryClickListener
{
    private StickyListHeadersListView newestListView;
    private LoadingMenu loadingMenu;

    private NewestInfoListViewAdapter newestInfoListViewAdapter;
    private ArrayList<ResourceInfo> resourceInfos = new ArrayList<>();
    private boolean isFirstIn = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        initRootView(inflater, R.layout.fragment_newest_view, container);
        if (isFirstIn)
        {
            isFirstIn = false;
            loadingMenu.showLoading();
            loadNewestInfo();
        }

        return rootView;
    }


    @Override
    protected void initChildView(View view)
    {
        newestListView = view.findViewById(R.id.fragment_newest_view_listview);
        newestInfoListViewAdapter = new NewestInfoListViewAdapter(getActivity(), resourceInfos);
        newestListView.setAdapter(newestInfoListViewAdapter);
        newestListView.setOnItemClickListener(newestListViewOnItemClickListener);

        loadingMenu = view.findViewById(R.id.loading_menu);
        loadingMenu.setOnRetryClickListener(this);
    }


    @SuppressWarnings("unchecked")
    private void updateUI(ArrayList<ResourceInfo> infos)
    {
        resourceInfos.clear();
        resourceInfos.addAll(infos);
        newestInfoListViewAdapter.notifyDataSetChanged();
    }


    @SuppressWarnings("unchecked")
    private void loadNewestInfo()
    {
        NetService.getIndexInfo(getActivity(), new NetHelper.NetCallback()
        {
            @Override
            public void onSuccess(Object data)
            {
                loadingMenu.dismiss();
                updateUI((ArrayList<ResourceInfo>) data);
            }


            @Override
            public void onError(int errorCode, String errorMsg)
            {
                loadingMenu.showFail();
            }


            @Override
            public void onFailure(String failureMsg)
            {
                loadingMenu.showFail();
            }
        });
    }


    @Override
    public void onRetryClick(View v)
    {
        loadNewestInfo();
    }


    private AdapterView.OnItemClickListener newestListViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            ResourceInfo resourceInfo = resourceInfos.get(position);
            //TODO
        }
    };


    private class NewestInfoListViewAdapter extends BaseAdapter implements StickyListHeadersAdapter
    {
        private Context context;
        private List<ResourceInfo> resourceInfos;


        NewestInfoListViewAdapter(Context context, List<ResourceInfo> resourceInfos)
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
}
