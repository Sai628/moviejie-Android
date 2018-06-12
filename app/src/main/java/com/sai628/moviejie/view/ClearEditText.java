package com.sai628.moviejie.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.sai628.moviejie.R;


public class ClearEditText extends EditText implements TextWatcher
{
    private Drawable mClearDrawable;
    private boolean softInputEnable = true;


    public ClearEditText(Context context)
    {
        this(context, null);
    }


    public ClearEditText(Context context, AttributeSet attrs)
    {
        this(context, attrs, android.R.attr.editTextStyle);
    }


    public ClearEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }


    private void init()
    {
        mClearDrawable = getCompoundDrawables()[2];

        if (mClearDrawable == null)
        {
            mClearDrawable = getResources().getDrawable(R.drawable.icon_search_del_normal);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        addTextChangedListener(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (getCompoundDrawables()[2] != null)
        {
            if (event.getAction() == MotionEvent.ACTION_UP)
            {
                boolean touchable = event.getX() > (getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable)
                {
                    this.setText("");
                }
            }
        }

        if (!softInputEnable)
        {
            this.setInputType(InputType.TYPE_NULL);
        }

        return super.onTouchEvent(event);
    }


    protected void setClearIconVisible(boolean visible)
    {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after)
    {
        setClearIconVisible(s.length() > 0);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
    }


    @Override
    public void afterTextChanged(Editable s)
    {
    }


    public void setSoftInputEnable(boolean enable)
    {
        this.softInputEnable = enable;
    }
}
