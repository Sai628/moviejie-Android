package com.sai628.moviejie.activity.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.sai628.moviejie.R;
import com.sai628.moviejie.activity.base.BaseFragmentActivity;
import com.sai628.moviejie.utils.ContextUtil;

import java.util.ArrayList;


public class MainActivity extends BaseFragmentActivity implements OnClickListener
{
    private static final int TAB_INDEX_NEWEST = 0;
    private static final int TAB_INDEX_MOVIE = 1;
    private static final int TAB_INDEX_TV = 2;
    private static final int TAB_INDEX_OST = 3;

    private static final String TAG_NEWEST = "newest";
    private static final String TAG_MOVIE = "movie";
    private static final String TAG_TV = "tv";
    private static final String TAG_OST = "ost";

    private int[] titleResIDs = {
            R.string.renewal,
            R.string.movie,
            R.string.tv_series,
            R.string.ost
    };

    private TextView titleTv;
    private ArrayList<View> tabViews;

    private NewestInfoFragment newestInfoFragment;
    private NewMovieFragment newMovieFragment;
    private NewTvFragment newTvFragment;
    private NewOSTFragment newOSTFragment;

    private int currentTabIndex = -1;


    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.main_view);

        initView();
        tabViews.get(TAB_INDEX_NEWEST).performClick();
    }


    private void initView()
    {
        titleTv = findViewById(R.id.main_view_title_textview);

        tabViews = new ArrayList<>(titleResIDs.length);
        tabViews.add(findViewById(R.id.content_view_main_bottom_layout_newest_ll));
        tabViews.add(findViewById(R.id.content_view_main_bottom_layout_movie_ll));
        tabViews.add(findViewById(R.id.content_view_main_bottom_layout_tv_ll));
        tabViews.add(findViewById(R.id.content_view_main_bottom_layout_ost_ll));

        for (int i = 0; i < tabViews.size(); i++)
        {
            View tabView = tabViews.get(i);
            tabView.setOnClickListener(tabItemOnClickListener);
            tabView.setTag(i);
        }
    }


    private void resetTabItem()
    {
        for (View tabView : tabViews)
        {
            tabView.setSelected(false);
        }
    }


    private void changeToTabItem(int index)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideAllFragment(fm, transaction);

        switch (index)
        {
            case TAB_INDEX_NEWEST:
                if (newestInfoFragment == null)
                {
                    newestInfoFragment = new NewestInfoFragment();
                    transaction.add(R.id.main_view_fragment_container, newestInfoFragment, TAG_NEWEST);
                }
                transaction.show(newestInfoFragment);
                break;

            case TAB_INDEX_MOVIE:
                if (newMovieFragment == null)
                {
                    newMovieFragment = new NewMovieFragment();
                    transaction.add(R.id.main_view_fragment_container, newMovieFragment, TAG_MOVIE);
                }
                transaction.show(newMovieFragment);
                break;

            case TAB_INDEX_TV:
                if (newTvFragment == null)
                {
                    newTvFragment = new NewTvFragment();
                    transaction.add(R.id.main_view_fragment_container, newTvFragment, TAG_TV);
                }
                transaction.show(newTvFragment);
                break;


            case TAB_INDEX_OST:
                if (newOSTFragment == null)
                {
                    newOSTFragment = new NewOSTFragment();
                    transaction.add(R.id.main_view_fragment_container, newOSTFragment, TAG_OST);
                }
                transaction.show(newOSTFragment);
                break;
        }

        transaction.commitAllowingStateLoss();
    }


    private void hideAllFragment(FragmentManager fm, FragmentTransaction transaction)
    {
        if (newestInfoFragment != null && fm.findFragmentByTag(TAG_NEWEST) != null)
        {
            transaction.hide(newestInfoFragment);
        }
        if (newMovieFragment != null && fm.findFragmentByTag(TAG_MOVIE) != null)
        {
            transaction.hide(newMovieFragment);
        }
        if (newTvFragment != null && fm.findFragmentByTag(TAG_TV) != null)
        {
            transaction.hide(newTvFragment);
        }
        if (newOSTFragment != null && fm.findFragmentByTag(TAG_OST) != null)
        {
            transaction.hide(newOSTFragment);
        }
    }


    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            ContextUtil.goToHomeCategory(getThis());
            return true;
        }

        return super.dispatchKeyEvent(event);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //TODO
            default:
                break;
        }
    }


    private OnClickListener tabItemOnClickListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int index = (int) v.getTag();
            if (currentTabIndex != index)
            {
                currentTabIndex = index;

                resetTabItem();
                changeToTabItem(index);
                titleTv.setText(titleResIDs[index]);
                tabViews.get(index).setSelected(true);
            }
        }
    };
}
