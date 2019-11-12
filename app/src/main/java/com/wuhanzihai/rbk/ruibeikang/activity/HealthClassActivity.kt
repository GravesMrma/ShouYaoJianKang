package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoader
import com.wuhanzihai.rbk.ruibeikang.common.setOnBannerListener
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamPageReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthClassPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthClassView
import kotlinx.android.synthetic.main.activity_health_class.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class HealthClassActivity : BaseMvpActivity<HealthClassPresenter>(), HealthClassView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onHealthClassResult(result: HealthClassBean) {
        srView.finishRefresh()
        srView.finishLoadMore()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    override fun onHealthBannerResult(result: HealthClassBannerBean) {
        mBanner.update(result.item)
        mBanner.setOnBannerListener {
            setOnBannerListener(act,result.item[it])
        }
    }

    override fun onHealthClassDetailResult(result: HealthClassDetailBean) {
    }

    override fun onHealthClassDetailMusicResult(result: HealthClassDetailMusicBean) {
    }

    private lateinit var list: MutableList<HealthClassItem>
    private lateinit var adapter: BaseQuickAdapter<HealthClassItem, BaseViewHolder>
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_class)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        mBanner.setImageLoader(FrescoBannerLoader(true))
                .start()

        list = mutableListOf()
        adapter = object : BaseQuickAdapter<HealthClassItem, BaseViewHolder>(R.layout.item_health_class, list) {
            override fun convert(helper: BaseViewHolder?, item: HealthClassItem?) {
                helper!!.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item!!.course_img)
                helper.setText(R.id.tvName, item.course_title)
                        .setText(R.id.tvDesc, item.course_title)
                        .setText(R.id.tvLisNum, item.course_click.toString())

                if (helper.layoutPosition < 3) {
                    if (item.course_click == 0) {
                        helper.setText(R.id.tvLisNum, "1000+")
                    }
                } else {
                    if (item.course_click == 0) {
                        helper.setText(R.id.tvLisNum, "2000+")
                    }
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        adapter.setOnItemClickListener { _, _, position ->
            if (position < 3) {
                startActivity<HealthCallActivity>("id" to list[position].mc_id, "lisnum" to "1000+", "faker" to true)
            } else {
                startActivity<HealthCallActivity>("id" to list[position].mc_id, "lisnum" to "2000+", "faker" to true)
            }
        }

        srView.setOnRefreshListener {
            list.clear()
            page = 1
            initData()
        }

        srView.setOnLoadMoreListener {
            page++
            initData()
        }
    }

    private fun initData() {
        mPresenter.healthClass(NoParamPageReq(page))
        mPresenter.healthBanner()
    }
}
