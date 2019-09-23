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
import com.wuhanzihai.rbk.ruibeikang.utils.CityUtils;

import java.util.ArrayList;

/**
 * Created by
 */
public class CustomCityPicker {
    /**
     * 定义结果回调接口
     */
    public interface ResultHandler {
        void handle(String province, String city, String area);
    }

    private ResultHandler handler;
    private Context context;

    private Dialog datePickerDialog;
    private DatePickerView pvProvince, pvCity, pvArea;

    private ArrayList<String> provinceList = new ArrayList<>(), cityList = new ArrayList<>(), areaList = new ArrayList<>();
    private String selectProvince,selectCity,selectArea;
    private TextView tvCancel, tvSelect;


    public CustomCityPicker(Context context, ResultHandler resultHandler) {
        this.context = context;
        this.handler = resultHandler;
        initDialog();
        initView();
        initData();
    }

    private void initDialog() {
        if (datePickerDialog == null) {
            datePickerDialog = new Dialog(context, R.style.dialog_style);
            datePickerDialog.setCancelable(false);
            datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            datePickerDialog.setContentView(R.layout.item_city_picker);
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
        pvProvince = datePickerDialog.findViewById(R.id.pvProvince);
        pvCity = datePickerDialog.findViewById(R.id.pvCity);
        pvArea = datePickerDialog.findViewById(R.id.pvArea);
        tvCancel = datePickerDialog.findViewById(R.id.tvCancel);
        tvSelect = datePickerDialog.findViewById(R.id.tvSelect);
        pvProvince.setIsLoop(false);
        pvCity.setIsLoop(false);
        pvArea.setIsLoop(false);

        tvCancel.setOnClickListener(view -> datePickerDialog.dismiss());

        tvSelect.setOnClickListener(view -> {
            handler.handle(selectProvince, selectCity, selectArea);
            datePickerDialog.dismiss();
        });
    }

    private void initData(){
        provinceList.addAll(CityUtils.Companion.getInstance().getProvinceList(context));

        pvProvince.setData(provinceList);
        pvProvince.setSelected(0);
        selectProvince = provinceList.get(0);

        cityList.addAll(CityUtils.Companion.getInstance().getCityList(context,provinceList.get(0)));
        pvCity.setData(cityList);
        pvCity.setSelected(0);
        selectCity = cityList.get(0);

        areaList.addAll(CityUtils.Companion.getInstance().getAreaList(context,provinceList.get(0),cityList.get(0)));
        pvArea.setData(areaList);
        selectArea = areaList.get(0);
        executeScroll();
    }


    private void addListener() {
        pvProvince.setOnSelectListener(province -> {
            selectProvince = province;
            cityChange(selectProvince);
        });

        pvCity.setOnSelectListener(city -> {
            selectCity = city;
            areaChange(selectProvince,selectCity);
        });
        pvArea.setOnSelectListener(area->{
            selectArea = area;
        });
    }

    private void cityChange(String province) {
        cityList.clear();
        cityList.addAll(CityUtils.Companion.getInstance().getCityList(context,province));
        pvCity.setData(cityList);
        pvCity.setSelected(0);
        areaChange(province,cityList.get(0));
        executeAnimator(pvCity);
    }

    private void areaChange(String province,String city) {
        areaList.clear();
        areaList.addAll(CityUtils.Companion.getInstance().getAreaList(context,province,city));
        pvArea.setData(areaList);
        pvArea.setSelected(0);
        executeAnimator(pvArea);
        executeScroll();
    }

    private void executeAnimator(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start();
    }

    private void executeScroll() {
        pvProvince.setCanScroll(provinceList.size() > 1);
        pvCity.setCanScroll(cityList.size() > 1);
        pvArea.setCanScroll(areaList.size() > 1);
    }

    public void show() {
        addListener();
        setSelectedTime();
        datePickerDialog.show();
    }

    /**
     * 设置日期控件默认选中的时间
     */
    private void setSelectedTime() {
        pvProvince.setSelected(0);
        pvCity.setSelected(0);
        pvArea.setSelected(0);
        executeScroll();
    }
}