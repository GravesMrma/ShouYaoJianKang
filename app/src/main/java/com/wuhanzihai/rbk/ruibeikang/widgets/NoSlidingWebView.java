package com.wuhanzihai.rbk.ruibeikang.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class NoSlidingWebView extends WebView {

    public NoSlidingWebView(Context context) {
        super(context);
    }

    public NoSlidingWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoSlidingWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //重写onScrollChanged 方法
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        scrollTo(l,0);
    }
}