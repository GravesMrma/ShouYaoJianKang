package com.wuhanzihai.rbk.ruibeikang.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.just.agentweb.AgentWeb
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.ArticleDetailBean
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.presenter.UnifiedWebPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ArticleDetailView
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import kotlinx.android.synthetic.main.activity_travel_web.*
import org.jetbrains.anko.act

class TravelWebActivity : BaseMvpActivity<UnifiedWebPresenter>(), ArticleDetailView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onColloectResult() {}
    override fun onLikeResult() {}

    override fun onArticleDetailResult(result: ArticleDetailBean) {
////        tvLikeNum.text = result.follow.toString()
//        ivLike.isSelected = result.iszan == 1
//        ivCollect.isSelected = result.isshouchan == 1
//        tvText.text = result.title
//        tvReadNum.text = result.click.toString()
//        tvTime.text = result.hot_time
//        var agn = AgentWeb.with(this)
//                .setAgentWebParent(llView as LinearLayout, LinearLayout.LayoutParams(-1, -1) as ViewGroup.LayoutParams)
//                .useDefaultIndicator()
//                .createAgentWeb()
//                .ready()
//                .go(BaseConstant.BASE_URL+"api/Web/article?id="+result.article_id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_web)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))


        initView()

        initData()
    }

    private fun initView() {
        btCall.onClick {
            MyUtils.instance.callPhone(act,"4000186617")
        }
    }

    private fun initData() {
        AgentWeb.with(this)
                .setAgentWebParent(llView as LinearLayout, LinearLayout.LayoutParams(-1, -1) as ViewGroup.LayoutParams)
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(BaseConstant.BASE_URL + "api/Web/article?id=" + intent.getIntExtra("id", -1))
    }
}
