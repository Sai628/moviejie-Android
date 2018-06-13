package com.sai628.moviejie.activity.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sai628.moviejie.R;
import com.sai628.moviejie.activity.base.BaseActivity;
import com.sai628.moviejie.model.LinkInfo;
import com.sai628.moviejie.model.MovieInfo;
import com.sai628.moviejie.model.ResourceInfo;
import com.sai628.moviejie.service.net.NetHelper;
import com.sai628.moviejie.service.net.NetService;
import com.sai628.moviejie.utils.CollectionUtil;
import com.sai628.moviejie.utils.ContextUtil;
import com.sai628.moviejie.utils.DisplayUtil;
import com.sai628.moviejie.utils.ImageLoaderUtil;
import com.sai628.moviejie.utils.StringUtil;
import com.sai628.moviejie.utils.ViewUtil;
import com.sai628.moviejie.view.LetterSpacingTextView;
import com.sai628.moviejie.view.LoadingMenu;
import com.sai628.moviejie.viewadapter.CommonAdapter;
import com.sai628.moviejie.viewadapter.ViewHolderHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.ColorFilterTransformation;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;


/**
 * @author Sai
 * @ClassName: MovieInfoActivity
 * @Description: "电影/电视剧"信息视图
 * @date 11/05/2018 12:41
 */
public class MovieInfoActivity extends BaseActivity implements View.OnClickListener, LoadingMenu.OnRetryClickListener
{
    public static final String EXTRA_MOVIE_LINK = "movie_link";

    private static final int REQUEST_CODE_EPISODE_FILTER = 0;

    private ImageView backIv;
    private TextView episodeFilterMenu;

    private ScrollView scrollView;

    private ImageView bannerBgIv;
    private ImageView bannerIv;

    private TextView titleTv;
    private TextView baseInfoTv;
    private View starLayout;
    private TextView starTv;
    private MaterialRatingBar ratingBar;
    private TextView starEmptyTipTv;

    private TextView linkHeaderTv;
    private ListView linkListView;
    private TextView relatedHeaderTv;
    private ListView relatedListView;
    private TextView recommendedHeaderTv;
    private ListView recommendedListView;
    private TextView storyHeaderTv;
    private LetterSpacingTextView storyTv;

    private LoadingMenu loadingMenu;

