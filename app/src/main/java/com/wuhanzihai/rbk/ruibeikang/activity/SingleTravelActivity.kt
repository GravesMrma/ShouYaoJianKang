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
import com.wuhanzihai.rbk.ruibeikang.data.entity.TravelBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.TravelItem
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import com.wuhanzihai.rbk.ruibeikang.presenter.TravelPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.TravelView
import kotlinx.android.synthetic.main.activity_single_travel.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class SingleTravelActivity : BaseMvpActivity<TravelPresenter>(),TravelView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onTravelResult(result: TravelBean) {
        srView.finishLoadMore()
        srView.finishRefresh()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    private lateinit var list: MutableList<TravelItem>
    private lateinit var adapter: BaseQuickAdapter<TravelItem,BaseViewHolder>
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_travel)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<TravelItem, BaseViewHolder>(R.layout.item_image_travel, list){
            override fun convert(helper: BaseViewHolder?, item: TravelItem?) {
                helper!!.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item!!.thumb)
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        rvView.addItemDecoration(DividerItem14_14_14(act))
        adapter.setOnItemClickListener { _, _, position ->
            startActivity<TravelWebActivity>("id" to list[position].article_id)
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

    private fun initData(){
        mPresenter.singleTravel(page)
    }
}
