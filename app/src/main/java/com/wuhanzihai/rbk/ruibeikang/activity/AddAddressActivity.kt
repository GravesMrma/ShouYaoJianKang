package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.AddressBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.AddAddressReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.UpdateAddressReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.AddressPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.AddressView
import com.wuhanzihai.rbk.ruibeikang.utils.CityUtils
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomCityPicker
import kotlinx.android.synthetic.main.activity_add_address.*
import org.jetbrains.anko.act
import org.jetbrains.anko.toast
import java.net.URLEncoder

class AddAddressActivity : BaseMvpActivity<AddressPresenter>(), AddressView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onAddressResult() {
        setResult(4444)
        finish()
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
    private var addID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

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
            if (edName.text.isEmpty() ||
                    selectProvince.isEmpty() ||
                    selectCity.isEmpty() ||
                    selectArea.isEmpty() ||
                    edAddress.text.isEmpty() ||
                    edPhone.text.isEmpty()
            ) {
                toast("请完善信息")
                return@onClick
            }

            var is_default = 0
            if (ivDef.isSelected) {
                is_default = 1
            }
            if (intent.getBooleanExtra("update", false)) {
                mPresenter.upAddress(UpdateAddressReq(addID,edName.text.toString()
                        , selectProvince
                        , CityUtils.instance.getProvinceCode(this, selectProvince)
                        , selectCity
                        , CityUtils.instance.getCityCode(this, selectProvince, selectCity)
                        , selectArea
                        , CityUtils.instance.getAreaCode(this, selectProvince, selectCity, selectArea)
                        , edAddress.text.toString()
                        , edPhone.text.toString(), is_default))
            } else {
                mPresenter.addAddress(AddAddressReq(edName.text.toString()
                        , selectProvince
                        , CityUtils.instance.getProvinceCode(this, selectProvince)
                        , selectCity
                        , CityUtils.instance.getCityCode(this, selectProvince, selectCity)
                        , selectArea
                        , CityUtils.instance.getAreaCode(this, selectProvince, selectCity, selectArea)
                        , edAddress.text.toString()
                        , edPhone.text.toString(), is_default))
            }
        }

        ivDef.onClick {
            ivDef.isSelected = !ivDef.isSelected
        }
    }

    private fun initData() {
        if (intent.getBooleanExtra("update", false)) {
            var data = intent.getSerializableExtra("data") as AddressBean
            edName.setText(data.consignee)
            edPhone.setText(data.mobile)

            tvAddress.text = data.province + "-" + data.city + "-" + data.district
            edAddress.setText(data.address)
            ivDef.isSelected = data.is_default == 1
            selectProvince = data.province
            selectCity = data.city
            selectArea = data.district
            addID = data.address_id
        }
    }
}