    private MovieLinkListViewAdapter linkListViewAdapter;
    private MovieInfo movieInfo;
    private String currentEpisode = "";  // 当前分集(默认为空, 表示显示全部的分集)
    private String movieLink;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_movie_info_view);

        initData();
        initView();
        loadingMenu.showLoading();
        loadMovieInfo();
    }


    private void initData()
    {
        movieLink = getIntent().getStringExtra(EXTRA_MOVIE_LINK);
    }


    private void initView()
    {
        backIv = findViewById(R.id.info_moview_info_view_back_imageview);
        backIv.setOnClickListener(this);
        episodeFilterMenu = findViewById(R.id.info_movie_info_view_episode_filter_textview);
        episodeFilterMenu.setOnClickListener(this);

        scrollView = findViewById(R.id.info_movie_info_view_scrollview);

        bannerBgIv = findViewById(R.id.content_view_banner_bg_imageview);
        bannerIv = findViewById(R.id.content_view_banner_imageview);

        titleTv = findViewById(R.id.content_view_title_textview);
        baseInfoTv = findViewById(R.id.content_view_base_info_view);
        starLayout = findViewById(R.id.content_view_star_layout_ll);
        starTv = findViewById(R.id.content_view_star_textview);
        ratingBar = findViewById(R.id.content_view_ratingbar);
        starEmptyTipTv = findViewById(R.id.content_view_star_empty_tips_textview);

        linkHeaderTv = findViewById(R.id.info_movie_info_view_link_header_textview);
        linkListView = findViewById(R.id.info_movie_info_view_link_listview);
        relatedHeaderTv = findViewById(R.id.info_movie_info_view_related_header_textview);
        relatedListView = findViewById(R.id.info_movie_info_view_related_listview);
        recommendedHeaderTv = findViewById(R.id.info_movie_info_view_recommended_header_textview);
        recommendedListView = findViewById(R.id.info_movie_info_view_recommended_listview);
        storyHeaderTv = findViewById(R.id.info_movie_info_view_story_header_textview);
        storyTv = findViewById(R.id.info_movie_info_view_story_textview);

        loadingMenu = findViewById(R.id.loading_menu);
        loadingMenu.setOnRetryClickListener(this);
    }


    private void updateUI(MovieInfo info)
    {
        movieInfo = info;

        // "分集"菜单
        if (CollectionUtil.getSize(movieInfo.getEpisode_filters()) > 0)
        {
            episodeFilterMenu.setVisibility(View.VISIBLE);
        }

        // Banner
        Picasso.with(this).load(StringUtil.getNotEmptyUrl(movieInfo.getBanner()))
                .transform(new BlurTransformation(this, 15))
                .transform(new ColorFilterTransformation(0x77000000))
                .into(bannerBgIv);
        ImageLoaderUtil.loadImage(this, bannerIv, movieInfo.getBanner());

        // 基本信息
        titleTv.setText(movieInfo.getTitle());
        baseInfoTv.setText(getBaseInfoText(movieInfo));
        ViewUtil.bindShadow(starLayout, 0xBBAAAAAA, DisplayUtil.dip2px(this, 2), DisplayUtil.dip2px(this, 2), DisplayUtil.dip2px(this, 2));

        if (StringUtil.isNumber(movieInfo.getStar()))  // 有评分
        {
            starTv.setText(movieInfo.getStar());
            ratingBar.setRating(Float.valueOf(movieInfo.getStar()) / 2.0f);
            starTv.setVisibility(View.VISIBLE);
            starEmptyTipTv.setVisibility(View.GONE);
        }
        else // 无评分
        {
            starTv.setText(null);
            ratingBar.setRating(0f);
            starTv.setVisibility(View.GONE);
            starEmptyTipTv.setVisibility(View.VISIBLE);
        }

        // 下载链接
        List<LinkInfo> linkInfos = movieInfo.getLinks();
        if (!CollectionUtil.isEmpty(linkInfos))
        {
            linkHeaderTv.setVisibility(View.VISIBLE);
            linkListView.setVisibility(View.VISIBLE);
            linkListViewAdapter = new MovieLinkListViewAdapter(this, linkInfos);
            linkListView.setAdapter(linkListViewAdapter);
            linkListView.setOnItemClickListener(movieLinkListViewOnItemClickListener);
        }

        // 相关资源
        List<ResourceInfo> relatedInfos = movieInfo.getRelated_resources();
        if (!CollectionUtil.isEmpty(relatedInfos))
        {
            relatedHeaderTv.setVisibility(View.VISIBLE);
            relatedListView.setVisibility(View.VISIBLE);
            relatedListView.setAdapter(new MovieRelatedListViewAdapter(this, relatedInfos));
            relatedListView.setOnItemClickListener(movieRelatedListViewOnItemClickListener);
        }

        // 推荐资源
        List<ResourceInfo> recommendedInfos = movieInfo.getRecommended_resources();
        if (!CollectionUtil.isEmpty(recommendedInfos))
        {
            recommendedHeaderTv.setVisibility(View.VISIBLE);
            recommendedListView.setVisibility(View.VISIBLE);
            recommendedListView.setAdapter(new MovieRelatedListViewAdapter(this, recommendedInfos));
            recommendedListView.setOnItemClickListener(movieRecommendedListViewOnItemClickListener);
        }

        // 剧情简介
        String story = movieInfo.getStory();
        if (!TextUtils.isEmpty(story))
        {
            storyHeaderTv.setVisibility(View.VISIBLE);
            storyTv.setVisibility(View.VISIBLE);
            storyTv.setSpacing(2f);
            storyTv.setText(story);
        }

        // 自动滚动到最顶部, 解决显示页面时因列表项过长而定位不正确的问题
        scrollView.post(new Runnable()
        {
            @Override
            public void run()
            {
                scrollView.scrollTo(0, 0);
            }
        });
    }


    private String getBaseInfoText(MovieInfo movieInfo)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(!TextUtils.isEmpty(movieInfo.getDirectors()) ? "导演: " + movieInfo.getDirectors() + "\n" : "");
        sb.append(!TextUtils.isEmpty(movieInfo.getWriters()) ? "编剧: " + movieInfo.getWriters() + "\n" : "");
        sb.append(!TextUtils.isEmpty(movieInfo.getStars()) ? "主演: " + movieInfo.getStars() + "\n" : "");
        sb.append(!TextUtils.isEmpty(movieInfo.getGenres()) ? "类型: " + movieInfo.getGenres() + "\n" : "");
        sb.append(!TextUtils.isEmpty(movieInfo.getCountry()) ? "国家/地区: " + movieInfo.getCountry() + "\n" : "");
        sb.append(!TextUtils.isEmpty(movieInfo.getRelease_date()) ? "上映日期: " + movieInfo.getRelease_date() + "\n" : "");
        sb.append(!TextUtils.isEmpty(movieInfo.getRuntime()) ? "片长: " + movieInfo.getRuntime() + "\n" : "");
        sb.append(!TextUtils.isEmpty(movieInfo.getAkaname()) ? "又名: " + movieInfo.getAkaname() : "");

        return sb.toString();
    }


    private List<LinkInfo> filterLinks(List<LinkInfo> linkInfos, String episode)
    {
        ArrayList<LinkInfo> filterLinks = new ArrayList<>();
        if (CollectionUtil.isEmpty(linkInfos))
        {
            return filterLinks;
        }

        if (episode.equals("all"))  // 全部分集
        {
            filterLinks.addAll(linkInfos);
        }
        else
        {
            // 过滤出选中的分集
            for (LinkInfo linkInfo : linkInfos)
            {
                if (linkInfo.getEpisode().equals(episode))
                {
                    filterLinks.add(linkInfo);
                }
            }
        }

        return filterLinks;
    }


    private void loadMovieInfo()
    {
        NetService.getMovieInfo(this, movieLink, new NetHelper.NetCallback()
        {
            @Override
            public void onSuccess(Object data)
            {
                MovieInfo movieInfo = (MovieInfo) data;
                if (movieInfo == null)
                {
                    loadingMenu.showEmpty();
                    return;
                }

                loadingMenu.dismiss();
                updateUI(movieInfo);
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


    private void doEpisodeFilter()
    {
        Intent intent = new Intent(this, EpisodeFilterActivity.class);
        intent.putExtra(EpisodeFilterActivity.EXTRA_EPISODE_FILTERS, new ArrayList<>(movieInfo.getEpisode_filters()));
        intent.putExtra(EpisodeFilterActivity.EXTRA_SELECTED_EPISODE, currentEpisode);
        startActivityForResult(intent, REQUEST_CODE_EPISODE_FILTER);
    }


    @Override
    public void onRetryClick(View v)
    {
        loadMovieInfo();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.info_moview_info_view_back_imageview:
                finish();
                break;

            case R.id.info_movie_info_view_episode_filter_textview:
                doEpisodeFilter();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_EPISODE_FILTER)
        {
            currentEpisode = data.getStringExtra(EpisodeFilterActivity.EXTRA_SELECTED_EPISODE);
            String currentEpisodeText = data.getStringExtra(EpisodeFilterActivity.EXTRA_EPISODE_TEXT);

            List<LinkInfo> filterLinks = filterLinks(movieInfo.getLinks(), currentEpisode);
            linkHeaderTv.setVisibility(filterLinks.size() > 0 ? View.VISIBLE : View.GONE);
            linkHeaderTv.setText(String.format(Locale.CHINA, "下载链接 %s", currentEpisodeText));

            linkListViewAdapter.replaceAll(filterLinks);
            linkListView.setVisibility(filterLinks.size() > 0 ? View.VISIBLE : View.GONE);
            scrollView.post(new Runnable()
            {
                @Override
                public void run()
                {
                    scrollView.scrollTo(0, linkHeaderTv.getTop());
                }
            });
        }
    }


    private AdapterView.OnItemClickListener movieLinkListViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            LinkInfo linkInfo = (LinkInfo) parent.getItemAtPosition(position);
            if (!TextUtils.isEmpty(linkInfo.getLink()))
            {
                ContextUtil.readLinkInfo(getThis(), linkInfo.getLink());
            }
        }
    };


    private AdapterView.OnItemClickListener movieRelatedListViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            ResourceInfo resourceInfo = (ResourceInfo) parent.getItemAtPosition(position);
            ContextUtil.readMovieInfo(getThis(), resourceInfo.getMovie_link());
        }
    };


    private AdapterView.OnItemClickListener movieRecommendedListViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            ResourceInfo resourceInfo = (ResourceInfo) parent.getItemAtPosition(position);
            ContextUtil.readMovieInfo(getThis(), resourceInfo.getMovie_link());
        }
    };


    private class MovieLinkListViewAdapter extends CommonAdapter<LinkInfo>
    {
        MovieLinkListViewAdapter(Context context, List<LinkInfo> linkInfos)
        {
            super(context, R.layout.listview_item_movie_link, linkInfos);
        }


        @Override
        public void convert(ViewHolderHelper helper, LinkInfo linkInfo, int position)
        {
            helper.setText(R.id.listview_item_name_textview, linkInfo.getName());  // 资源名称
            helper.setText(R.id.listview_item_size_textview, linkInfo.getSize());  // 资源大小
            helper.setText(R.id.listview_item_other_info_textview, linkInfo.getDimen() + "/" + linkInfo.getFormat());  // 资源尺寸/格式

            helper.setTextColor(R.id.listview_item_name_textview, mContext.getResources().getColor(!TextUtils.isEmpty(linkInfo.getLink())
                    ? R.color.link : R.color._999));
            helper.setVisibility(R.id.listview_item_more_imageview, !TextUtils.isEmpty(linkInfo.getLink()) ? View.VISIBLE : View.INVISIBLE);
        }
    }


    private class MovieRelatedListViewAdapter extends CommonAdapter<ResourceInfo>
    {
        MovieRelatedListViewAdapter(Context context, List<ResourceInfo> resourceInfos)
        {
            super(context, R.layout.listview_item_movie_related, resourceInfos);
        }


        @Override
        public void convert(ViewHolderHelper helper, ResourceInfo resourceInfo, int position)
        {
            helper.setText(R.id.listview_item_title_textview, resourceInfo.getTitle());  // 资源名称
        }
    }
}
