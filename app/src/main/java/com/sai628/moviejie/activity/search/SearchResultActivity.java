package com.sai628.moviejie.activity.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.paging.listview.PagingListView;
import com.sai628.moviejie.R;
import com.sai628.moviejie.activity.base.BaseActivity;
import com.sai628.moviejie.model.MovieSimpleInfo;
import com.sai628.moviejie.service.net.NetHelper;
import com.sai628.moviejie.service.net.NetService;
import com.sai628.moviejie.utils.CollectionUtil;
import com.sai628.moviejie.utils.ContextUtil;
import com.sai628.moviejie.view.LoadingMenu;
import com.sai628.moviejie.viewadapter.MovieListViewAdapter;

import java.util.ArrayList;
import java.util.Locale;


/**
 * @author Sai
 * @ClassName: SearchResultActivity
 * @Description: "搜索结果"视图
 * @date 12/06/2018 14:57
 */
public class SearchResultActivity extends BaseActivity implements LoadingMenu.OnRetryClickListener, View.OnClickListener
{
    public static final String EXTRA_KEYWORD = "keyword";

    private ImageView backIv;
    private TextView titleTv;
    private PagingListView movieListView;
    private LoadingMenu loadingMenu;

    private MovieListViewAdapter movieListViewAdapter;
    private ArrayList<MovieSimpleInfo> movieSimpleInfos = new ArrayList<>();
    private int currentPage = 1;

    private String keyword;  // 搜索关键字, 跳转到该视图时需传该参数


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_view);

        preInitData();
        initView();
        loadingMenu.showLoading();
        loadSearchResult();
    }


    private void preInitData()
    {
        keyword = getIntent().getStringExtra(EXTRA_KEYWORD);
    }


    private void initView()
    {
        backIv = findViewById(R.id.search_result_view_back_imageview);
        backIv.setOnClickListener(this);
        titleTv = findViewById(R.id.search_result_view_title_textview);
        titleTv.setText(String.format(Locale.CHINA, "搜索\"%s\"", keyword));

        movieListView = findViewById(R.id.search_result_view_listview);
        movieListViewAdapter = new MovieListViewAdapter(this, movieSimpleInfos, keyword);
        movieListView.setAdapter(movieListViewAdapter);
        movieListView.setHasMoreItems(false);
        movieListView.setPagingableListener(pagingableListener);
        movieListView.setOnItemClickListener(movieListViewOnItemClickListener);

        loadingMenu = findViewById(R.id.loading_menu);
        loadingMenu.setOnRetryClickListener(this);
    }


    @SuppressWarnings("unchecked")
    private void loadSearchResult()
    {
        NetService.search(this, keyword, "p1", new NetHelper.NetCallback()
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
                movieListView.setHasMoreItems(items.size() >= 10);
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
    private void loadMoreSearchResult()
    {
        NetService.search(this, keyword, "p" + currentPage, new NetHelper.NetCallback()
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
    public void onRetryClick(View v)
    {
        loadSearchResult();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.search_result_view_back_imageview:
                finish();
                break;
        }
    }


    private AdapterView.OnItemClickListener movieListViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            MovieSimpleInfo movieSimpleInfo = (MovieSimpleInfo) parent.getAdapter().getItem(position);
            ContextUtil.readMovieInfo(getThis(), movieSimpleInfo.getMovie_link());
        }
    };


    private PagingListView.Pagingable pagingableListener = new PagingListView.Pagingable()
    {
        @Override
        public void onLoadMoreItems()
        {
            loadMoreSearchResult();
        }
    };
}
