package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_bim_check.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

// 血氧 血压监测
class BIMCheckActivity : AppCompatActivity() {
    private var type = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bim_check)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        type = intent.getIntExtra("type", 1)
        initView()

        initData()
    }

    private fun initView() {
        if (type == 1) {
            tvTitle.setTitleText("血压自检")
            tvMachine.loadImage("http://www.hcjiankang.com/androidimg/ic_xueya3.png")
            tvManual.loadImage("http://www.hcjiankang.com/androidimg/ic_xueya1.png")
            tvSearch.loadImage("http://www.hcjiankang.com/androidimg/ic_xueya2.png")
        }
        if (type == 2) {
            tvTitle.setTitleText("血糖自检")
            tvMachine.loadImage("http://www.hcjiankang.com/androidimg/ic_xuetang3.png")
            tvManual.loadImage("http://www.hcjiankang.com/androidimg/ic_xuetang1.png")
            tvSearch.loadImage("http://www.hcjiankang.com/androidimg/ic_xuetang2.png")
        }

        tvMachine.onClick {
            if (type == 1) {
                startActivity<BloodCheckActivity>("title" to "血压", "type" to 1)
            }
            if (type == 2) {
                startActivity<BloodCheckActivity>("title" to "血糖", "type" to 2)
            }
        }
        tvManual.onClick {
            startActivity<MachineActivity>("type" to type)
        }
        tvSearch.onClick {
            startActivity<BloodPreDataActivity>("type" to type)
        }
    }

    private fun initData() {

    }

}
