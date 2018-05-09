package com.sai628.moviejie.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sai628.moviejie.R;


public class LoadingMenu extends RelativeLayout implements OnClickListener
{
    private ImageView emptyPromptIv;
    private TextView emptyPromptTv;
    private TextView retryPromptTv;
    private ProgressBar progressBar;

    private OnRetryClickListener onRetryClickListener;


    public LoadingMenu(Context context)
    {
        super(context);
        init();
    }


    public LoadingMenu(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }


    private void init()
    {
        inflate(getContext(), R.layout.loading_menu, this);

        emptyPromptIv = findViewById(R.id.loading_menu_empty_prompt_imageview);
        emptyPromptIv.setVisibility(View.GONE);
        emptyPromptTv = findViewById(R.id.loading_menu_empty_prompt_textview);
        emptyPromptTv.setVisibility(View.GONE);

        retryPromptTv = findViewById(R.id.loading_menu_retry_prompt_textview);
        retryPromptTv.setVisibility(View.GONE);
        progressBar = findViewById(R.id.loading_menu_progressbar);
        progressBar.setVisibility(View.GONE);

        setOnClickListener(this);
    }


    public void setEmptyPromptText(CharSequence text)
    {
        emptyPromptTv.setText(text);
    }


    public void showLoading()
    {
        setVisibility(View.VISIBLE);

        emptyPromptIv.setVisibility(View.GONE);
        emptyPromptTv.setVisibility(View.GONE);
        retryPromptTv.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }


    public void showFail()
    {
        showFail(R.string.network_error_and_touch_retry);
    }


    public void showFail(int textResId)
    {
        if (getContext() != null)
        {
            showFail(getContext().getString(textResId));
        }
    }


    public void showFail(CharSequence text)
    {
        setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.GONE);
        emptyPromptIv.setVisibility(View.GONE);
        emptyPromptTv.setVisibility(View.GONE);

        retryPromptTv.setText(text);
        retryPromptTv.setVisibility(View.VISIBLE);
    }


    public void showEmpty()
    {
        if (getContext() != null)
        {
            showEmpty(getContext().getString(R.string.empty_data_prompt));
        }
    }


    public void showEmpty(CharSequence text)
    {
        setVisibility(View.VISIBLE);

        retryPromptTv.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        emptyPromptIv.setVisibility(View.VISIBLE);
        emptyPromptTv.setVisibility(View.VISIBLE);
        emptyPromptTv.setText(text);

        setOnClickListener(null);
    }


    public void dismiss()
    {
        if (getVisibility() != View.GONE)
        {
            setVisibility(View.GONE);
        }
    }


    public void setOnRetryClickListener(OnRetryClickListener onRetryClickListener)
    {
        this.onRetryClickListener = onRetryClickListener;
    }


    public OnRetryClickListener getOnRetryClickListener()
    {
        return onRetryClickListener;
    }


    public interface OnRetryClickListener
    {
        void onRetryClick(View v);
    }


    @Override
    public void onClick(View v)
    {
        if (progressBar.getVisibility() != View.VISIBLE)
        {
            showLoading();
            if (onRetryClickListener != null)
            {
                onRetryClickListener.onRetryClick(this);
            }
        }
    }
}
