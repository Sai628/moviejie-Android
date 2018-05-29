package com.sai628.moviejie.activity.info;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sai628.moviejie.R;
import com.sai628.moviejie.activity.base.BaseActivity;
import com.sai628.moviejie.model.LinkInfo;
import com.sai628.moviejie.model.OSTInfo;
import com.sai628.moviejie.service.net.NetHelper;
import com.sai628.moviejie.service.net.NetService;
import com.sai628.moviejie.utils.CollectionUtil;
import com.sai628.moviejie.utils.ContextUtil;
import com.sai628.moviejie.utils.ImageLoaderUtil;
import com.sai628.moviejie.utils.StringUtil;
import com.sai628.moviejie.view.LetterSpacingTextView;
import com.sai628.moviejie.view.LoadingMenu;
import com.sai628.moviejie.viewadapter.CommonAdapter;
import com.sai628.moviejie.viewadapter.ViewHolderHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.ColorFilterTransformation;


/**
 * @author Sai
 * @ClassName: OSTInfoActivity
 * @Description: "原声大碟"信息视图
 * @date 29/05/2018 19:45
 */
public class OSTInfoActivity extends BaseActivity implements View.OnClickListener, LoadingMenu.OnRetryClickListener
{
    public static final String EXTRA_OST_LINK = "ost_link";

    private ImageView backIv;

    private ImageView bannerBgIv;
    private ImageView bannerIv;

    private TextView titleTv;
    private TextView baseInfoTv;
    private TextView sourceHeaderTv;
    private ListView sourceListView;
    private TextView trackListHeaderTv;
    private LetterSpacingTextView trackListTv;
    private TextView linkHeaderTv;
    private ListView linkListView;

    private LoadingMenu loadingMenu;

