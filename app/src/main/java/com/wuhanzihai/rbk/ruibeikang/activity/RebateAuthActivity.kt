package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.hhjt.baselibrary.utils.LoginUtils
import com.jaeger.library.StatusBarUtil
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.RebateAddressReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.RebateAddressPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.RebateAddressView
import com.wuhanzihai.rbk.ruibeikang.utils.CityUtils
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomCityPicker
import kotlinx.android.synthetic.main.activity_rebate_auth.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity

class RebateAuthActivity : BaseMvpActivity<RebateAddressPresenter>(), RebateAddressView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

//    override fun onRebateAddressResult() {
//        startActivity<RebateActivity>()
//        finish()
//    }
//
//    override fun onRebateResult(result: RebateBean) {
//        Hawk.put(BaseConstant.REBATE_INFO, result)
//        LoginUtils.saveRebateId(result.id)
//    }

    override fun onAuthRebateResult() {
        startActivity<RebateActivity>()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rebate_auth)
        StatusBarUtil.setTranslucentForImageView(act, 0, null)

        initView()

        initData()
    }

    private fun initView() {
        ivBack.onClick {
            finish()
        }

        ivBt.onClick {
            mPresenter.authRebate()

//            if (selectArea != null) {
//                mPresenter.rebateAddress(RebateAddressReq("", CityUtils.instance.getProvinceCode(act, selectProvince).toInt()
//                        , CityUtils.instance.getCityCode(this, selectProvince, selectCity).toInt()
//                        , CityUtils.instance.getAreaCode(this, selectProvince, selectCity, selectArea).toInt()))
//            }
        }
    }

    private fun initData() {
//        mPresenter.disbutorIndex()
    }
}
