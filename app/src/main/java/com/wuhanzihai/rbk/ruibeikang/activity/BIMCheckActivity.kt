package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hhjt.baselibrary.ext.onClick
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_bim_check.*
import org.jetbrains.anko.startActivity

// 血氧 血压监测
class BIMCheckActivity : AppCompatActivity() {
    private var type = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bim_check)

        type = intent.getIntExtra("type", 1)
        initView()

        initData()

    }

    private fun initView() {

        if (type == 1) {
            tvTitle.setTitleText("血压自检")
            tvSearch.text = "血压数据查询"
        }
        if (type == 2) {
            tvTitle.setTitleText("血糖自检")
            tvSearch.text = "血糖数据查询"
        }

        tvMachine.onClick {

            startActivity<BloodCheckActivity>()
        }
        tvManual.onClick {
            startActivity<MachineActivity>("type" to type)
        }
        tvSearch.onClick {
            startActivity<BloodPreDataActivity>()

        }
    }

    private fun initData() {

    }

}
