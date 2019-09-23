package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_machine.*

class MachineActivity : AppCompatActivity() {
    private var type = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_machine)

        type = intent.getIntExtra("type", 1)

        initView()

        initData()
    }

    private fun initView(){
        if (type == 1){
            rlBloodPressure.visibility = View.VISIBLE
        }

        if (type == 2){
            rlBloodSugar.visibility = View.VISIBLE
        }
    }

    private fun initData(){

    }

}
