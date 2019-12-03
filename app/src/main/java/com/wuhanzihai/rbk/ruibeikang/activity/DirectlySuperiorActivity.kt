package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.data.entity.UpLevelBean
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.DirectlySuperiorPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.DirectView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.DirectlySuperiorView
import kotlinx.android.synthetic.main.activity_directly_superior.*
import org.jetbrains.anko.act

class DirectlySuperiorActivity : BaseMvpActivity<DirectlySuperiorPresenter>(), DirectlySuperiorView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onUplevelResult(result: UpLevelBean) {
        if (result.topagent.card_no.isEmpty()) {
            tvTimeDef.text = result.topagent.create_time
            tvNameDef.text = result.topagent.nickname
            return
        }

        clInfo.visibility = View.VISIBLE

        if (result.topagent.head_pic.isNotEmpty()) {
            ivHead.loadImage(result.topagent.head_pic)
        }
        tvName.text = result.topagent.g_name + "\n" + result.topagent.nickname

        tvAddress.text = result.topagent.card_no
        tvTime.text = result.topagent.agent_create_time

        tvText2.text = result.defaultagent.g_name + ":"
        tvName2.text = result.defaultagent.nickname
        tvAddress1.text = result.defaultagent.card_no
        tvTime1.text = result.defaultagent.agent_create_time
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directly_superior)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {


    }

    private fun initData() {
        mPresenter.myDisbutor()
    }
}
