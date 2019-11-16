package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateBean
import kotlinx.android.synthetic.main.activity_apply_cash.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import per.goweii.anylayer.AnyLayer

class ApplyCashActivity : AppCompatActivity() {

    private val dialog by lazy {
        val anyLayer =
                AnyLayer.with(act)
                        .contentView(R.layout.layout_charge)
                        .gravity(Gravity.CENTER)
                        .backgroundResource(R.color.clarity_40)
                        .cancelableOnTouchOutside(true)
                        .onClick(R.id.tvCommit) { anyLayer, v ->
                            anyLayer.dismiss()
                        }
                        .onClick(R.id.ivClose) { anyLayer, v ->
                            anyLayer.dismiss()
                        }
        anyLayer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_cash)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        tvBank.onClick {
            startActivity<BankCardActivity>()
        }
        tvSearch.onClick {
            startActivity<ApplyCashProcessActivity>()
        }
        tvCommit.onClick {
            dialog.show()
        }

        var data = Hawk.get<RebateBean>(BaseConstant.REBATE_INFO)
        tvMoney.text = data.toexmaine_money.toString()
    }

    private fun initData() {

    }
}
