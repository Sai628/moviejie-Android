package com.sai628.moviejie.viewadapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paging.listview.PagingBaseAdapter;
import com.sai628.moviejie.R;
import com.sai628.moviejie.model.OSTSimpleInfo;
import com.sai628.moviejie.utils.ImageLoaderUtil;

import java.util.List;


/**
 * @author Sai
 * @ClassName: OSTListViewAdapter
 * @Description: 最新原声大碟列表适配器
 * @date 10/11/2018 09:58
 */
public class OSTListViewAdapter extends PagingBaseAdapter<OSTSimpleInfo>
{
    public OSTListViewAdapter(Context context, List<OSTSimpleInfo> ostSimpleInfos)
    {
        super(context, ostSimpleInfos);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolderHelper helper = ViewHolderHelper.get(context, convertView, parent, R.layout.listview_item_new_ost, position);
        OSTSimpleInfo ostSimpleInfo = (OSTSimpleInfo) getItem(position);

        ImageView bannerIv = helper.getView(R.id.listview_item_imageview);
        TextView movieNameTv = helper.getView(R.id.listview_item_title_textview);
        TextView resInfoTv = helper.getView(R.id.listview_item_res_info_textview);
        TextView countryTv = helper.getView(R.id.listview_item_country_textview);
        TextView publishTimeTv = helper.getView(R.id.listview_item_publish_time_textview);

        ImageLoaderUtil.loadImage(context, bannerIv, ostSimpleInfo.getBanner());
        movieNameTv.setText(ostSimpleInfo.getMovie_name());  // 电影名称
        resInfoTv.setText(String.format("%s | %s", !TextUtils.isEmpty(ostSimpleInfo.getFile_type()) ? ostSimpleInfo.getFile_type() : "-",
                !TextUtils.isEmpty(ostSimpleInfo.getRes_size()) ? ostSimpleInfo.getRes_size() : "-"));  // 文件类型|资源大小
        countryTv.setText(String.format("地区/语言: %s", ostSimpleInfo.getCountry()));  // 地区/语言
        publishTimeTv.setText(String.format("发行时间: %s", ostSimpleInfo.getPublish_time()));  // 发行时间

        return helper.getConvertView();
    }
}
