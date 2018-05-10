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
import com.sai628.moviejie.model.MovieSimpleInfo;
import com.sai628.moviejie.service.net.NetHelper;
import com.sai628.moviejie.service.net.NetService;
import com.sai628.moviejie.utils.CollectionUtil;
import com.sai628.moviejie.utils.ViewUtil;
import com.sai628.moviejie.view.LoadingMenu;
import com.sai628.moviejie.viewadapter.MovieListViewAdapter;

import java.util.ArrayList;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
 * @author Sai
 * @ClassName: NewTvFragment
 * @Description: "最新电视剧"视图
 * @date 09/05/2018 16:58
 */
public class NewTvFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, LoadingMenu.OnRetryClickListener
{
    private BGARefreshLayout refreshLayout;
    private PagingListView movieListView;
    private LoadingMenu loadingMenu;

    private MovieListViewAdapter movieListViewAdapter;
    private ArrayList<MovieSimpleInfo> movieSimpleInfos = new ArrayList<>();
    private int currentPage = 1;
    private boolean isFirstIn = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        initRootView(inflater, R.layout.fragment_new_movie_view, container);
        if (isFirstIn)
        {
            isFirstIn = false;
            loadingMenu.showLoading();
            loadNewMovies();
        }

        return rootView;
    }


    @Override
    protected void initChildView(View view)
    {
        refreshLayout = view.findViewById(R.id.fragment_new_movie_view_refresh_layout);
        movieListView = view.findViewById(R.id.fragment_new_movie_view_listview);

        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), false);
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(refreshViewHolder);

        movieListViewAdapter = new MovieListViewAdapter(getActivity(), movieSimpleInfos);
        movieListView.setAdapter(movieListViewAdapter);
        movieListView.setHasMoreItems(false);
        movieListView.setPagingableListener(pagingableListener);
        movieListView.setOnItemClickListener(movieListViewOnItemClickListener);

        loadingMenu = view.findViewById(R.id.loading_menu);
        loadingMenu.setOnRetryClickListener(this);
    }


    @SuppressWarnings("unchecked")
    private void loadNewMovies()
    {
        NetService.getNewTv(getActivity(), "p1", new NetHelper.NetCallback()
        {
            @Override
            public void onSuccess(Object data)
            {
                ArrayList<MovieSimpleInfo> items = (ArrayList<MovieSimpleInfo>) data;
                if (CollectionUtil.isEmpty(items))
                {
                    loadingMenu.showEmpty();
                    return;
                }

                loadingMenu.dismiss();
                currentPage = 2;
                movieListViewAdapter.replaceAllItems(items);
                movieListView.setHasMoreItems(true);
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
    private void refreshNewMovies()
    {
        NetService.getNewTv(getActivity(), "p1", new NetHelper.NetCallback()
        {
            @Override
            public void onSuccess(Object data)
            {
                ViewUtil.endRefreshingDelayed(refreshLayout);

                ArrayList<MovieSimpleInfo> items = (ArrayList<MovieSimpleInfo>) data;
                if (CollectionUtil.isEmpty(items))
                {
                    return;
                }

                currentPage = 2;
                movieListViewAdapter.replaceAllItems(items);
                movieListView.setHasMoreItems(true);
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
    private void loadMoreMovies()
    {
        NetService.getNewTv(getActivity(), "p" + currentPage, new NetHelper.NetCallback()
        {
            @Override
            public void onSuccess(Object data)
            {
                ArrayList<MovieSimpleInfo> items = (ArrayList<MovieSimpleInfo>) data;
                boolean hasMore = CollectionUtil.getSize(items) >= 10;
                movieListView.setHasMoreItems(hasMore);
                movieListView.onFinishLoading(hasMore, items);
                currentPage += (hasMore ? 1 : 0);
            }


            @Override
            public void onError(int errorCode, String errorMsg)
            {
                movieListView.onFinishLoading(true, null);
            }


            @Override
            public void onFailure(String failureMsg)
            {
                movieListView.onFinishLoading(true, null);
            }
        });
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout)
    {
        // 正在上拉加载更多时, 下拉刷新马上返回
        if (movieListView.isLoading())
        {
            refreshLayout.endRefreshing();
            return;
        }

        refreshNewMovies();
    }


    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout)
    {
        return false;
    }


    @Override
    public void onRetryClick(View v)
    {
        loadNewMovies();
    }


    private AdapterView.OnItemClickListener movieListViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            //TODO
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
                movieListView.onFinishLoading(true, null);
                return;
            }

            loadMoreMovies();
        }
    };
}
