package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_about.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        tvAbout.onClick {
            startActivity<StandardWebActivity>("title" to "关于首要健康"
                    , "data" to "http://api.hcjiankang.com/api/Web/article?id=725")
        }
        tvUser.onClick {
            startActivity<StandardWebActivity>("title" to "用户服务协议"
                    , "data" to "http://api.hcjiankang.com/api/Web/article?id=722")
        }
        tvSecret.onClick {
            startActivity<StandardWebActivity>("title" to "隐私政策"
                    , "data" to "http://api.hcjiankang.com/api/Web/article?id=722")
        }
    }

    private fun initData() {

    }
}