    private OSTInfo ostInfo;
    private String ostLink;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_ost_info_view);

        initData();
        initView();
        loadingMenu.showLoading();
        loadOSTInfo();
    }


    private void initData()
    {
        ostLink = getIntent().getStringExtra(EXTRA_OST_LINK);
    }


    private void initView()
    {
        backIv = findViewById(R.id.info_ost_info_view_back_imageview);
        backIv.setOnClickListener(this);

        bannerBgIv = findViewById(R.id.content_view_banner_bg_imageview);
        bannerIv = findViewById(R.id.content_view_banner_imageview);

        titleTv = findViewById(R.id.content_view_title_textview);
        baseInfoTv = findViewById(R.id.content_view_base_info_view);
        sourceHeaderTv = findViewById(R.id.info_ost_info_view_source_header_textview);
        sourceListView = findViewById(R.id.info_ost_info_view_source_listview);
        trackListHeaderTv = findViewById(R.id.info_ost_info_view_track_list_header_textview);
        trackListTv = findViewById(R.id.info_ost_info_view_track_list_textview);
        linkHeaderTv = findViewById(R.id.info_ost_info_view_link_header_textview);
        linkListView = findViewById(R.id.info_ost_info_view_link_listview);

        loadingMenu = findViewById(R.id.loading_menu);
        loadingMenu.setOnRetryClickListener(this);
    }


    private void updateUI(OSTInfo info)
    {
        ostInfo = info;

        // Banner
        Picasso.with(this).load(StringUtil.getNotEmptyUrl(ostInfo.getBanner()))
                .transform(new BlurTransformation(this, 15))
                .transform(new ColorFilterTransformation(0x77000000))
                .into(bannerBgIv);
        ImageLoaderUtil.loadImage(this, bannerIv, ostInfo.getBanner());

        // 基本信息
        titleTv.setText(ostInfo.getMovie_name());
        baseInfoTv.setText(getBaseInfoText(ostInfo));

        // 来源电影/电视剧
        String source = ostInfo.getMovie_name();
        if (!TextUtils.isEmpty(source))
        {
            List<String> sources = CollectionUtil.toArrayList(new String[]{source});
            sourceHeaderTv.setVisibility(View.VISIBLE);
            sourceListView.setVisibility(View.VISIBLE);
            sourceListView.setAdapter(new OSTSourceListViewAdapter(this, sources));
            sourceListView.setOnItemClickListener(sourceListViewOnItemClickListener);
        }

        // 专辑曲目
        List<String> trackList = ostInfo.getTrack_list();
        if (CollectionUtil.getSize(trackList) > 0)
        {
            String trackText = getTrackText(trackList);
            trackListHeaderTv.setVisibility(View.VISIBLE);
            trackListTv.setVisibility(View.VISIBLE);
            trackListTv.setSpacing(2f);
            trackListTv.setText(trackText);
        }

        // 下载链接
        List<LinkInfo> linkInfos = ostInfo.getLinks();
        if (!CollectionUtil.isEmpty(linkInfos))
        {
            linkHeaderTv.setVisibility(View.VISIBLE);
            linkListView.setVisibility(View.VISIBLE);
            linkListView.setAdapter(new OSTLinkListViewAdapter(this, linkInfos));
            linkListView.setOnItemClickListener(ostLinkListViewOnItemClickListener);
        }

    }


    private String getBaseInfoText(OSTInfo ostInfo)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(!TextUtils.isEmpty(ostInfo.getCountry()) ? "地区: " + ostInfo.getCountry() + "\n" : "");
        sb.append(!TextUtils.isEmpty(ostInfo.getLanguage()) ? "语言: " + ostInfo.getLanguage() + "\n" : "");
        sb.append(!TextUtils.isEmpty(ostInfo.getPublish_time()) ? "发行时间: " + ostInfo.getPublish_time() + "\n" : "");
        sb.append(!TextUtils.isEmpty(ostInfo.getFile_type()) ? "资源格式: " + ostInfo.getFile_type() : "");

        return sb.toString();
    }


    private String getTrackText(List<String> trackList)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < trackList.size(); i++)
        {
            sb.append(i).append(". ").append(trackList.get(i));
            if (i != trackList.size() - 1)
            {
                sb.append("\n");
            }
        }

        return sb.toString();
    }


    private void loadOSTInfo()
    {
        NetService.getOSTInfo(this, ostLink, new NetHelper.NetCallback()
        {
            @Override
            public void onSuccess(Object data)
            {
                OSTInfo ostInfo = (OSTInfo) data;
                if (ostInfo == null)
                {
                    loadingMenu.showEmpty();
                    return;
                }

                loadingMenu.dismiss();
                updateUI(ostInfo);
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
        loadOSTInfo();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.info_ost_info_view_back_imageview:
                finish();
                break;
        }
    }


    private AdapterView.OnItemClickListener sourceListViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            ContextUtil.readMovieInfo(getThis(), ostInfo.getMovie_link());
        }
    };


    private AdapterView.OnItemClickListener ostLinkListViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            LinkInfo linkInfo = ostInfo.getLinks().get(position);
            if (!TextUtils.isEmpty(linkInfo.getLink()))
            {
                ContextUtil.readLinkInfo(getThis(), linkInfo.getLink());
            }
        }
    };


    private class OSTSourceListViewAdapter extends CommonAdapter<String>
    {
        OSTSourceListViewAdapter(Context context, List<String> sources)
        {
            super(context, R.layout.listview_item_ost_source, sources);
        }


        @Override
        public void convert(ViewHolderHelper helper, String source, int position)
        {
            helper.setText(R.id.listview_item_title_textview, source);  // 来源电影/电视剧
        }
    }


    private class OSTLinkListViewAdapter extends CommonAdapter<LinkInfo>
    {
        OSTLinkListViewAdapter(Context context, List<LinkInfo> linkInfos)
        {
            super(context, R.layout.listview_item_ost_link, linkInfos);
        }


        @Override
        public void convert(ViewHolderHelper helper, LinkInfo linkInfo, int position)
        {
            helper.setText(R.id.listview_item_name_textview, linkInfo.getName());  // 资源名称
            helper.setText(R.id.listview_item_size_textview, linkInfo.getSize());  // 资源大小

            helper.setTextColor(R.id.listview_item_name_textview, mContext.getResources().getColor(!TextUtils.isEmpty(linkInfo.getLink())
                    ? R.color.link : R.color._999));
            helper.setVisibility(R.id.listview_item_more_imageview, !TextUtils.isEmpty(linkInfo.getLink()) ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
