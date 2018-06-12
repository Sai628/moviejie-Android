package com.sai628.moviejie.viewadapter;

import android.content.Context;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paging.listview.PagingBaseAdapter;
import com.sai628.moviejie.R;
import com.sai628.moviejie.model.MovieSimpleInfo;
import com.sai628.moviejie.utils.HtmlUtil;
import com.sai628.moviejie.utils.ImageLoaderUtil;
import com.sai628.moviejie.utils.StringUtil;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;


/**
 * @author Sai
 * @ClassName: MovieListViewAdapter
 * @Description: 最新电影列表适配器
 * @date 10/05/2018 21:15
 */
public class MovieListViewAdapter extends PagingBaseAdapter<MovieSimpleInfo>
{
    private String keyword;


    public MovieListViewAdapter(Context context, List<MovieSimpleInfo> movieSimpleInfos, String keyword)
    {
        super(context, movieSimpleInfos);
        this.keyword = keyword;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolderHelper helper = ViewHolderHelper.get(context, convertView, parent, R.layout.listview_item_new_movie, position);
        MovieSimpleInfo movieSimpleInfo = (MovieSimpleInfo) getItem(position);

        ImageView bannerIv = helper.getView(R.id.listview_item_imageview);
        TextView titleTv = helper.getView(R.id.listview_item_title_textview);
        MaterialRatingBar ratingBar = helper.getView(R.id.listview_item_ratingbar);
        TextView ratingBarTv = helper.getView(R.id.listview_item_ratingbar_textview);
        TextView starEmptyTipTv = helper.getView(R.id.listview_item_start_empty_tip_textview);
        TextView genresTv = helper.getView(R.id.listview_item_genres_textview);
        TextView countryTv = helper.getView(R.id.listview_item_country_textview);

        ImageLoaderUtil.loadImage(context, bannerIv, movieSimpleInfo.getBanner());

        Spanned titleSpanned = HtmlUtil.formatHtml(context, movieSimpleInfo.getTitle(), keyword, R.string.text_match_color);
        titleTv.setText(titleSpanned);  // 标题
        if (StringUtil.isNumber(movieSimpleInfo.getStar()))  // 有评分时
        {
            starEmptyTipTv.setVisibility(View.INVISIBLE);
            ratingBarTv.setText(movieSimpleInfo.getStar());
            ratingBar.setRating(Float.valueOf(movieSimpleInfo.getStar()) / 2.0f);
            ratingBar.setVisibility(View.VISIBLE);
        }
        else   // 无评分时
        {
            starEmptyTipTv.setVisibility(View.VISIBLE);
            ratingBarTv.setText(null);
            ratingBar.setVisibility(View.INVISIBLE);
        }
        genresTv.setText(String.format("类型: %s", movieSimpleInfo.getGenres()));
        countryTv.setText(String.format("国家/地区: %s", movieSimpleInfo.getCountry()));

        return helper.getConvertView();
    }
}
