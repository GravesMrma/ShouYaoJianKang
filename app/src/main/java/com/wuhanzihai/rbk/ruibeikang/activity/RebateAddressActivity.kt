package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.RebateAddressReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.RebateAddressPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.RebateAddressView
import com.wuhanzihai.rbk.ruibeikang.utils.CityUtils
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomCityPicker
import kotlinx.android.synthetic.main.activity_rebate_address.*
import org.jetbrains.anko.act
import org.jetbrains.anko.toast

class RebateAddressActivity : BaseMvpActivity<RebateAddressPresenter>(), RebateAddressView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onAuthRebateResult() {

    }

//    override fun onRebateResult(result: RebateBean) {
//        tvName.text = result.name
//        tvPhone.text = result.phone
//        selectProvince = CityUtils.instance.getProvince(act, result.province)
//        selectCity = CityUtils.instance.getCity(act, result.province, result.city)
//        selectArea = CityUtils.instance.getArea(act, result.province, result.city, result.area)
//        tvAddress.text = CityUtils.instance.getProvince(act, result.province) +
//                CityUtils.instance.getCity(act, result.province, result.city) +
//                CityUtils.instance.getArea(act, result.province, result.city, result.area)
//        edAddress.setText(result.address)
//    }
//
//    override fun onRebateAddressResult() {
//        toast("修改成功")
//        finish()
//    }

    private var selectProvince = ""
    private var selectCity = ""
    private var selectArea = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rebate_address)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

//        initView()
//
//        initData()
    }

//    private fun initView() {
//        lAddress.onClick {
//            CustomCityPicker(this, CustomCityPicker.ResultHandler { province, city, area ->
//                selectProvince = province
//                selectCity = city
//                selectArea = area
//                tvAddress.text = "${province}-${city}-${area}"
//
//            }).show()
//        }
//
//        tvAddAddress.onClick {
//            mPresenter.rebateAddress(RebateAddressReq(edAddress.text.toString(),
//                    CityUtils.instance.getProvinceCode(act, selectProvince).toInt()
//                    , CityUtils.instance.getCityCode(this, selectProvince, selectCity).toInt()
//                    , CityUtils.instance.getAreaCode(this, selectProvince, selectCity, selectArea).toInt()))
//        }
//    }
//
//    private fun initData() {
//        mPresenter.disbutorIndex()
//    }
}
