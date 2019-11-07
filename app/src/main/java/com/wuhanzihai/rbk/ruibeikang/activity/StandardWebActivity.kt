package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.just.agentweb.AgentWeb
import com.kotlin.base.utils.AppPrefsUtils
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_standard_web.*
import org.jetbrains.anko.act

class StandardWebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standard_web)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        tvTitle.setTitleText(intent.getStringExtra("title"))

        AgentWeb.with(this)
                .setAgentWebParent(llView as LinearLayout, LinearLayout.LayoutParams(-1, -1) as ViewGroup.LayoutParams)
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(intent.getStringExtra("data"))


        if (intent.getStringExtra("title") == "健康打卡") {
            btCommit.visibility = View.VISIBLE
            btCommit.isSelected = AppPrefsUtils.getBoolean(BaseConstant.FAHJASJK)
            if (btCommit.isSelected){
                btCommit.text = "已预约"
            }
            btCommit.onClick {
                if (!btCommit.isSelected) {
                    btCommit.text = "已预约"
                    AppPrefsUtils.putBoolean(BaseConstant.FAHJASJK, true)
                }
            }
        }
    }
}
