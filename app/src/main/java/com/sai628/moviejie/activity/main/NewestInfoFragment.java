package com.sai628.moviejie.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.sai628.moviejie.R;
import com.sai628.moviejie.activity.base.BaseFragment;
import com.sai628.moviejie.model.ResourceInfo;
import com.sai628.moviejie.service.net.NetHelper;
import com.sai628.moviejie.service.net.NetService;
import com.sai628.moviejie.utils.ContextUtil;
import com.sai628.moviejie.utils.ToastUtil;
import com.sai628.moviejie.view.LoadingMenu;
import com.sai628.moviejie.viewadapter.NewestInfoListViewAdapter;

import java.util.ArrayList;

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
            String movieLink = resourceInfo.getMovie_link();
            String link = resourceInfo.getLink();
            if (!TextUtils.isEmpty(movieLink))
            {
                ContextUtil.readMovieInfo(getActivity(), movieLink);
            }
            else if (!TextUtils.isEmpty(link))
            {
                ContextUtil.readLinkInfo(getActivity(), link);
            }
            else
            {
                ToastUtil.show(getActivity(), "未支持");
            }
        }
    };
}
