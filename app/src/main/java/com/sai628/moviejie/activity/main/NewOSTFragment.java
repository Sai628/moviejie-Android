package com.sai628.moviejie.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.paging.listview.PagingListView;
import com.sai628.moviejie.R;
import com.sai628.moviejie.activity.base.BaseFragment;
import com.sai628.moviejie.model.OSTSimpleInfo;
import com.sai628.moviejie.service.net.NetHelper;
import com.sai628.moviejie.service.net.NetService;
import com.sai628.moviejie.utils.CollectionUtil;
import com.sai628.moviejie.utils.ContextUtil;
import com.sai628.moviejie.utils.ViewUtil;
import com.sai628.moviejie.view.LoadingMenu;
import com.sai628.moviejie.viewadapter.OSTListViewAdapter;

import java.util.ArrayList;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
 * @author Sai
 * @ClassName: NewOSTFragment
 * @Description: "原声大碟"视图
 * @date 09/05/2018 16:58
 */
public class NewOSTFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, LoadingMenu.OnRetryClickListener
{
    private BGARefreshLayout refreshLayout;
    private PagingListView ostListView;
    private LoadingMenu loadingMenu;

    private OSTListViewAdapter ostListViewAdapter;
    private ArrayList<OSTSimpleInfo> ostSimpleInfos = new ArrayList<>();
    private int currentPage = 1;
    private boolean isFirstIn = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        initRootView(inflater, R.layout.fragment_new_ost_view, container);
        if (isFirstIn)
        {
            isFirstIn = false;
            loadingMenu.showLoading();
            loadNewOSTInfos();
        }

        return rootView;
    }


    @Override
    protected void initChildView(View view)
    {
        refreshLayout = view.findViewById(R.id.fragment_new_ost_view_refresh_layout);
        ostListView = view.findViewById(R.id.fragment_new_ost_view_listview);

        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), false);
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(refreshViewHolder);

        ostListViewAdapter = new OSTListViewAdapter(getActivity(), ostSimpleInfos);
        ostListView.setAdapter(ostListViewAdapter);
        ostListView.setHasMoreItems(false);
        ostListView.setPagingableListener(pagingableListener);
        ostListView.setOnItemClickListener(ostListViewOnItemClickListener);

        loadingMenu = view.findViewById(R.id.loading_menu);
        loadingMenu.setOnRetryClickListener(this);
    }


    @SuppressWarnings("unchecked")
    private void loadNewOSTInfos()
    {
        NetService.getNewOST(getActivity(), "p1", new NetHelper.NetCallback()
        {
            @Override
            public void onSuccess(Object data)
            {
                ArrayList<OSTSimpleInfo> items = (ArrayList<OSTSimpleInfo>) data;
                if (CollectionUtil.isEmpty(items))
                {
                    loadingMenu.showEmpty();
                    return;
                }

                loadingMenu.dismiss();
                currentPage = 2;
                ostListViewAdapter.replaceAllItems(items);
                ostListView.setHasMoreItems(true);
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


    @SuppressWarnings("unchecked")
    private void refreshNewOSTInfos()
    {
        NetService.getNewOST(getActivity(), "p1", new NetHelper.NetCallback()
        {
            @Override
            public void onSuccess(Object data)
            {
                ViewUtil.endRefreshingDelayed(refreshLayout);

                ArrayList<OSTSimpleInfo> items = (ArrayList<OSTSimpleInfo>) data;
                if (CollectionUtil.isEmpty(items))
                {
                    return;
                }

                currentPage = 2;
                ostListViewAdapter.replaceAllItems(items);
                ostListView.setHasMoreItems(true);
            }


            @Override
            public void onError(int errorCode, String errorMsg)
            {
                ViewUtil.endRefreshingDelayed(refreshLayout);
            }


            @Override
            public void onFailure(String failureMsg)
            {
                ViewUtil.endRefreshingDelayed(refreshLayout);
            }
        });
    }


    @SuppressWarnings("unchecked")
    private void loadMoreOSTInfos()
    {
        NetService.getNewOST(getActivity(), "p" + currentPage, new NetHelper.NetCallback()
        {
            @Override
            public void onSuccess(Object data)
            {
                ArrayList<OSTSimpleInfo> items = (ArrayList<OSTSimpleInfo>) data;
                boolean hasMore = CollectionUtil.getSize(items) >= 10;
                ostListView.setHasMoreItems(hasMore);
                ostListView.onFinishLoading(hasMore, items);
                currentPage += (hasMore ? 1 : 0);
            }


            @Override
            public void onError(int errorCode, String errorMsg)
            {
                ostListView.onFinishLoading(true, null);
            }


            @Override
            public void onFailure(String failureMsg)
            {
                ostListView.onFinishLoading(true, null);
            }
        });
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout)
    {
        // 正在上拉加载更多时, 下拉刷新马上返回
        if (ostListView.isLoading())
        {
            refreshLayout.endRefreshing();
            return;
        }

        refreshNewOSTInfos();
    }


    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout)
    {
        return false;
    }


    @Override
    public void onRetryClick(View v)
    {
        loadNewOSTInfos();
    }


    private AdapterView.OnItemClickListener ostListViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            OSTSimpleInfo ostSimpleInfo = (OSTSimpleInfo) parent.getAdapter().getItem(position);
            ContextUtil.readOSTInfo(getActivity(), ostSimpleInfo.getOst_link());
        }
    };


    private PagingListView.Pagingable pagingableListener = new PagingListView.Pagingable()
    {
        @Override
        public void onLoadMoreItems()
        {
            // 正在下拉刷新时, 上拉加载更多马上返回
            if (refreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING)
            {
                ostListView.onFinishLoading(true, null);
                return;
            }

            loadMoreOSTInfos();
        }
    };
}
