package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Html
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.ApplyCardPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ApplyCardView
import kotlinx.android.synthetic.main.activity_apply_card.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class ApplyCardActivity : BaseMvpActivity<ApplyCardPresenter>(), ApplyCardView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onApplyCard() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_card)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {

        tvText1.text = Html.fromHtml("如果您需要将卡号库存中的虚拟卡制作成实物卡用于<font color=#FFA200>线下推广、礼品赠送</font>等用途,可以在此向平台申请制作实体卡;")
        tvText2.text = Html.fromHtml("实体卡每次最多申请<font color=#FFA200>20</font>张，每月最多申请<font color=#FFA200>2</font>次;")
        tvText3.text = Html.fromHtml("为避免套卡等侵害平台权益的行为，申请实体卡需要您按照<font color=#FFA200>每张299元垫付款项</font>，您售卖后的佣金按照卡的已激活状态返现至您的--->提现金额中;")

        tvCommit.onClick {
            startActivity<ApplyCardDetailActivity>()
        }
    }

    private fun initData() {

    }
}
