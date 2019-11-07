package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.hhjt.baselibrary.utils.DateUtils
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.HealthHabitsDetailActivity
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.support.v4.act
import com.wuhanzihai.rbk.ruibeikang.activity.SearchActivity
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.protocal.SearchReq
import com.wuhanzihai.rbk.ruibeikang.event.MusicEvent
import com.wuhanzihai.rbk.ruibeikang.presenter.SearchPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SearchView

class SearchClassFragment() : BaseMvpFragment<SearchPresenter>(), SearchView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onHealthListResult(result: HealthListBean) {

    }
    override fun onSearchBean(result: SearchBean) {

    }
    override fun onSearchGoodsBean(result: GoodsResult) {

    }

    override fun onHealthClassResult(result: HealthClassBean) {
        srView.finishRefresh()
        srView.finishLoadMore()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    override fun onMusicDetailResult(result: MusicDetailBean) {

    }

    private lateinit var list: MutableList<HealthClassItem>
    private lateinit var adapter: BaseQuickAdapter<HealthClassItem, BaseViewHolder>
    private var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_recyclerview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        Log.e("搜索","list init")
        adapter = object : BaseQuickAdapter<HealthClassItem,BaseViewHolder>(R.layout.item_health_class_item,list){
            override fun convert(helper: BaseViewHolder?, item: HealthClassItem?) {
                helper!!.setText(R.id.tvName,item!!.course_title)
                        .setText(R.id.tvLisNum,"预计时长")
                        .setText(R.id.tvTime,DateUtils.longToString(item.course_date.toLong(),"yyyy-MM-dd"))
                helper.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.course_img)
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        adapter.emptyView = getEmptyView(act,"暂无数据")
        adapter.setOnItemClickListener { _, _, position ->

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
        mPresenter.searchClass(SearchReq(SearchActivity.keyWord, page))
    }

    fun refreshData() {
        if (isResumed) {
            page = 1
            list.clear()
            initData()
        }
    }
}