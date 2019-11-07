package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.layout_webview.*
import android.webkit.WebViewClient
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthClassBannerBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthClassBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthClassDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthClassDetailMusicBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.HealthClassDetailReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthClassPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthClassView

@SuppressLint("ValidFragment")
class WebFragment(var mc_id: Int) : BaseMvpFragment<HealthClassPresenter>(), HealthClassView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onHealthClassResult(result: HealthClassBean) {
    }

    override fun onHealthBannerResult(result: HealthClassBannerBean) {
    }

    override fun onHealthClassDetailResult(result: HealthClassDetailBean) {
        webView.loadDataWithBaseURL(
                null,
                result.course_desc,
                BaseConstant.MIME_TYPE,
                BaseConstant.ENCODING,
                null
        )
    }

    override fun onHealthClassDetailMusicResult(result: HealthClassDetailMusicBean) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_webview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        initView()

        initData()
    }

    private fun initView() {
        val settings = webView.settings
        settings.javaScriptEnabled = false  // js jiaohu
        settings.setSupportZoom(false) //支持缩放，默认为true。是下面那个的前提。
        settings.builtInZoomControls = false //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.displayZoomControls = true //隐藏原生的缩放控件
    }

    private fun initData() {
        mPresenter.healthClassDetail(HealthClassDetailReq(mc_id))
    }
}