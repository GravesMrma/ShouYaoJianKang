package com.wuhanzihai.rbk.ruibeikang.itemDiv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.wuhanzihai.rbk.ruibeikang.R;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

/**
 * Created by wzuriel on 2017/12/5.
 */

public class DividerItemLine extends Y_DividerItemDecoration {

    private Context context;

    public DividerItemLine(Context context) {
        super(context);
        this.context = context;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public Y_Divider getDivider(int itemPosition) {
        Y_Divider divider = new Y_DividerBuilder()
//                .setRightSideLine(true, ContextCompat.getColor(context, R.color.transparent),
//                        14, 0, 0)
//                .setLeftSideLine(true, ContextCompat.getColor(context, R.color.transparent),
//                        7, 0, 0)
//                .setTopSideLine(true, ContextCompat.getColor(context, R.color.transparent),
//                        14, 0, 0)
                .setBottomSideLine(true, ContextCompat.getColor(context, R.color.line),
                        1, 14, 14)
                .create();
        return divider;
    }
}
