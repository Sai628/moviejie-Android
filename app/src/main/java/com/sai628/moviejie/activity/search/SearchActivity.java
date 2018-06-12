package com.sai628.moviejie.activity.search;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.sai628.moviejie.R;
import com.sai628.moviejie.activity.base.BaseActivity;
import com.sai628.moviejie.config.AppUserData;
import com.sai628.moviejie.utils.ImeUtil;
import com.sai628.moviejie.view.ClearEditText;
import com.sai628.moviejie.view.CustomDialog;
import com.sai628.moviejie.view.TagGroup;

import java.util.ArrayList;


/**
 * @author Sai
 * @ClassName: SearchActivity
 * @Description: "搜索"视图
 * @date 08/06/2018 15:16
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener
{
    private ClearEditText searchEt;
    private TextView cancelTv;

    private View historyLayout;
    private ImageView clearUpIv;
    private TagGroup historyTagGroup;

    private ArrayList<String> searchHistories;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);

        initData();
        initView();
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        updateUI();
        searchEt.setText(null);
        ImeUtil.setKeyboardFocus(searchEt);
    }


    private void initData()
    {
        searchHistories = AppUserData.getSearchHistories(this);
    }


    private void initView()
    {
        searchEt = findViewById(R.id.search_view_search_edittext);
        searchEt.setOnEditorActionListener(searchOnEditorActionListener);
        cancelTv = findViewById(R.id.search_view_cancel_textview);
        cancelTv.setOnClickListener(this);

        historyLayout = findViewById(R.id.search_view_history_layout);
        clearUpIv = findViewById(R.id.search_view_clearup_imageview);
        clearUpIv.setOnClickListener(this);
        historyTagGroup = findViewById(R.id.search_view_history_taggroup);
        historyTagGroup.setOnTagClickListener(onTagClickListener);
    }


    private void updateUI()
    {
        String[] tags = searchHistories.toArray(new String[]{});
        historyTagGroup.setTags(tags);
        historyLayout.setVisibility(tags.length > 0 ? View.VISIBLE : View.GONE);
    }


    private void doSearch(String keyword)
    {
        ImeUtil.hideIme(this);

        searchHistories.remove(keyword);
        searchHistories.add(0, keyword);
        AppUserData.saveSearchHistories(this, searchHistories);

        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra(SearchResultActivity.EXTRA_KEYWORD, keyword);
        startActivity(intent);
    }


    private void clearAllSearchHistory()
    {
        ImeUtil.hideIme(this);
        CustomDialog.showDialog(this, "提示", "是否清空所有搜索历史?", "取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        }, "清空", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();

                searchHistories.clear();
                AppUserData.saveSearchHistories(getThis(), searchHistories);
                updateUI();
            }
        });
    }


    private TextView.OnEditorActionListener searchOnEditorActionListener = new TextView.OnEditorActionListener()
    {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
        {
            String keyword = v.getText().toString();
            if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(keyword))
            {
                doSearch(keyword);
                return true;
            }

            return false;
        }
    };


    private TagGroup.OnTagClickListener onTagClickListener = new TagGroup.OnTagClickListener()
    {
        @Override
        public void onTagClick(String tag)
        {
            doSearch(tag);
        }
    };


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.search_view_cancel_textview:
                finish();
                break;

            case R.id.search_view_clearup_imageview:
                clearAllSearchHistory();
                break;
        }
    }
}
