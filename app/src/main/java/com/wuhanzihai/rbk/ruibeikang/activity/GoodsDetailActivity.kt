package com.wuhanzihai.rbk.ruibeikang.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoader
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemCoupon
import com.wuhanzihai.rbk.ruibeikang.presenter.GoodsDetailPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.GoodsDetailView
import com.wuhanzihai.rbk.ruibeikang.utils.JavaUtils
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.layout_specs.view.*
import okhttp3.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.toast
import per.goweii.anylayer.AnimHelper
import per.goweii.anylayer.AnyLayer
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class GoodsDetailActivity : BaseMvpActivity<GoodsDetailPresenter>(), GoodsDetailView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule())
                .build().inject(this)
        mPresenter.mView = this
    }

    override fun onGoodsDetailResult(result: GoodsDetailBean) {
        this.result = result
        for (i in 0 until result.image.size) {
            bannerList.add(BannerEntity(0, "", 0, result.image[i], 0, ""))
        }
        mBanner.setImageLoader(FrescoBannerLoader(false))
                .setImages(bannerList)
                .start()
        tvName.text = result.name
        tvPrice.text = result.price
        tvOldPrice.text = "¥${result.original_price}"
        tvOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        list.addAll(result.couponlist)

        for (couponBean in list) {
            if (couponBean.usercouponcount > 0) {
                when (couponBean.type) {
                    1 -> {
                        listCouponTag.add("现金抵扣券 ¥" + couponBean.face_money.replace(".00", ""))
                    }
                    2 -> {
                        listCouponTag.add("满${couponBean.limit_money.replace(".00", "")}减${couponBean.face_money.replace(".00", "")}")
                    }
                    3 -> {
                        listCouponTag.add("现金折扣券" + couponBean.face_money.replace(".00", "") + "折")
                    }
                    4 -> {
                        listCouponTag.add("会员礼品抵扣券")
                    }
                }
            }
        }

        if (list.isEmpty()) {
            tvIsCoupon.visibility = View.VISIBLE
            rvCoupon.visibility = View.GONE
        } else {
            if (listCouponTag.isEmpty()) {
                tvIsCoupon.visibility = View.VISIBLE
                rvCoupon.visibility = View.GONE
                tvIsCoupon.text = "有优惠券可领"
            } else {
                tvIsCoupon.visibility = View.GONE
                rvCoupon.visibility = View.VISIBLE
                adapterCouponTag.notifyDataSetChanged()
            }
        }

        if (result.sales == 0) {
            if (id == 146 ||
                    id == 147 ||
                    id == 148 ||
                    id == 152 ||
                    id == 154 ||
                    id == 155 ||
                    id == 156 ||
                    id == 157 ||
                    id == 158) {
                tvSellNum.text = "已领取:${result.counterfeit_sales}"
            } else {
                tvSellNum.text = "已热销:${result.counterfeit_sales}"
            }
        } else {
            if (id == 146 ||
                    id == 147 ||
                    id == 148 ||
                    id == 152 ||
                    id == 154 ||
                    id == 155 ||
                    id == 156 ||
                    id == 157 ||
                    id == 158) {
                tvSellNum.text = "已领取:${result.sales}"
            } else {
                tvSellNum.text = "已热销:${result.sales}"
            }
        }

        webView.loadDataWithBaseURL(
                null,
                MyUtils.instance.htmlFormat(result.info),
                BaseConstant.MIME_TYPE,
                BaseConstant.ENCODING,
                null
        )
    }

    override fun onTakeCouponResult() {
        mPresenter.goodsDetailRe(GoodsDetailReq(id))
    }

    override fun onGoodsDetailResultRe(result: GoodsDetailBean) {
        list.clear()
        list.addAll(result.couponlist)
        adapter.notifyDataSetChanged()
        listCouponTag.clear()
        for (couponBean in list) {
            if (couponBean.usercouponcount > 0) {
                when (couponBean.type) {
                    1 -> {
                        listCouponTag.add("现金抵扣券 ¥" + couponBean.face_money.replace(".00", ""))
                    }
                    2 -> {
                        listCouponTag.add("满${couponBean.limit_money.replace(".00", "")}减${couponBean.face_money.replace(".00", "")}")
                    }
                    3 -> {
                        listCouponTag.add("现金折扣券" + couponBean.face_money.replace(".00", "") + "折")
                    }
                    4 -> {
                        listCouponTag.add("会员礼品抵扣券")
                    }
                }
            }
        }
        adapterCouponTag.notifyDataSetChanged()

        if (list.isEmpty()) {
            tvIsCoupon.visibility = View.VISIBLE
            rvCoupon.visibility = View.GONE
        } else {
            if (listCouponTag.isEmpty()) {
                tvIsCoupon.visibility = View.VISIBLE
                rvCoupon.visibility = View.GONE
                tvIsCoupon.text = "有优惠券可领"
            } else {
                tvIsCoupon.visibility = View.GONE
                rvCoupon.visibility = View.VISIBLE
                adapterCouponTag.notifyDataSetChanged()
            }
        }
    }

    override fun onAddCartResult() {
        toast("添加购物车成功")
        if (isBuy) {
            mPresenter.getShoppingCartList()
        }
    }

    override fun onBuyResult(result: GoodsBuyBean) {
        startActivity<SureOrderBuyActivity>("data" to result
                , "product_id" to this.result.product_id
                , "skuId" to skuId)
    }

    override fun onCartNumberResult(result: CartNumberBean) {
        if (result.count > 0) {
            tvCartNumber.visibility = View.VISIBLE
            tvCartNumber.text = "${result.count}"
        } else {
            tvCartNumber.visibility = View.GONE
        }
    }

    private var id = 0
    private var bannerList = mutableListOf<BannerEntity>()
    private lateinit var result: GoodsDetailBean
    private var isBuy = false

    private lateinit var listCouponTag: MutableList<String>
    private lateinit var adapterCouponTag: BaseQuickAdapter<String, BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)
        StatusBarUtil.setTranslucentForImageView(act, 0, null)
        StatusBarUtil.setLightMode(act)

        id = intent.getIntExtra("id", 0)

        if (id == 146 ||
                id == 147 ||
                id == 148 ||
                id == 152 ||
                id == 154 ||
                id == 155 ||
                id == 156 ||
                id == 157 ||
                id == 158) {
            tvTagGS.text = "限新会员领取"
            tvAddCart.visibility = View.GONE
            tvShopCart.visibility = View.GONE
            tvBuy.text = "立即领取"
        }

        initView()

        initData()
    }

    private fun initView() {
        val settings = webView.settings
        settings.javaScriptEnabled = false  // js jiaohu
        settings.setSupportZoom(false) //支持缩放，默认为true。是下面那个的前提。
        settings.builtInZoomControls = false //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.displayZoomControls = true //隐藏原生的缩放控件

        list = mutableListOf()
        adapter = object : BaseQuickAdapter<GoodsCouponBean, BaseViewHolder>(R.layout.item_coupon, list) {
            override fun convert(helper: BaseViewHolder?, item: GoodsCouponBean?) {
                helper!!.setText(R.id.tvText, item!!.name)
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

                if (item.most_have > item.usercouponcount) {
                    helper.setText(R.id.tvState, "立即兑换")
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

                    helper.setBackgroundRes(R.id.rlView, R.mipmap.ic_coupon_bg)
                    helper.setOnClickListener(R.id.tvState) {
                        mPresenter.takeExchangeCoupons(CouponIdReq(list[helper.layoutPosition].coupon_id))
                    }
                } else {
                    helper.setText(R.id.tvState, "已兑换")
                    helper.setTextColor(R.id.tvState, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvL1tag1, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvCash, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvL1tag11, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvL2tag1, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvCoupon, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvLimit, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvDiscount, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvL3tag1, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvL3tag11, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvL4tag1, ContextCompat.getColor(act, R.color.gray_99))
                    helper.setBackgroundRes(R.id.rlView, R.mipmap.ic_coupon_bg_s)
                }

                helper.setOnClickListener(R.id.tvText1) {
                    item.isShow = !item.isShow
                    if (item.isShow) {
                        helper.getView<RelativeLayout>(R.id.rlDesc).visibility = View.VISIBLE
                    } else {
                        helper.getView<RelativeLayout>(R.id.rlDesc).visibility = View.GONE
                    }
                }
            }
        }
        dialogCoupon.getView<RecyclerView>(R.id.rvView).adapter = adapter
        dialogCoupon.getView<RecyclerView>(R.id.rvView).layoutManager = GridLayoutManager(act, 1)
        dialogCoupon.getView<RecyclerView>(R.id.rvView).addItemDecoration(DividerItemCoupon(act))
        adapter.emptyView = getEmptyView(act, "暂无可用优惠券")


        listCouponTag = mutableListOf()
        adapterCouponTag = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_coupon_tag, listCouponTag) {
            override fun convert(helper: BaseViewHolder?, item: String?) {
                helper!!.setText(R.id.tvText, item!!)
            }
        }
        rvCoupon.adapter = adapterCouponTag
        rvCoupon.layoutManager = GridLayoutManager(act, 1, RecyclerView.HORIZONTAL, false)

        tvAddCart.onClick {
            // 是否是实物商品
            isBuy = false
            if (result.type == "0") {
                // 判断 是否有规格   有 就出弹窗 没有 就弹窗
                if (result.propertydata != null) {
                    if (result.propertydata.isNotEmpty()) {
                        dialog.show()
                    } else {
                        mPresenter.addCart(AddCartReq(result.product_id, skuId))
                    }
                } else {
                    mPresenter.addCart(AddCartReq(result.product_id, skuId))
                }
            } else {
                toast("该商品不能加入购物车")
            }
        }
        tvShopCart.onClick {
            startActivity<ShoppingCartActivity>()
        }
        tvBuy.onClick {
            // 先判断有没有规格  如果有规格
            isBuy = true
            if (result.propertydata != null) {
                if (result.propertydata.isNotEmpty()) {
                    if (skuId == 0) {
                        dialog.show()
                    } else {
                        mPresenter.buyGoods(BuyGoodsReq(result.product_id, skuId))
                    }
                } else {
                    mPresenter.buyGoods(BuyGoodsReq(result.product_id, skuId))
                }
            } else {
                mPresenter.buyGoods(BuyGoodsReq(result.product_id, skuId))
            }
        }
        llView1.onClick {
            //            startActivity<CouponActivity>()
        }
        ivBack.onClick {
            finish()
        }
        ivShare.onClick {
            getBitmap(BaseConstant.BASE_URL + result.image.first())
            dialogShare.show()
        }
        ivCollect.onClick {
            toast("收藏")
        }
        tvKefu.onClick {
            startActivity<StandardWebActivity>("title" to "在线客服"
                    , "data" to "http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT35316404&lng=cn")
        }
        llView1.onClick {
            dialogCoupon.show()
            adapter.notifyDataSetChanged()
        }

        tvTitle.background.mutate().alpha = 0
        fake_status_bar.background.mutate().alpha = 0
        var index = MyUtils.getWidth(act)
        var sy = 0
        nsView.viewTreeObserver.addOnScrollChangedListener {
            sy = nsView.scrollY
            if (sy in 0..index) {
                var aaa = ((sy.toFloat() / index.toFloat()) * 255).toInt()
                Log.e("透明质", aaa.toString())
                tvTitle.background.mutate().alpha = aaa
                fake_status_bar.background.mutate().alpha = aaa

            } else {
                tvTitle.background.mutate().alpha = 255
                fake_status_bar.background.mutate().alpha = 255
            }
        }
    }

    private fun initData() {
        mPresenter.goodsDetail(GoodsDetailReq(id))
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getCartNumber()
    }

    private lateinit var colorAdapter: TagAdapter<PropertydataItem>
    private lateinit var colorList: MutableList<PropertydataItem>
    private var colorId = 0
    private var colorName = ""

    private lateinit var sizeAdapter: TagAdapter<PropertydatavalueItem>
    private lateinit var sizeList: MutableList<PropertydatavalueItem>
    private var sizeId = 0
    private var sizeName = ""

    private lateinit var skuAdapter: TagAdapter<SkuItem>
    private lateinit var skuList: MutableList<SkuItem>
    private var skuId = 0

    private val dialog by lazy {
        var view = layoutInflater.inflate(R.layout.layout_specs, null)
        view.tvPrice.text = result.price
        if (result.image.isNotEmpty()) view.ivImg.loadImage(result.image.first())

        view.tvStock.text = "库存: ${result.price}件"
        colorList = mutableListOf()
        colorList.addAll(result.propertydata)
        colorAdapter = object : TagAdapter<PropertydataItem>(colorList) {
            override fun getView(parent: FlowLayout, position: Int, t: PropertydataItem): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_spces_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t.name
                return tv
            }
        }
        view.rvView1.adapter = colorAdapter

        view.rvView1.setOnTagClickListener { _, position, parent ->
            colorId = colorList[position].pid
            colorName = colorList[position].name
            for (item in result.sku) {
                Log.e("测试", item.properties + "aaaa" + "${colorId}:${sizeId}")
                if (item.properties == "${colorId}:${sizeId}") {
                    skuId = item.sku_id
                    view.tvPrice.text = item.price
                    view.tvSpecs.text = "$colorName - $sizeName"
                    view.ivImg.loadImage(item.skuimg)
                }
            }
            return@setOnTagClickListener false
        }
        sizeList = mutableListOf()
        sizeList.addAll(colorList.first().propertydatavalue)
        sizeAdapter = object : TagAdapter<PropertydatavalueItem>(sizeList) {
            override fun getView(parent: FlowLayout, position: Int, t: PropertydatavalueItem): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_spces_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t.valname
                return tv
            }
        }
        view.rvView2.adapter = sizeAdapter
        view.rvView2.setOnTagClickListener { _, position, parent ->
            sizeId = sizeList[position].vid
            sizeName = sizeList[position].valname
            Log.e("测试ID", sizeId.toString())
            for (item in result.sku) {
                Log.e("测试", item.properties + "aaaa" + "${colorId}:${sizeId}")
                if (item.properties == "${colorId}:${sizeId}") {
                    skuId = item.sku_id
                    view.tvPrice.text = item.price
                    view.tvSpecs.text = "$colorName - $sizeName"
                    view.ivImg.loadImage(item.skuimg)
                }
            }
            return@setOnTagClickListener false
        }

        skuList = mutableListOf()
        skuAdapter = object : TagAdapter<SkuItem>(skuList) {
            override fun getView(parent: FlowLayout, position: Int, t: SkuItem): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_spces_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t.point_price
                return tv
            }
        }
        view.rvView3.adapter = skuAdapter

        val anyLayer =
                AnyLayer.with(act)
                        .contentView(view)
                        .gravity(Gravity.BOTTOM)
                        .backgroundResource(R.color.clarity_40)
                        .cancelableOnTouchOutside(true)
                        .contentAnim(object : AnyLayer.IAnim {
                            override fun inAnim(target: View?): Long {
                                AnimHelper.startBottomAlphaInAnim(target, 350)
                                return 350
                            }

                            override fun outAnim(target: View?): Long {
                                AnimHelper.startBottomOutAnim(target, 350)
                                return 350
                            }
                        })
                        .onClick(R.id.btCommit) { anyLayer, v ->
                            if (colorId == 0 || sizeId == 0) {
                                toast("请选择规格")
                            } else {
                                for (item in result.sku) {
                                    if (item.properties == "${colorId}:${sizeId}") {
                                        skuId = item.sku_id
                                        if (isBuy) {
                                            mPresenter.buyGoods(BuyGoodsReq(result.product_id, skuId))
                                        } else {
                                            mPresenter.addCart(AddCartReq(result.product_id, skuId))
                                        }
                                    }
                                }
                                anyLayer.dismiss()
                            }
                        }
                        .onClick(R.id.ivClose) { anyLayer, v ->
                            anyLayer.dismiss()
                        }
                        .onClick(R.id.spView) { anyLayer, v ->
                            anyLayer.dismiss()
                        }
        anyLayer
    }

    private val dialogShare by lazy {
        val anyLayer =
                AnyLayer.with(act)
                        .contentView(R.layout.layout_share)
                        .gravity(Gravity.BOTTOM)
                        .backgroundResource(R.color.clarity_40)
                        .cancelableOnTouchOutside(true)
                        .onClick(R.id.tvWeChat) { anyLayer, v ->
                            shareWx(1)
                            anyLayer.dismiss()
                        }.onClick(R.id.tvFriend) { anyLayer, v ->
                            shareWx(2)
                            anyLayer.dismiss()
                        }.onClick(R.id.tvCancel) { anyLayer, v ->
                            anyLayer.dismiss()
                        }
        anyLayer
    }


    private lateinit var list: MutableList<GoodsCouponBean>
    private lateinit var adapter: BaseQuickAdapter<GoodsCouponBean, BaseViewHolder>

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
        anyLayer
    }

    private val api by lazy {
        WXAPIFactory.createWXAPI(act, BaseConstant.APP_WXID, true)
    }
    private var bitmap: Bitmap? = null
    private fun shareWx(type: Int) {
        api.registerApp(BaseConstant.APP_WXID)
        //初始化一个WXWebpageObject，填写url
        val webpage = WXWebpageObject()
        webpage.webpageUrl = "http://www.shouyaojiankang.com/downloadApp.html"

//用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        val msg = WXMediaMessage(webpage)
        msg.title = result.name
        msg.description = "更多精彩请下载首要健康APP"

        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        }
        val baos = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val datas = JavaUtils.bmpToByteArray(bitmap, 32000)
        msg.thumbData = datas

//构造一个Req
        val req = SendMessageToWX.Req()
        req.transaction = System.currentTimeMillis().toString()
        req.message = msg
        if (type == 1) {
            req.scene = SendMessageToWX.Req.WXSceneSession
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline
        }
        api.sendReq(req)
    }

    private fun getBitmap(url: String) {
        var httpClient = OkHttpClient()
        var request = Request.Builder().url(url)
                .build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                var file = File(externalCacheDir, url.substring(url.lastIndexOf("/") + 1))
                if (!file.exists()) {
                    file.createNewFile()
                }
                bitmap = JavaUtils().getFile(response, file)
            }
        })
    }
}
