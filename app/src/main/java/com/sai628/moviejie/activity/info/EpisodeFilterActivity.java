package com.sai628.moviejie.activity.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sai628.moviejie.R;
import com.sai628.moviejie.activity.base.BaseActivity;
import com.sai628.moviejie.viewadapter.CommonAdapter;
import com.sai628.moviejie.viewadapter.ViewHolderHelper;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Sai
 * @ClassName: EpisodeFilterActivity
 * @Description: "分集过滤"视图
 * @date 12/06/2018 17:32
 */
public class EpisodeFilterActivity extends BaseActivity implements View.OnClickListener
{
    public static final String EXTRA_EPISODE_FILTERS = "episode_filters";  // 分集列表数据键名. 跳转到该视图时, 需设置该键名对应的值
    public static final String EXTRA_SELECTED_EPISODE = "selected_episode";  // 选中的分集数据键名. 跳转到该视图时, 可设置该键名对应的值

    public static final String EXTRA_EPISODE_TEXT = "episode_text";  // 分集名称提示文字键名. 点击分集子项时, 上一级的视图可通过该键名获取点击中的分集

    private ImageView backIv;
    private GridView episodeFilterGridView;

    private ArrayList<HashMap<String, String>> episodeFilters;  // 分集列表. 跳转到该视图时, 需设置该参数. Item的结构为: {"episode": String, "text": String}
    private String selectedEpisode; // 默认选中的分集. 当跳转到该视图并且设置该参数时, 对应的分集子项将为选中的状态


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_episode_filter_view);

        initData();
        initView();
    }


    @SuppressWarnings("unchecked")
    private void initData()
    {
        Intent intent = getIntent();
        episodeFilters = (ArrayList<HashMap<String, String>>) intent.getSerializableExtra(EXTRA_EPISODE_FILTERS);
        selectedEpisode = intent.getStringExtra(EXTRA_SELECTED_EPISODE);
    }


    private void initView()
    {
        backIv = findViewById(R.id.info_episode_filter_view_back_imageview);
        backIv.setOnClickListener(this);

        episodeFilterGridView = findViewById(R.id.info_episode_filter_view_gridview);
        episodeFilterGridView.setAdapter(new EpisodeFilterAdapter(this, episodeFilters, selectedEpisode));
        episodeFilterGridView.setOnItemClickListener(episodeFilterGridViewOnItemClickListener);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.info_episode_filter_view_back_imageview:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }


    private AdapterView.OnItemClickListener episodeFilterGridViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            HashMap<String, String> map = episodeFilters.get(position);
            String episode = map.get("episode");
            String episodeText = map.get("text");

            Intent intent = new Intent();
            intent.putExtra(EXTRA_SELECTED_EPISODE, episode);
            intent.putExtra(EXTRA_EPISODE_TEXT, episodeText);
            setResult(RESULT_OK, intent);
            finish();
        }
    };


    private class EpisodeFilterAdapter extends CommonAdapter<HashMap<String, String>>
    {
        private String selectedEpisode;


        EpisodeFilterAdapter(Context context, ArrayList<HashMap<String, String>> data, String selectedEpisode)
        {
            super(context, R.layout.gridview_item_episode_filter, data);
            this.selectedEpisode = selectedEpisode;
        }


        @Override
        public void convert(ViewHolderHelper helper, HashMap<String, String> map, int position)
        {
            String episode = map.get("episode");
            String episodeText = map.get("text");
            int color = mContext.getResources().getColor(episode.equals(selectedEpisode) ? R.color.link : R.color._333);

            TextView titleTv = helper.getView(R.id.gridview_item_title_textview);
            titleTv.setText(episodeText);
            titleTv.setTextColor(color);
        }
    }
}
