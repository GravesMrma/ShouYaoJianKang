package com.wuhanzihai.rbk.ruibeikang.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wuhanzihai.rbk.ruibeikang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chuangxiangyue on 2018/1/31.
 */

public class ViewPagerIndicator implements ViewPager.OnPageChangeListener {

    private int size;
    private int img1 = R.drawable.sp_black_indicator, img2 = R.drawable.sp_gray_indicator;
    private int imgSize = 30;
    private List<ImageView> dotViewLists = new ArrayList<>();

    public ViewPagerIndicator(Context context, LinearLayout dotLayout, int size) {
        this.size = size;

        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            //为小圆点左右添加间距
            params.leftMargin = 5;
            params.rightMargin = 5;
            //手动给小圆点一个大小
//            params.height = imgSize;
//            params.width = imgSize;
            if (i == 0) {
                imageView.setImageResource(img1);
            } else {
                imageView.setImageResource(img2);
            }
            //为LinearLayout添加ImageView
            dotLayout.addView(imageView, params);
            dotViewLists.add(imageView);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < size; i++) {
            //选中的页面改变小圆点为选中状态，反之为未选中
            if ((position % size) == i) {
                dotViewLists.get(i).setImageResource(img1);
            } else {
                dotViewLists.get(i).setImageResource(img2);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}