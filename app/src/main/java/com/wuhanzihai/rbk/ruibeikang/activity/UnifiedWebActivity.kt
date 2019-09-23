package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.ArticleDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ArticleDetailReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.presenter.UnifiedWebPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ArticleDetailView
import kotlinx.android.synthetic.main.activity_unified_web.*

class UnifiedWebActivity : BaseMvpActivity<UnifiedWebPresenter>(), ArticleDetailView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onArticleDetailResult(result: ArticleDetailBean) {
        webView.loadDataWithBaseURL(
                null,
                result.content,
                BaseConstant.MIME_TYPE,
                BaseConstant.ENCODING,
                null
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_unified_web)

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
        mPresenter.articleDetail(ArticleDetailReq(intent.getIntExtra("id",-1)))
    }

}
