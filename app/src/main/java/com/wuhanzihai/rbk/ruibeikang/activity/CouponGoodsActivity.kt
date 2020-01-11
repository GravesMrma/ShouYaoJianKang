package com.wuhanzihai.rbk.ruibeikang.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsResult
import com.wuhanzihai.rbk.ruibeikang.data.protocal.CouponGoodsReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GoodsReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemNews
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthCarePresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthCareView
import kotlinx.android.synthetic.main.activity_coupon_goods.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class CouponGoodsActivity : BaseMvpActivity<HealthCarePresenter>(), HealthCareView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule())
                .build().inject(this)
        mPresenter.mView = this
    }

    override fun onCouponGoodsResult(result: GoodsResult) {
        srView.finish()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }


    private lateinit var adapter: BaseQuickAdapter<GoodsBean, BaseViewHolder>
    private lateinit var list: MutableList<GoodsBean>
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_goods)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

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
                    helper.setImageResource(R.id.ivVip, R.mipmap.ic_vip_tag)
                    if (item.sales == 0) {
                        if (item.product_id == 146 ||
                                item.product_id == 147 ||
                                item.product_id == 148 ||
                                item.product_id == 152 ||
                                item.product_id == 154 ||
                                item.product_id == 155 ||
                                item.product_id == 156 ||
                                item.product_id == 157 ||
                                item.product_id == 158) {
                            helper.getView<ImageView>(R.id.ivVip).visibility = View.GONE
                            helper.getView<TextView>(R.id.tvVip).visibility = View.VISIBLE
                            helper.setText(R.id.tvReserve, "${item.counterfeit_sales}人已领取")
                        } else {
                            helper.getView<ImageView>(R.id.ivVip).visibility = View.VISIBLE
                            helper.getView<TextView>(R.id.tvVip).visibility = View.GONE
                            helper.setText(R.id.tvReserve, "${item.counterfeit_sales}人已购买")
                        }
                    } else {
                        if (item.product_id == 146 ||
                                item.product_id == 147 ||
                                item.product_id == 148 ||
                                item.product_id == 152 ||
                                item.product_id == 154 ||
                                item.product_id == 155 ||
                                item.product_id == 156 ||
                                item.product_id == 157 ||
                                item.product_id == 158) {
                            helper.getView<ImageView>(R.id.ivVip).visibility = View.GONE
                            helper.getView<TextView>(R.id.tvVip).visibility = View.VISIBLE
                            helper.setText(R.id.tvReserve, "${item.sales}人已领取")
                        } else {
                            helper.getView<ImageView>(R.id.ivVip).visibility = View.VISIBLE
                            helper.getView<TextView>(R.id.tvVip).visibility = View.GONE
                            helper.setText(R.id.tvReserve, "${item.sales}人已购买")
                        }
                    }
                } else {
                    helper.setImageResource(R.id.ivVip, R.mipmap.ic_dinjzf)
                    helper.getView<ImageView>(R.id.ivVip).visibility = View.VISIBLE
                    helper.getView<TextView>(R.id.tvVip).visibility = View.GONE
                    if (item.sales == 0) {
                        helper.setText(R.id.tvReserve, "${item.counterfeit_sales}人已预订")
                    } else {
                        helper.setText(R.id.tvReserve, "${item.sales}人已预订")
                    }
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemNews(act))
        adapter.emptyView = getEmptyView(act, "暂无商品,请下拉刷新")
        adapter.setOnItemClickListener { _, _, position ->
            if (list[position].is_reservation == 0) {
                startActivity<GoodsDetailActivity>("id" to list[position].product_id)
            } else {
                startActivity<GoodsServiceDetailActivity>("id" to list[position].product_id)
            }
        }

        srView.refresh({
            list.clear()
            initData()
        }, {
            srView.setNoMoreData(true)
            srView.finish()
        })
    }

    private fun initData() {
        mPresenter.getCouponGoods(CouponGoodsReq(intent.getIntExtra("couponId", 0), intent.getIntExtra("typeId", 0)))
    }
}
