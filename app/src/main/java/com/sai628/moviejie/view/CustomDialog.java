package com.sai628.moviejie.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sai628.moviejie.R;


public class CustomDialog extends Dialog
{
    public static void showDialog(Context context, int titleID, int messageID, int negativeButtonTextID, DialogInterface.OnClickListener negativeListener, int positiveButtonTextID, DialogInterface.OnClickListener positiveListener)
    {
        showDialog(context, context.getString(titleID), context.getString(messageID), context.getString(negativeButtonTextID), negativeListener, context.getString(positiveButtonTextID), positiveListener);
    }


    public static void showDialog(Context context, int titleID, String message, int negativeButtonTextID, DialogInterface.OnClickListener negativeListener, int positiveButtonTextID, DialogInterface.OnClickListener positiveListener)
    {
        showDialog(context, context.getString(titleID), message, context.getString(negativeButtonTextID), negativeListener, context.getString(positiveButtonTextID), positiveListener);
    }


    public static void showDialog(Context context, int titleID, int messageID, String negativeButtonText, DialogInterface.OnClickListener negativeListener, int positiveButtonTextID, DialogInterface.OnClickListener positiveListener)
    {
        showDialog(context, context.getString(titleID), context.getString(messageID), negativeButtonText, negativeListener, context.getString(positiveButtonTextID), positiveListener);
    }


    public static void showDialog(Context context, int titleID, int messageID, String negativeButtonText, DialogInterface.OnClickListener negativeListener, String positiveButtonText, DialogInterface.OnClickListener positiveListener)
    {
        showDialog(context, context.getString(titleID), context.getString(messageID), negativeButtonText, negativeListener, positiveButtonText, positiveListener);
    }


    public static void showDialog(Context context, String title, String message, int negativeButtonTextID, DialogInterface.OnClickListener negativeListener, int positiveButtonTextID, DialogInterface.OnClickListener positiveListener)
    {
        showDialog(context, title, message, context.getString(negativeButtonTextID), negativeListener, context.getString(positiveButtonTextID), positiveListener);
    }


    public static void showDialog(Context context, String title, String message, String negativeButtonText, DialogInterface.OnClickListener negativeListener, int positiveButtonTextID, DialogInterface.OnClickListener positiveListener)
    {
        showDialog(context, title, message, negativeButtonText, negativeListener, context.getString(positiveButtonTextID), positiveListener);
    }


    public static void showDialog(Context context, String title, String message, String negativeButtonText, DialogInterface.OnClickListener negativeListener, String positiveButtonText, DialogInterface.OnClickListener positiveListener)
    {
        createDialog(context, title, message, negativeButtonText, negativeListener, positiveButtonText, positiveListener).show();
    }


