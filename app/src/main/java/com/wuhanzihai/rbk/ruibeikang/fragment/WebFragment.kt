package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.layout_webview.*
import android.webkit.WebViewClient


class WebFragment:Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_webview,null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        initView()

        initData()

    }

    private fun initView(){

//        webView.setOnTouchListener { v, event ->
//            return@setOnTouchListener true
//        }

        val settings = webView.settings
        settings.javaScriptEnabled = false  // js jiaohu
        settings.setSupportZoom(false) //支持缩放，默认为true。是下面那个的前提。
        settings.builtInZoomControls = false //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.displayZoomControls = true //隐藏原生的缩放控件
        webView.setBackgroundColor(0)
        webView.background.alpha = 0
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false

//        webView.settings.useWideViewPort = true
//
//        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
//
//        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
//        webView.settings.loadWithOverviewMode = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("http://www.hcjiankang.com/index.php/api/Article/get_article_contents.html?id=1643")

//        AgentWeb.with(this)
//                .setAgentWebParent(llView as LinearLayout, LinearLayout.LayoutParams(-1, -1))
//                .useDefaultIndicator()
//                .createAgentWeb()
//                .ready()
//                .go("http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT35316404&lng=cn")
    }

    private fun initData(){}


}