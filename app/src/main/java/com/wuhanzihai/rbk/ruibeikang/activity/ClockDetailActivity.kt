package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hhjt.baselibrary.common.BaseConstant
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import kotlinx.android.synthetic.main.activity_clock_detail.*

class ClockDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock_detail)

        initView()

        initData()
    }


    private fun initView(){
        val settings = webView.settings
        settings.javaScriptEnabled = false  // js jiaohu
        settings.setSupportZoom(false) //支持缩放，默认为true。是下面那个的前提。
        settings.builtInZoomControls = false //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.displayZoomControls = true //隐藏原生的缩放控件

    }

    private fun initData(){
        var data = intent.getStringExtra("data")

        tvTitle.setTitleText(intent.getStringExtra("title"))

        webView.loadDataWithBaseURL(
                null,
                MyUtils.myUtils.htmlFormat(data),
                BaseConstant.MIME_TYPE,
                BaseConstant.ENCODING,
                null
        )
    }
}
