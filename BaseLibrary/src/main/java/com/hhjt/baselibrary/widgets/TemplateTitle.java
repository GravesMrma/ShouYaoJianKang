package com.hhjt.baselibrary.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhjt.baselibrary.R;
import com.hhjt.baselibrary.common.AppCommonExt;


/**
 * 标题控件
 */
public class TemplateTitle extends RelativeLayout {

    private String titleText;
    private int titleTextColor;
    private int titleRightImg;
    private boolean canBack;
    private String backText;
    private String moreText;
    private int moreImg;
    private TextView tvMore;
    private int tvMoreRightImg;
    private int backgroundColor;
    private boolean isShare;
    private boolean isWhiteBack;

    private Context context;

    public TemplateTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.title, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TemplateTitle, 0, 0);
        try {
            titleText = ta.getString(R.styleable.TemplateTitle_titleText);
            titleTextColor = ta.getColor(R.styleable.TemplateTitle_titleTextColor, Color.BLACK);
            titleRightImg = ta.getResourceId(R.styleable.TemplateTitle_titleRightImg, 0);
            canBack = ta.getBoolean(R.styleable.TemplateTitle_canBack, false);
            isShare = ta.getBoolean(R.styleable.TemplateTitle_isShare, false);
            isWhiteBack = ta.getBoolean(R.styleable.TemplateTitle_isWhiteBack, false);
            backText = ta.getString(R.styleable.TemplateTitle_backText);
            moreImg = ta.getResourceId(R.styleable.TemplateTitle_moreImg, 0);
            moreText = ta.getString(R.styleable.TemplateTitle_moreText);
            tvMoreRightImg = ta.getResourceId(R.styleable.TemplateTitle_moreTextRightImg, 0);
            backgroundColor = ta.getColor(R.styleable.TemplateTitle_titleBackground,
                    ContextCompat.getColor(context, R.color.white));
            setUpView();
        } finally {
            ta.recycle();
        }

    }

    private void setUpView() {
        TextView tvTitle = findViewById(R.id.title);
        tvTitle.setText(titleText);
        tvTitle.setTextColor(titleTextColor);
        if (titleRightImg != 0) {
            Drawable drawable = context.getResources().getDrawable(titleRightImg);
            // / 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvTitle.setCompoundDrawables(null, null, drawable, null);
        }
        LinearLayout backBtn = findViewById(R.id.title_back);
        backBtn.setVisibility(canBack ? VISIBLE : INVISIBLE);
        if (canBack) {
            TextView tvBack = findViewById(R.id.txt_back);
            tvBack.setText(backText);
            backBtn.setOnClickListener(v -> ((Activity) getContext()).finish());
        }
        if (isShare) {
            ImageView moreImgView = findViewById(R.id.img_more);
            moreImgView.setImageResource(R.drawable.ic_share);
        }
        if (isWhiteBack) {
            ImageView moreImgView = findViewById(R.id.img_back);
            moreImgView.setImageResource(R.drawable.ic_return_white);
        }
        if (moreImg != 0) {
            ImageView moreImgView = findViewById(R.id.img_more);
            moreImgView.setImageDrawable(getContext().getResources().getDrawable(moreImg));
        }
        tvMore = findViewById(R.id.txt_more);
        tvMore.setText(moreText);
        RelativeLayout view = findViewById(R.id.title_container);
        view.setBackgroundColor(backgroundColor);
        if (tvMoreRightImg != 0) {
            Drawable drawable = context.getResources().getDrawable(tvMoreRightImg);
            // / 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvMore.setCompoundDrawables(null, null, drawable, null);
        }
    }


    /**
     * 标题控件
     *
     * @param titleText 设置标题文案
     */
    public void setTitleText(String titleText) {
        this.titleText = titleText;
        TextView tvTitle = findViewById(R.id.title);
        tvTitle.setText(titleText);
    }

    /**
     * 设置更多按钮事件
     *
     * @param listener 事件监听
     */
    public void setTitleAction(OnClickListener listener) {
        TextView tvTitle = findViewById(R.id.title);
        tvTitle.setOnClickListener(listener);
    }

    /**
     * 设置更多按钮事件
     *
     * @param listener 事件监听
     */
    public void setShareAction(OnClickListener listener) {
        ImageView tvTitle = findViewById(R.id.img_more);
        tvTitle.setOnClickListener(listener);
    }

    /**
     * 标题更多按钮
     *
     * @param img 设置更多按钮
     */
    public void setMoreImg(int img) {
        moreImg = img;
        ImageView moreImgView = findViewById(R.id.img_more);
        moreImgView.setImageDrawable(getContext().getResources().getDrawable(moreImg));
    }


    /**
     * 设置更多按钮事件
     *
     * @param listener 事件监听
     */
    public void setMoreImgAction(OnClickListener listener) {
        ImageView moreImgView = findViewById(R.id.img_more);
        moreImgView.setOnClickListener(listener);
    }


    /**
     * 设置更多按钮事件
     *
     * @param listener 事件监听
     */
    public void setMoreTextAction(OnClickListener listener) {
        tvMore.setOnClickListener(listener);
    }


    /**
     * 设置更多文字内容
     *
     * @param text 更多文本
     */
    public void setMoreTextContext(String text) {
        tvMore.setText(text);
    }


    /**
     * 设置返回按钮事件
     *
     * @param listener 事件监听
     */
    public void setBackListener(OnClickListener listener) {
        if (canBack) {
            LinearLayout backBtn = findViewById(R.id.title_back);
            backBtn.setOnClickListener(listener);
        }
    }

    public String getMoreText() {
        return tvMore.getText().toString();
    }

    public void setAppBackground(int color) {
        RelativeLayout view = findViewById(R.id.title_container);
        view.setBackgroundColor(color);
    }
}
