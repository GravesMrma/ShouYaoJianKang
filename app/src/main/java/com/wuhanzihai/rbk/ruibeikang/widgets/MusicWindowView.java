package com.wuhanzihai.rbk.ruibeikang.widgets;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import com.wuhanzihai.rbk.ruibeikang.R;


/**
 * Created by mq on 2018/8/26 下午5:55
 * mqcoder90@gmail.com
 */

public class MusicWindowView extends android.support.v7.widget.AppCompatTextView {

    public MusicWindowView(Context context) {
        this(context, null);
    }

    public MusicWindowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public MusicWindowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackgroundResource(R.drawable.sp_white_5);
        setGravity(Gravity.CENTER);
        setTextColor(Color.BLACK);
        setText("悬浮弹窗");
    }

    /**
     * 设置宽和高的属性
     *
     * @param height
     * @param width
     */
    public void setHW(int height, int width) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
        setLayoutParams(params);
    }
}
