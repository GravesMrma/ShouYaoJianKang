package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hhjt.baselibrary.ext.onClick
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_body_fat.*
import org.jetbrains.anko.startActivity

class BodyFatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body_fat)


        initView()

    }

    private fun initView(){
        tvData.onClick {
            startActivity<BodyFatDataActivity>()
        }
    }
}
