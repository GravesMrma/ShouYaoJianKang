package com.wuhanzihai.rbk.ruibeikang.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.EditText;

public class CancelSlideWebView extends WebView {
    public EditText mFocusDistraction;
    public Context mContext;

    public CancelSlideWebView(Context context) {
        super(context);
        init(context);
    }

    public CancelSlideWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CancelSlideWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public void init(Context context) {
        if (isInEditMode()) return;
        mContext = context;
        mFocusDistraction = new EditText(context);
        mFocusDistraction.setBackgroundResource(android.R.color.transparent);
        this.addView(mFocusDistraction);
        mFocusDistraction.getLayoutParams().width = 1;
        mFocusDistraction.getLayoutParams().height = 1;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        invalidate();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                int scrollRangeX, int scrollRangeY, int maxOverScrollX,
                                int maxOverScrollY, boolean isTouchEvent) {
        return false;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(0, 0);
    }

}
