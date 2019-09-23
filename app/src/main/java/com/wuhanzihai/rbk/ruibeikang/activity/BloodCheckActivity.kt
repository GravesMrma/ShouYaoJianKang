package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hhjt.baselibrary.ext.onClick
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.dialog.BluetoothDialog
import kotlinx.android.synthetic.main.activity_blood_check.*

// 血压 蓝牙页面
class BloodCheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_check)


        initView()

        initData()
    }

    private fun initView() {

        tvConnect.onClick {

            BluetoothDialog(this,true,true).show()
        }
    }

    private fun initData() {

    }
}
