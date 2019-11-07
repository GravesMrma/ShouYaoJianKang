package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.GoodsDetailActivity
import com.wuhanzihai.rbk.ruibeikang.activity.GoodsServiceDetailActivity
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsClassBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsResult
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GoodsReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemNews
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthCarePresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthCareView
import kotlinx.android.synthetic.main.fragment_health_care.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

@SuppressLint("ValidFragment")
class HealthCareFragment(var category_id: Int) : BaseMvpFragment<HealthCarePresenter>(), HealthCareView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule())
                .build().inject(this)
        mPresenter.mView = this
    }

    override fun onGoodsListResult(result: GoodsResult) {
        srView.finishRefresh()
        srView.finishLoadMore()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    private lateinit var adapter: BaseQuickAdapter<GoodsBean, BaseViewHolder>
    private lateinit var list: MutableList<GoodsBean>
    private var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_health_care, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<GoodsBean, BaseViewHolder>(R.layout.item_health_goods, list) {
            override fun convert(helper: BaseViewHolder?, item: GoodsBean?) {
                // type  0   实物商品   1 虚拟商品

                helper!!.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item!!.image)
                helper.setText(R.id.tvName, item.name)
                helper.setText(R.id.tvDesc, item.intro)
                helper.setText(R.id.tvPrice, item.price)

                if (item.is_reservation == 0) {
                    if (item.sales==0){
                        if (item.product_id  == 145||
                                item.product_id  == 146||
                                item.product_id  == 147||
                                item.product_id  == 148){
                            helper.setText(R.id.tvReserve, "${item.counterfeit_sales}人已领取")
                        }else{
                            helper.setText(R.id.tvReserve, "${item.counterfeit_sales}人已购买")
                        }
                    }else{
                        if (item.product_id  == 145||
                                item.product_id  == 146||
                                item.product_id  == 147||
                                item.product_id  == 148){
                            helper.setText(R.id.tvReserve, "${item.sales}人已领取")
                        }else{
                            helper.setText(R.id.tvReserve, "${item.sales}人已购买")
                        }
                    }
                    helper.setText(R.id.tvOldPrice, item.original_price)
                    helper.getView<TextView>(R.id.tvOldPrice).paint.flags = Paint.STRIKE_THRU_TEXT_FLAG; //中划线
                    helper.getView<TextView>(R.id.tvOldPrice).visibility = View.VISIBLE
                } else {
                    if (item.sales==0){
                        helper.setText(R.id.tvReserve, "${item.counterfeit_sales}人已预定")
                    }else{
                        helper.setText(R.id.tvReserve, "${item.sales}人已预定")
                    }
                    helper.getView<TextView>(R.id.tvOldPrice).visibility = View.GONE
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemNews(act))
        adapter.emptyView = getEmptyView(act,"暂无商品,请下拉刷新")
        adapter.setOnItemClickListener { adapter, view, position ->
            if (list[position].is_reservation == 0){
                startActivity<GoodsDetailActivity>("id" to list[position].product_id)
            }else{
                startActivity<GoodsServiceDetailActivity>("id" to list[position].product_id)
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
        mPresenter.getGoodsList(GoodsReq(category_id, page))
    }

    fun refreshData(category_id: Int) {
        this.category_id = category_id
        list.clear()
        page = 1
        initData()
    }
}