package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.AddressBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.AddAddressReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.AddressPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.AddressView
import com.wuhanzihai.rbk.ruibeikang.utils.CityUtils
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomCityPicker
import kotlinx.android.synthetic.main.activity_add_address.*
import org.jetbrains.anko.toast
import java.net.URLEncoder

class AddAddressActivity : BaseMvpActivity<AddressPresenter>(), AddressView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onAddressResult() {
        toast("添加地址成功")
    }

    override fun onAddressListResult(result: MutableList<AddressBean>) {
    }

    override fun onDefAddressResult(result: AddressBean) {
    }

    override fun onAddressInfoResult(result: AddressBean) {
    }

    override fun onUpAddressResult() {
    }


    private var selectProvince = ""
    private var selectCity = ""
    private var selectArea = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)


        initView()

        initData()
    }

    private fun initView() {
        lAddress.onClick {
            CustomCityPicker(this, CustomCityPicker.ResultHandler { province, city, area ->
                selectProvince = province
                selectCity = city
                selectArea = area
                tvAddress.text = "${province}-${city}-${area}"

            }).show()
        }
        tvAddAddress.onClick {
            if (edName.text.isEmpty()||
                    selectProvince.isEmpty()||
                    selectCity.isEmpty()||
                    selectArea.isEmpty()||
                    edAddress.text.isEmpty()||
                    edPhone.text.isEmpty()
                    ){
                toast("请完善信息")
            }

            var is_default = 0
            if (ivDef.isSelected){
                is_default = 1
            }
            mPresenter.addAddress(AddAddressReq(URLEncoder.encode(edName.text.toString(), "UTF-8")
                    , URLEncoder.encode(selectProvince, "UTF-8")
                    , CityUtils.instance.getProvinceCode(this, selectProvince)
                    , URLEncoder.encode(selectCity, "UTF-8")
                    , CityUtils.instance.getCityCode(this, selectProvince, selectCity)
                    , URLEncoder.encode(selectArea, "UTF-8")
                    , CityUtils.instance.getAreaCode(this, selectProvince, selectCity, selectArea)
                    , URLEncoder.encode(edAddress.text.toString(), "UTF-8")
                    , edPhone.text.toString(),is_default))
        }

        ivDef.onClick {
            ivDef.isSelected = !ivDef.isSelected
        }
    }

    private fun initData() {


    }
}
