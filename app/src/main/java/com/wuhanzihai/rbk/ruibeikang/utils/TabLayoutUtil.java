package com.wuhanzihai.rbk.ruibeikang.utils;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

public class TabLayoutUtil {

    public static void setTabLine(Context context, TabLayout tabLayout, int size) {
        try {
            //拿到tabLayout的mTabStrip属性
            LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);

                //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                mTextViewField.setAccessible(true);

                TextView mTextView = (TextView) mTextViewField.get(tabView);

                tabView.setPadding(0, 0, 0, 0);

                //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                int width = 0;
                width = mTextView.getWidth();
                if (width == 0) {
                    mTextView.measure(0, 0);
                    width = mTextView.getMeasuredWidth();
                }

                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                params.width = width;
                if (size == 0) {
                    // 设置固定宽度
                    params.leftMargin = DisplayUtil.dip2px(context, 20);
                    params.rightMargin = DisplayUtil.dip2px(context, 20);
                } else {
                    params.leftMargin = (DisplayUtil.getScreenWidth(context) / size - width) / 4;
                    params.rightMargin = (DisplayUtil.getScreenWidth(context) / size - width) / 4;
                }

                tabView.setLayoutParams(params);
                tabView.invalidate();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
