package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.eightbitlab.rxbus.Bus
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.event.MainFragmentEvent
import kotlinx.android.synthetic.main.activity_pay_result.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class PayResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_result)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))


        initView()

        initData()
    }

    private fun initView() {

        tvMore.onClick {
            Bus.send(MainFragmentEvent(2))
            startActivity<MainActivity>()
        }

        tvCommit.onClick {
            startActivity<OrderActivity>()
        }
    }

    private fun initData() {

    }
}
