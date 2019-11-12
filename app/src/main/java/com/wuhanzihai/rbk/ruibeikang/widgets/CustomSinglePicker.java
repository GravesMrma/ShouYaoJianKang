package com.wuhanzihai.rbk.ruibeikang.widgets;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wuhanzihai.rbk.ruibeikang.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by liuwan on 2016/9/28.
 */
public class CustomSinglePicker {

    /**
     * 定义结果回调接口
     */
    public interface ResultHandler {
        void handle(String time);
    }

    private ResultHandler handler;
    private Context context;

    private Dialog datePickerDialog;
    private DatePickerView data_pv;

    private List<String> list_data = new ArrayList<>();
    private String selectData;
    private TextView tv_cancle, tv_select;

    public CustomSinglePicker(Context context, ResultHandler resultHandler) {
        this.context = context;
        this.handler = resultHandler;
        initDialog();
        initView();
    }

    private void initDialog() {
        if (datePickerDialog == null) {
            datePickerDialog = new Dialog(context, R.style.dialog_style);
            datePickerDialog.setCancelable(false);
            datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            datePickerDialog.setContentView(R.layout.item_single_picker);
            Window window = datePickerDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = dm.widthPixels;
            window.setAttributes(lp);
        }
    }

    private void initView() {
        data_pv = datePickerDialog.findViewById(R.id.data_pv);
        tv_cancle = datePickerDialog.findViewById(R.id.tv_cancel);
        tv_select = datePickerDialog.findViewById(R.id.tv_select);
        tv_cancle.setOnClickListener(view -> datePickerDialog.dismiss());
        tv_select.setOnClickListener(view -> {
            handler.handle(selectData);
            datePickerDialog.dismiss();
        });
    }

    private void addListener() {
        data_pv.setOnSelectListener(data -> selectData = data);
    }

    private void executeScroll() {
        data_pv.setCanScroll(list_data.size() > 1);
    }

    public CustomSinglePicker setData(List<String> data) {
        list_data = data;
        data_pv.setData(list_data);
        executeScroll();
        addListener();
        selectData = data.get(0);
        return this;
    }

    public CustomSinglePicker setIsLoop(boolean isLoop) {
        data_pv.setIsLoop(isLoop); // 设置日期控件是否可以循环滚动

        return this;
    }

    public void show() {
        datePickerDialog.show();
    }

    /**
     * 设置日期控件默认选中的数据
     */
    public CustomSinglePicker setSelected(int index) {
        data_pv.setSelected(index);
        selectData = list_data.get(index);
        executeAnimator(data_pv);
        return this;
    }

    private void executeAnimator(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start();
    }
}
