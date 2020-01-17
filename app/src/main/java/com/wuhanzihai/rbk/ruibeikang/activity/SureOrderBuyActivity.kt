package com.wuhanzihai.rbk.ruibeikang.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.CommitBuyGoodsReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemCoupon
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemGoodsItem
import com.wuhanzihai.rbk.ruibeikang.presenter.SureOrderPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SureOrderView
import kotlinx.android.synthetic.main.activity_sure_order_buy.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import per.goweii.anylayer.AnyLayer
import java.text.SimpleDateFormat
import java.util.*

class SureOrderBuyActivity : BaseMvpActivity<SureOrderPresenter>(), SureOrderView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule())
                .build().inject(this)
        mPresenter.mView = this
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

    private var adrId = 0

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

    private lateinit var listCoupon: MutableList<CouponBean>
    private lateinit var adapterCoupon: BaseQuickAdapter<CouponBean, BaseViewHolder>

    private var coupon = ""
    private var couponId = ""

    private var allMoney = 0.0
    private var oldAllMoney = 0.0

    private val dialogCoupon by lazy {
        val anyLayer =
                AnyLayer.with(act)
                        .contentView(R.layout.layout_coupon)
                        .gravity(Gravity.BOTTOM)
                        .backgroundResource(R.color.clarity_40)
                        .cancelableOnTouchOutside(true)
                        .onClick(R.id.ivClose) { anyLayer, v ->
                            anyLayer.dismiss()
                        }
                        .onClick(R.id.tvNoUser) { anyLayer, v ->
                            coupon = ""
                            couponId = ""
                            allMoney = oldAllMoney
                            tvMoney11.text = "${allMoney}"
                            tvMoney.text = "${allMoney}"
                            tvCoupon.text = "${listCoupon.size} 张可用优惠券"
                            anyLayer.dismiss()
                        }
        anyLayer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sure_order_buy)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {

        listCoupon = mutableListOf()
        adapterCoupon = object : BaseQuickAdapter<CouponBean, BaseViewHolder>(R.layout.item_coupon, listCoupon) {
            override fun convert(helper: BaseViewHolder?, item: CouponBean?) {
                helper!!.setText(R.id.tvText, item!!.cname)
                        .setText(R.id.tvTime, "${SimpleDateFormat("yyyy.MM.dd").format(Date(item.start_time.toLong() * 1000))}-${SimpleDateFormat("yyyy.MM.dd").format(Date(item.end_time.toLong() * 1000))}")
                        .setText(R.id.tvDesc, item.description)
//1:代金券|2:满减券|3:折扣券|5:免单券
                var llView1 = helper.getView<LinearLayout>(R.id.llView1)
                var llView2 = helper.getView<LinearLayout>(R.id.llView2)
                var llView3 = helper.getView<LinearLayout>(R.id.llView3)
                var llView4 = helper.getView<LinearLayout>(R.id.llView4)
                llView1.visibility = View.GONE
                llView2.visibility = View.GONE
                llView3.visibility = View.GONE
                llView4.visibility = View.GONE
                when (item.type) {
                    1 -> {
                        llView1.visibility = View.VISIBLE
                        helper.setText(R.id.tvCash, item.face_money.replace(".00", ""))
                    }
                    2 -> {
                        llView2.visibility = View.VISIBLE
                        helper.setText(R.id.tvCoupon, item.face_money.replace(".00", ""))
                                .setText(R.id.tvLimit, "满${item.limit_money.replace(".00", "")}减${item.face_money.replace(".00", "")}")
                    }
                    3 -> {
                        llView3.visibility = View.VISIBLE
                        helper.setText(R.id.tvDiscount, item.face_money.replace(".00", ""))
                    }
                    4 -> {
                        llView4.visibility = View.VISIBLE
//                        helper.setText(R.id.tvCoupon, item.face_money)
                    }
                }

                if (item.limit_money.toDouble() == 0.0) {
                    helper.setText(R.id.tvLimit, "现金抵扣券")
                }

                helper.setText(R.id.tvState, "立即使用")
                helper.setBackgroundRes(R.id.rlView, R.mipmap.ic_coupon_bg)
                helper.setTextColor(R.id.tvState, ContextCompat.getColor(act, R.color.orange))
                        .setTextColor(R.id.tvL1tag1, ContextCompat.getColor(act, R.color.orange))
                        .setTextColor(R.id.tvCash, ContextCompat.getColor(act, R.color.orange))
                        .setTextColor(R.id.tvL1tag11, ContextCompat.getColor(act, R.color.orange))
                        .setTextColor(R.id.tvL2tag1, ContextCompat.getColor(act, R.color.orange))
                        .setTextColor(R.id.tvCoupon, ContextCompat.getColor(act, R.color.orange))
                        .setTextColor(R.id.tvLimit, ContextCompat.getColor(act, R.color.orange))
                        .setTextColor(R.id.tvDiscount, ContextCompat.getColor(act, R.color.orange))
                        .setTextColor(R.id.tvL3tag1, ContextCompat.getColor(act, R.color.orange))
                        .setTextColor(R.id.tvL3tag11, ContextCompat.getColor(act, R.color.orange))
                        .setTextColor(R.id.tvL4tag1, ContextCompat.getColor(act, R.color.orange))
                helper.setOnClickListener(R.id.tvText1) {
                    item.isShow = !item.isShow
                    if (item.isShow) {
                        helper.getView<RelativeLayout>(R.id.rlDesc).visibility = View.VISIBLE
                    } else {
                        helper.getView<RelativeLayout>(R.id.rlDesc).visibility = View.GONE
                    }
                    helper.getView<TextView>(R.id.tvText1).isSelected = item.isShow
                }
                helper.getView<TextView>(R.id.tvText1).isSelected = item.isShow

            }
        }
        dialogCoupon.getView<RecyclerView>(R.id.rvView).adapter = adapterCoupon
        dialogCoupon.getView<RecyclerView>(R.id.rvView).layoutManager = GridLayoutManager(act, 1)
        dialogCoupon.getView<RecyclerView>(R.id.rvView).addItemDecoration(DividerItemCoupon(act))
        adapterCoupon.emptyView = getEmptyView(act, "暂无可用优惠券")
        adapterCoupon.setOnItemClickListener { _, _, position ->
            when (listCoupon[position].type) {
                1 -> {
                    if (listCoupon[position].face_money.toDouble() > oldAllMoney) {
                        showTextDesc(act, "不符合使用条件")
                        return@setOnItemClickListener
                    }
                    coupon = "现金抵扣券 ¥" + listCoupon[position].face_money.replace(".00", "")
                }
                2 -> {
                    if (listCoupon[position].face_money.toDouble() > oldAllMoney) {
                        showTextDesc(act, "不符合使用条件")
                        return@setOnItemClickListener
                    }
                    coupon = "满${listCoupon[position].limit_money.replace(".00", "")}减${listCoupon[position].face_money.replace(".00", "")}"
                }
                3 -> {
                    coupon = "现金折扣券 " + listCoupon[position].face_money.replace(".00", "" + "折")
                }
                4 -> {
                    coupon = "会员礼品抵扣券"
                }
            }
            couponId = listCoupon[position].id.toString()

            when (listCoupon[position].type) {
                2 -> {
                    allMoney = oldAllMoney - listCoupon[position].face_money.toDouble()
                }
                3 -> {
                    allMoney = oldAllMoney * (listCoupon[position].face_money.toDouble() / 100)
                }
                1 -> {
                    allMoney = oldAllMoney - listCoupon[position].face_money.toDouble()
                }
                4 -> allMoney = 0.0
            }

            tvMoney11.text = "${allMoney}"
            tvMoney.text = "${allMoney}"
            tvCoupon.text = coupon
            dialogCoupon.dismiss()
        }

        tvAddAddress.onClick {
            startActivityForResult<AddressActivity>(2222, "chose" to true
                    , "addId" to adrId)
        }
        clAddress.onClick {
            startActivityForResult<AddressActivity>(2222, "chose" to true
                    , "addId" to adrId)
        }

        tvText2.onClick {
            dialogCoupon.show()
        }

        tvPay.onClick {
            if (adrId == 0) {
                showTextDesc(act, "请先添加收货地址")
            } else {
                mPresenter.commitBuyGoods(CommitBuyGoodsReq(
                        intent.getIntExtra("product_id", -1)
                        , intent.getIntExtra("skuId", -1)
                        , adrId
                        , 0
                        , "noremaker", couponId, data.product.number))
            }
        }
    }

    private lateinit var data: GoodsBuyBean
    private fun initData() {
        data = intent.getSerializableExtra("data") as GoodsBuyBean
        tvName1.text = data.product.name
        tvSpec.text = data.product.intro
        tvPrice.text = data.product.price
        tvMoney11.text = "${data.product.price.toDouble() * data.product.number.toInt()}"
        tvMoney.text = "${data.product.price.toDouble() * data.product.number.toInt()}"
        tvNumber.text = "x${data.product.number}"
        oldAllMoney = data.product.price.toDouble()
        listCoupon.addAll(data.coupons)
        tvCoupon.text = "${listCoupon.size} 张可用优惠券"
        adapterCoupon.notifyDataSetChanged()

        if (data.product.image.contains(",")) {
            ivImg.loadImage(data.product.image.substring(0, data.product.image.indexOf(",")))
        } else {
            ivImg.loadImage(data.product.image)
        }
        mPresenter.getDefAddress()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == 4444) {
            var dataAdr = data!!.getSerializableExtra("address") as AddressBean
            tvName.text = dataAdr.consignee
            tvPhone.text = dataAdr.mobile
            tvAddress.text = dataAdr.province + dataAdr.city + dataAdr.district + dataAdr.address
            tvAddAddress.visibility = View.INVISIBLE
            clAddress.visibility = View.VISIBLE
            adrId = dataAdr.address_id
            if (dataAdr.is_default == 1) {
                tvTag.visibility = View.VISIBLE
            } else {
                tvTag.visibility = View.GONE
            }
        }
    }
}
