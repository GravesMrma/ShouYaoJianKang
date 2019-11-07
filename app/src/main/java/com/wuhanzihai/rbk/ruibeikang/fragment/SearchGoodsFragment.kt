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
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.HealthHabitsDetailActivity
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem11_11_10
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthHabitsPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthHabitsView
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.wuhanzihai.rbk.ruibeikang.activity.GoodsDetailActivity
import com.wuhanzihai.rbk.ruibeikang.activity.SearchActivity
import com.wuhanzihai.rbk.ruibeikang.activity.UnifiedWebActivity
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.protocal.HealthListReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.SearchReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.presenter.SearchPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SearchView

class SearchGoodsFragment() : BaseMvpFragment<SearchPresenter>(), SearchView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onHealthListResult(result: HealthListBean) {

    }

    override fun onSearchBean(result: SearchBean) {

    }

    override fun onHealthClassResult(result: HealthClassBean) {

    }

    override fun onSearchGoodsBean(result: GoodsResult) {
        srView.finishRefresh()
        srView.finishLoadMore()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    private lateinit var list: MutableList<GoodsBean>
    private lateinit var adapter: BaseQuickAdapter<GoodsBean, BaseViewHolder>
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
        Log.e("搜索", "list init")
        adapter = object : BaseQuickAdapter<GoodsBean, BaseViewHolder>(R.layout.item_cells_goods, list) {
            override fun convert(helper: BaseViewHolder?, item: GoodsBean?) {
                helper!!.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item!!.image)
                helper.setText(R.id.tvName, item.name)
                helper.setText(R.id.tvSpec, item.intro)
                helper.setText(R.id.tvPrice, item.original_price)
                helper.setText(R.id.tvNum, "已热销:${item.buyer_quota}")
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem11_11_10(act))
        adapter.emptyView = getEmptyView(act,"暂无数据")
        adapter.setOnItemClickListener { adapter, view, position ->
            startActivity<GoodsDetailActivity>("id" to list[position].product_id)
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
        mPresenter.searchGoods(SearchReq(SearchActivity.keyWord, page))
    }

    fun refreshData() {
        if (isResumed) {
            page = 1
            list.clear()
            initData()
        }
    }
}