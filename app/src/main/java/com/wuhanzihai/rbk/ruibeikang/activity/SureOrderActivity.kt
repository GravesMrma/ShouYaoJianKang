package com.wuhanzihai.rbk.ruibeikang.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.CommitOrderReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.DoneCartReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemGoodsItem
import com.wuhanzihai.rbk.ruibeikang.presenter.SureOrderPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SureOrderView
import kotlinx.android.synthetic.main.activity_sure_order.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

// 购物车
class SureOrderActivity : BaseMvpActivity<SureOrderPresenter>(), SureOrderView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule())
                .build().inject(this)
        mPresenter.mView = this
    }

    override fun onDoneCartResult(result: SureOrderBean) {
        list.addAll(result.storedata)
        adapter.notifyDataSetChanged()
        for (product in result.storedata.first().product_list) {
            allMoney += product.price.toDouble()
        }
        tvAllMoney.text = "${allMoney}"
        tvMoney.text = "${allMoney}"
        tvCoupon.text = "${result.storedata.first().userstorecoupons.size}张可用"
        cartid = result.storedata.first().product_list.first().cartid
    }

    override fun onDefAddress(result: AddressBean) {
        tvName.text = result.consignee
        tvPhone.text = result.mobile
        tvAddress.text = result.address
        adrId = result.address_id
        tvAddAddress.visibility = View.INVISIBLE
        clAddress.visibility = View.VISIBLE

        if (result.is_default == 1) {
            tvTag.visibility = View.VISIBLE
        } else {
            tvTag.visibility = View.GONE
        }
    }

    override fun onCommitOrderResult(result: OrderDetailBean) {
        var data = ""
        for (s in result.orderlist) {
            data = "$data$s,"
        }
        if (data.isNotEmpty()) {
            startActivity<PayActivity>("data" to data.substring(0, data.length - 1)
                    , "price" to result.countprice)
            finish()
        } else {
            toast("订单确认无数据")
        }
    }

    private lateinit var list: MutableList<Storedata>
    private lateinit var adapter: BaseQuickAdapter<Storedata, BaseViewHolder>
    private var cartid = 0
    private var adrId = 0
    private var allMoney = 0.0

    private val dividerItemGoodsItem by lazy {
        DividerItemGoodsItem(act)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sure_order)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<Storedata, BaseViewHolder>(R.layout.item_order_sure, list) {
            override fun convert(helper: BaseViewHolder?, item: Storedata?) {
                helper!!.getView<RecyclerView>(R.id.rvView).run {
                    adapter = object : BaseQuickAdapter<Product, BaseViewHolder>(R.layout.item_order_sure_item, item!!.product_list) {
                        override fun convert(helper: BaseViewHolder?, item: Product?) {
                            helper!!.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item!!.product_image)
                            helper.setText(R.id.tvName, item.productname)
                                    .setText(R.id.tvSpec, item.skuname)
                                    .setText(R.id.tvPrice, item.price)
                                    .setText(R.id.tvNumber, "x${item.number}")
                        }
                    }
                    layoutManager = GridLayoutManager(act, 1)
                    if (itemDecorationCount != 0) {
                        removeItemDecoration(dividerItemGoodsItem)
                    }
                    addItemDecoration(dividerItemGoodsItem)
                }
                helper.setText(R.id.tvAllNum, "合计${list.first().product_list.size}件商品     小计:")
                        .setText(R.id.tvMoney, "${allMoney}")
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
//        rvView.addItemDecoration(DividerItemFive(act))

        tvAddAddress.onClick {
            startActivityForResult<AddressActivity>(2222, "chose" to true
                    , "addId" to adrId)
        }

        clAddress.onClick {
            startActivityForResult<AddressActivity>(2222, "chose" to true
                    , "addId" to adrId)
        }

        tvPay.onClick {
            if (adrId == 0) {
                showTextDesc(act, "请先添加收货地址")
            } else {
                mPresenter.commitOrder(CommitOrderReq(cartid, adrId))
            }
        }
    }

    private fun initData() {
        var data = intent.getStringExtra("data")
        mPresenter.doneCart(DoneCartReq(data))
        mPresenter.getDefAddress()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == 4444) {
            var dataAdr = data!!.getSerializableExtra("address") as AddressBean
            tvName.text = dataAdr.consignee
            tvPhone.text = dataAdr.mobile
            tvAddress.text = dataAdr.province + dataAdr.city + dataAdr.district + dataAdr.address
            adrId = dataAdr.address_id
            tvAddAddress.visibility = View.INVISIBLE
            clAddress.visibility = View.VISIBLE
            if (dataAdr.is_default == 1) {
                tvTag.visibility = View.VISIBLE
            } else {
                tvTag.visibility = View.GONE
            }
        }
    }
}
