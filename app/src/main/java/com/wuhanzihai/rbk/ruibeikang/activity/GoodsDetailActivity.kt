package com.wuhanzihai.rbk.ruibeikang.activity

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.hhjt.baselibrary.utils.LoginUtils
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoader
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.AddCartReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.DoneCartReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GoodsDetailReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.presenter.GoodsDetailPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.GoodsDetailView
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.layout_specs.view.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import per.goweii.anylayer.AnimHelper
import per.goweii.anylayer.AnyLayer

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
            bannerList.add(BannerEntity(0, "", "", result.image[i], ""))
        }
        mBanner.setImageLoader(FrescoBannerLoader(false))
                .setImages(bannerList)
                .start()
        tvName.text = result.name
        tvPrice.text = result.price
        tvOldPrice.text = "¥${result.original_price}"
        tvOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        tvSellNum.text = "已热销:${result.sales}"

        webView.loadDataWithBaseURL(
                null,
                MyUtils.myUtils.htmlFormat(result.info),
                BaseConstant.MIME_TYPE,
                BaseConstant.ENCODING,
                null
        )

    }

    override fun onAddCartResult() {
        toast("添加购物车成功")
    }

    private var id = 0
    private var bannerList = mutableListOf<BannerEntity>()
    private lateinit var result: GoodsDetailBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        id = intent.getIntExtra("id", 0)


        initView()

        initData()
    }

    private fun initView() {

        val settings = webView.settings
        settings.javaScriptEnabled = false  // js jiaohu
        settings.setSupportZoom(false) //支持缩放，默认为true。是下面那个的前提。
        settings.builtInZoomControls = false //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.displayZoomControls = true //隐藏原生的缩放控件

        tvAddCart.onClick {
            mPresenter.addCart(AddCartReq(result.product_id, skuId))
        }

        tvShopCart.onClick {
            startActivity<ShoppingCartActivity>()
        }

        tvBuy.onClick {
            dialog.show()
        }
    }

    private fun initData() {
        mPresenter.goodsDetail(GoodsDetailReq(id))
    }

    private lateinit var colorAdapter: TagAdapter<PropertydataItem>
    private lateinit var colorList: MutableList<PropertydataItem>
    private var colorId = 0

    private lateinit var sizeAdapter: TagAdapter<PropertydatavalueItem>
    private lateinit var sizeList: MutableList<PropertydatavalueItem>
    private var sizeId = 0

    private lateinit var skuAdapter: TagAdapter<SkuItem>
    private lateinit var skuList: MutableList<SkuItem>
    private var skuId = 0


    private val dialog by lazy {
        var view = layoutInflater.inflate(R.layout.layout_specs, null)
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

        view.rvView1.setOnTagClickListener { view, position, parent ->
            colorId = colorList[position].pid
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
        view.rvView2.setOnTagClickListener { view, position, parent ->
            sizeId = sizeList[position].pid
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
                        .onClick(R.id.ivClose) { anyLayer, v ->
                            anyLayer.dismiss()
                        }
                        .onClick(R.id.btCommit) { anyLayer, v ->
                            if (colorId == 0 || sizeId == 0) {
                                toast("请选择规格")
                            } else {
                                for (item in result.sku) {
                                    if (item.properties == "${colorId}:${sizeId}"){
                                        skuId = item.sku_id
                                    }
                                }
                                anyLayer.dismiss()
                            }
                        }
        anyLayer
    }
}
