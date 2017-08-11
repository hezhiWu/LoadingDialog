package com.wayto.loadingdialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * ProgressDialog
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/8/4 10:59
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class WaytoProgressDialog extends ProgressDialog {

    private Context context;

    private TextView tipTextView;
    private ImageView tipImageView;

    private AnimationDrawable animationDrawable;

    private CharSequence message;
    private int mIcon;
    private boolean finish;

    public WaytoProgressDialog(Context context) {
        super(context, 0);
    }

    public WaytoProgressDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x980:

                    dismiss();

                    if (finish
                            && context != null
                            && context instanceof Activity) {

                        ((Activity) context).finish();

                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wayto_progress_dialog);
        setProgressStyle(STYLE_HORIZONTAL);

        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.0f;
        int width = (int) (window.getWindowManager().getDefaultDisplay().getWidth() * 0.3f);
        window.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);

        tipImageView = (ImageView) findViewById(R.id.progress_tip_ImageView);
        tipTextView = (TextView) findViewById(R.id.progress_tip_textView);

        tipImageView.setImageResource(R.drawable.wayto_progress_animation);
        animationDrawable = (AnimationDrawable) tipImageView.getDrawable();
        animationDrawable.start();

        if (!TextUtils.isEmpty(message)) {
            tipTextView.setText(message);
            tipTextView.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 显示对话框
     * <p>
     * author: hezhiWu
     * created at 2017/8/4 15:25
     *
     * @param context
     */
    public static WaytoProgressDialog show(Context context) {
        return show(context, null);
    }

    /**
     * author: hezhiWu
     * created at 2017/8/4 15:43
     */
    public static WaytoProgressDialog show(Context context, boolean cancelable) {
        return show(context, null, cancelable);
    }

    /**
     * 显示对话框
     * <p>
     * author: hezhiWu
     * created at 2017/8/4 15:28
     */
    public static WaytoProgressDialog show(Context content, int msgId) {
        return show(content, content.getString(msgId));
    }

    /**
     * 显示对话框
     * <p>
     * author: hezhiWu
     * created at 2017/8/4 15:44
     */
    public static WaytoProgressDialog show(Context context, int msgId, boolean cancelable) {
        return show(context, context.getString(msgId), cancelable);
    }

    /**
     * 显示对话框
     * <p>
     * author: hezhiWu
     * created at 2017/8/4 14:44
     *
     * @param context
     * @param msg
     */
    public static WaytoProgressDialog show(Context context, CharSequence msg) {
        return show(context, msg, false);
    }

    /**
     * 显示对话框
     * <p>
     * author: hezhiWu
     * created at 2017/8/4 15:40
     *
     * @param context
     * @param msg
     * @param cancelable Sets whether this dialog is cancelable with the
     */
    public static WaytoProgressDialog show(Context context, CharSequence msg, boolean cancelable) {
        WaytoProgressDialog progressDialog = new WaytoProgressDialog(context, R.style.CommProgressDialog);
        progressDialog.setTitelText(msg);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
        return progressDialog;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    /**
     * 设置Message
     * <p>
     * author: hezhiWu
     * created at 2017/8/4 15:24
     */
    private void setTitelText(CharSequence msg) {
        this.message = msg;
    }

    /**
     * 加完完成，设置提示语
     * <p>
     * author: hezhiWu
     * created at 2017/8/4 15:24
     *
     * @param msgId 提示消息
     */
    public void setPromptMessage(int msgId) {
        setPromptMessage(getContext().getString(msgId));
    }

    /**
     * 加完完成，设置提示语
     * <p>
     * author: hezhiWu
     * created at 2017/8/4 15:24
     *
     * @param msg 提示消息
     */
    public void setPromptMessage(String msg) {
        setPromptMessage(msg, false);
    }

    /**
     * 加完完成，设置提示语
     * <p>
     * author: hezhiWu
     * created at 2017/8/11 10:27
     */
    public void setPromptMessage(int icon, String msg) {
        setPromptMessage(icon, msg, false);
    }

    /**
     * 加完完成，设置提示语
     * <p>
     * author: hezhiWu
     * created at 2017/8/11 10:28
     */
    public void setPromptMessage(int icon, int msgId) {
        setPromptMessage(icon, getContext().getString(msgId));
    }

    /**
     * 加完完成，设置提示语
     * <p>
     * author: hezhiWu
     * created at 2017/8/4 15:32
     *
     * @param msgId  提示消息
     * @param finish 设置dimiss对话框，是否finish当前Activity; true-finish,false-not finish
     */
    public void setPromptMessage(int msgId, boolean finish) {
        setPromptMessage(getContext().getString(msgId), finish);
    }


    /**
     * 加完完成，设置提示语
     * <p>
     * author: hezhiWu
     * created at 2017/8/4 15:32
     *
     * @param msg    提示消息
     * @param finish 设置dimiss对话框，是否finish当前Activity; true-finish,false-not finish
     */
    public void setPromptMessage(String msg, boolean finish) {
        setPromptMessage(0, msg, finish);
    }

    /**
     * 加完完成，设置提示语
     * <p>
     * author: hezhiWu
     * created at 2017/8/4 16:09
     *
     * @param icon   设置提示图标
     * @param msgId  设置提示信息
     * @param finish 设置dimiss对话框，是否finish当前Activity; true-finish,false-not finish
     */
    public void setPromptMessage(int icon, int msgId, boolean finish) {
        setPromptMessage(icon, getContext().getString(msgId), finish);
    }

    /**
     * 加完完成，设置提示语
     * <p>
     * author: hezhiWu
     * created at 2017/8/4 16:09
     *
     * @param icon   设置提示图标
     * @param msg    设置提示信息
     * @param finish 设置dimiss对话框，是否finish当前Activity; true-finish,false-not finish
     */
    public void setPromptMessage(int icon, String msg, boolean finish) {
        if (animationDrawable != null)
            animationDrawable.stop();

        this.finish = finish;

        if (icon <= 0) {
            tipImageView.setVisibility(View.GONE);
        } else {
            tipImageView.setImageResource(icon);

            tipImageView.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(msg)) {
            tipTextView.setVisibility(View.GONE);
        } else {
            tipTextView.setText(msg);

            tipTextView.setVisibility(View.VISIBLE);
        }

        handler.sendEmptyMessageDelayed(0x980, 2000);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
