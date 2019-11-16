package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.UpLevelBean
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.DirectlySuperiorPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.DirectView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.DirectlySuperiorView
import kotlinx.android.synthetic.main.activity_directly_superior.*
import org.jetbrains.anko.act

class DirectlySuperiorActivity : BaseMvpActivity<DirectlySuperiorPresenter>(),DirectlySuperiorView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onUplevelResult(result: UpLevelBean) {
        tvName.text = result.toexamine.name
        tvAddress.text = result.toexamine.province + result.toexamine.city + result.toexamine.area
        tvTime.text = result.toexamine.create_time

        tvName2.text = result.topdisbutor.name
        tvAddress1.text = result.topdisbutor.province + result.topdisbutor.city + result.topdisbutor.area
        tvTime1.text = result.topdisbutor.create_time
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directly_superior)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView(){



    }

    private fun initData(){
        mPresenter.myDisbutor()

    }
}
