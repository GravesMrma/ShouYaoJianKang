package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.utils.LoginUtils
import com.jaeger.library.StatusBarUtil
import com.kotlin.base.utils.AppPrefsUtils
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class WelcomeActivity : AppCompatActivity() {

    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (AppPrefsUtils.getBoolean(BaseConstant.IS_FIRST)) {
            setContentView(R.layout.activity_welcome)
            StatusBarUtil.setTranslucentForImageView(act, 0, null)
            handler.postDelayed({
                if (LoginUtils.getLoginStatus()) {
                    startActivity<MainActivity>()
                } else {
                    startActivity<LoginActivity>()
                }
                finish()
            }, 2000)
        } else {
            startActivity<GuideActivity>()
            finish()
        }
    }
}
