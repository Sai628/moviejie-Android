package com.sai628.moviejie.view;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;


/**
 * Text view that allows changing the letter spacing of the text.
 *
 * @author Pedro Barros (pedrobarros.dev at gmail.com)
 * @since May 7, 2013
 */
public class LetterSpacingTextView extends android.widget.TextView
{
    private float spacing = 0f;
    private CharSequence originalText = "";


    public LetterSpacingTextView(Context context)
    {
        super(context);
    }


    public LetterSpacingTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }


    public LetterSpacingTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }


    public float getSpacing()
    {
        return this.spacing;
    }


    public void setSpacing(float spacing)
    {
        this.spacing = spacing;
        applySpacing();
    }


    @Override
    public void setText(CharSequence text, BufferType type)
    {
        originalText = text;
        applySpacing();
    }


    @Override
    public CharSequence getText()
    {
        return originalText;
    }


    private void applySpacing()
    {
        if (originalText == null)
        {
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < originalText.length(); i++)
        {
            builder.append(originalText.charAt(i));
            if (i + 1 < originalText.length())
            {
                builder.append("\u00A0");
            }
        }
        SpannableString finalText = new SpannableString(builder.toString());
        if (builder.toString().length() > 1)
        {
            for (int i = 1; i < builder.toString().length(); i += 2)
            {
                finalText.setSpan(new ScaleXSpan((spacing + 1) / 10), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        super.setText(finalText, BufferType.SPANNABLE);
    }
}