    public static void showSimpleAlertDialog(Context context, String title, String message, String buttonText)
    {
        showDialog(context, title, message, buttonText, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        }, null, null);
    }


    public static CustomDialog createDialog(Context context, String title, String message, String negativeButtonText, DialogInterface.OnClickListener negativeListener, String positiveButtonText, DialogInterface.OnClickListener positiveListener)
    {
        Builder builder = new Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(negativeButtonText, negativeListener);
        builder.setPositiveButton(positiveButtonText, positiveListener);

        return builder.create();
    }


    public CustomDialog(Context context, int theme)
    {
        super(context, theme);
    }


    public CustomDialog(Context context)
    {
        super(context);
    }


    public static class Builder
    {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private LinearLayout bottomLl;
        private TextView titleTv;
        private TextView messageTv;
        private int titleGravity = Gravity.LEFT; // 标题文字默认为靠左显示
        private int messageGravity = Gravity.LEFT; // 内容文字默认为居中显示
        private int contentViewGravite = Gravity.CENTER; // 自定义内部布局居中显示
        private Button positiveBtn;
        private Button negativeBtn;

        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;


        public Builder(Context context)
        {
            this.context = context;
        }


        /**
         * Set the Dialog message from resource
         *
         * @param messageID
         * @return
         */
        public Builder setMessage(int messageID)
        {
            this.message = context.getResources().getString(messageID);
            return this;
        }


        /**
         * Set the Dialog message from String
         *
         * @param message
         * @return
         */
        public Builder setMessage(String message)
        {
            this.message = message;
            return this;
        }


        /**
         * Set the Dialog message from String
         *
         * @param message
         * @param messageGravity
         * @return
         */
        public Builder setMessage(String message, int messageGravity)
        {
            this.message = message;
            this.messageGravity = messageGravity;
            return this;
        }


        /**
         * Set the Dialog message from resource
         *
         * @param messageID
         * @param messageGravity
         * @return
         */
        public Builder setMessage(int messageID, int messageGravity)
        {
            this.message = context.getResources().getString(messageID);
            this.messageGravity = messageGravity;
            return this;
        }


        /**
         * Set the Dialog title from resource
         *
         * @param titleID
         * @return
         */
        public Builder setTitle(int titleID)
        {
            this.title = context.getResources().getString(titleID);
            return this;
        }


        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title)
        {
            this.title = title;
            return this;
        }


        /**
         * Set the Dialog title from resource
         *
         * @param titleID
         * @param titleGravity
         * @return
         */
        public Builder setTitle(int titleID, int titleGravity)
        {
            this.title = context.getResources().getString(titleID);
            this.titleGravity = titleGravity;
            return this;
        }


        /**
         * Set the Dialog title from String
         *
         * @param title
         * @param titleGravity
         * @return
         */
        public Builder setTitle(String title, int titleGravity)
        {
            this.title = title;
            this.titleGravity = titleGravity;
            return this;
        }


        /**
         * Set a custom content view for the Dialog.
         * If a message is set, the contentView is not
         * added to the Dialog...
         *
         * @param view
         * @return
         */
        public Builder setContentView(View view, int contentViewGravity)
        {
            this.contentView = view;
            this.contentViewGravite = contentViewGravity;
            return this;
        }


        /**
         * Set a custom content view for the Dialog.
         * If a message is set, the contentView is not
         * added to the Dialog...
         *
         * @param view
         * @return
         */
        public Builder setContentView(View view)
        {
            this.contentView = view;
            return this;
        }


        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonTextID
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonTextID, DialogInterface.OnClickListener listener)
        {
            this.positiveButtonText = context.getResources().getString(positiveButtonTextID);
            this.positiveButtonClickListener = listener;
            return this;
        }


        /**
         * Set the positive button text and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener)
        {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }


        /**
         * Set the negative button resource and it's listener
         *
         * @param negativeButtonTextID
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonTextID, DialogInterface.OnClickListener listener)
        {
            this.negativeButtonText = context.getResources().getString(negativeButtonTextID);
            this.negativeButtonClickListener = listener;
            return this;
        }


        /**
         * Set the negative button text and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener)
        {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }


        /**
         * Create the custom dialog
         */
        public CustomDialog create()
        {
            final CustomDialog dialog = new CustomDialog(context, R.style.my_dialog_style_default);
            View layout = LayoutInflater.from(context).inflate(R.layout.custom_dialog_view, null);

            titleTv = (TextView) layout.findViewById(R.id.title);
            messageTv = (TextView) layout.findViewById(R.id.message);
            bottomLl = (LinearLayout) layout.findViewById(R.id.bottom);
            positiveBtn = (Button) layout.findViewById(R.id.positiveButton);
            negativeBtn = (Button) layout.findViewById(R.id.negativeButton);

            titleTv.setText(title);
            titleTv.setGravity(titleGravity);

            if (this.positiveButtonText != null)
            {
                positiveBtn.setText(this.positiveButtonText);
                if (positiveButtonClickListener != null)
                {
                    positiveBtn.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            }
            else
            {
                positiveBtn.setVisibility(View.GONE);
            }

            if (this.negativeButtonText != null)
            {
                negativeBtn.setText(negativeButtonText);
                if (negativeButtonClickListener != null)
                {
                    negativeBtn.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            }
            else
            {
                negativeBtn.setVisibility(View.GONE);
            }

            if (positiveBtn.getVisibility() == View.GONE && negativeBtn.getVisibility() == View.GONE)
            {
                bottomLl.setVisibility(View.GONE);
            }

            if (message != null)
            {
                messageTv.setText(message);
                messageTv.setGravity(messageGravity);
            }
            else if (contentView != null)
            {
                LinearLayout contentLl = (LinearLayout) layout.findViewById(R.id.content);
                contentLl.removeAllViews();
                contentLl.setGravity(contentViewGravite);
                contentLl.addView(contentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            }

            dialog.setContentView(layout);

            return dialog;
        }
    }
}
