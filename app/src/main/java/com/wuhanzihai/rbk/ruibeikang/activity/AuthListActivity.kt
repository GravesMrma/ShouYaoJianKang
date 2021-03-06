package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import org.jetbrains.anko.act

class AuthListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_list)

        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

    }
}
