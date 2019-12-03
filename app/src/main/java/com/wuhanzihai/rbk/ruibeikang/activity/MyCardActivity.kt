package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.MyCardBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamIdTypeReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.MyCardPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MyCardView
import kotlinx.android.synthetic.main.activity_my_card.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class MyCardActivity : BaseMvpActivity<MyCardPresenter>(), MyCardView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onMyCardResult(result: MyCardBean) {
        if (result.vipcard != null) {
            tvText11.text = "卡号: ${result.vipcard.card_id}\n密码:${result.vipcard.password}"
            tvTime.text = SimpleDateFormat("yyyy-MM-dd").format(Date(result.vipcard.activation_time * 1000))
        }
        if (result.uaer_agent_grade.g_name != null) {
            tvCard3.text = result.uaer_agent_grade.g_name
            tvText33.text = "售卡佣金：分享售出1张可得  ¥${result.uaer_agent_grade.direct_card}"
            tvText3.visibility = View.VISIBLE
        } else {
            tvText333.visibility = View.VISIBLE
        }

        if (result.bankcard.card_number != null) {
            tvCard2.text = result.bankcard.bank_name
            tvText2.text = result.bankcard.card_type_name
            tvText22.text = toCardNo(result.bankcard.card_number)
            tvText2.visibility = View.VISIBLE
        } else {
            tvText222.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_card)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()
    }

    private fun initView() {
        clBank.onClick {
            startActivity<BankCardActivity>()
        }
        clDent.onClick {
            startActivity<ApplyLevelActivity>()
        }
        tvText3.onClick {
            startActivity<StandardWebActivity>("title" to "返利规则"
                    , "data" to "http://api.hcjiankang.com/api/Web/article?id=773")
        }
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initData() {
        mPresenter.myCard(NoParamIdTypeReq(0))
    }

    private fun toCardNo(code: String): String {
        var a = code.length
        var b = 0
        b = a / 4 + 1
        var list = mutableListOf<String>()
        for (i in 0 until b) {
            if (i == b - 1) {
                list.add(code.substring(i * 4))
            } else {
                list.add(code.substring(i * 4, i * 4 + 4))
            }
        }
        var result = ""
        for (s in list) {
            result = result + s + " "
        }
        return result
    }
}
