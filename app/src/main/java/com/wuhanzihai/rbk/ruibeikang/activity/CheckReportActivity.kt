package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_check_report.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class CheckReportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_report)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))


        initView()

        initData()
    }

    private fun initView() {
        ivImg1.onClick {
            startActivity<GeneArchivesActivity>()
        }
        ivImg2.onClick {
            startActivity<StandardWebActivity>("title" to "瑞慈体检"
                    , "data" to "https://report.rich-healthcare.com/medicalReport/?rcc_id=61ffeda40d8cf9878e70f04a0cc1680d")
        }
    }

    private fun initData() {

    }
}
