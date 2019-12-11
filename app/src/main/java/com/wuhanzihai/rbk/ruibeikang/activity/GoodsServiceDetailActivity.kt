package com.wuhanzihai.rbk.ruibeikang.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoader
import com.wuhanzihai.rbk.ruibeikang.data.entity.BannerEntity
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsBuyBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.BuyGoodsReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GoodsDetailReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.presenter.GoodsDetailPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.GoodsDetailView
import com.wuhanzihai.rbk.ruibeikang.utils.JavaUtils
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import kotlinx.android.synthetic.main.activity_goods_service_detail.*
import okhttp3.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import per.goweii.anylayer.AnyLayer
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class GoodsServiceDetailActivity : BaseMvpActivity<GoodsDetailPresenter>(), GoodsDetailView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule())
                .build().inject(this)
        mPresenter.mView = this
    }

    override fun onGoodsDetailResult(result: GoodsDetailBean) {
        this.result = result
        for (i in 0 until result.image.size) {
            bannerList.add(BannerEntity(0, "", 0, result.image[i], 1,""))
        }
        mBanner.setImageLoader(FrescoBannerLoader(false))
                .setImages(bannerList)
                .start()
        tvName.text = result.name
        tvPrice.text = result.price
        tvOldPrice.text = "¥${result.original_price}"
        tvOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        if (result.sales == 0){
            tvSellNum.text = "已预订:${result.counterfeit_sales}"
        }else{
            tvSellNum.text = "已预订:${result.sales}"
        }

        webView.loadDataWithBaseURL(
                null,
                MyUtils.instance.htmlFormat(result.info),
                BaseConstant.MIME_TYPE,
                BaseConstant.ENCODING,
                null
        )
    }

    override fun onAddCartResult() {

    }

    override fun onBuyResult(result: GoodsBuyBean) {
        startActivity<SureServiceOrderActivity>("data" to result
                ,"product_id" to this.result.product_id
                ,"skuId" to 0)
    }

    private var id = 0
    private var bannerList = mutableListOf<BannerEntity>()
    private lateinit var result: GoodsDetailBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_service_detail)
        StatusBarUtil.setTranslucentForImageView(act, 0, null)
        StatusBarUtil.setLightMode(act)

        id = intent.getIntExtra("id", 0)

        initView()

        initData()
    }

    private fun initView(){
        val settings = webView.settings
        settings.javaScriptEnabled = false  // js jiaohu
        settings.setSupportZoom(false) //支持缩放，默认为true。是下面那个的前提。
        settings.builtInZoomControls = false //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.displayZoomControls = true //隐藏原生的缩放控件

        tvBuy.onClick {
            mPresenter.buyGoods(BuyGoodsReq(result.product_id, 0))
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

        tvTitle.background.mutate().alpha = 0
        fake_status_bar.background.mutate().alpha = 0
        var index =MyUtils.getWidth(act)
        var sy = 0
        nsView.viewTreeObserver.addOnScrollChangedListener {
            sy = nsView.scrollY
            if (sy in 0..index) {
                var aaa = ((sy.toFloat() / index.toFloat()) * 255).toInt()
                tvTitle.background.mutate().alpha = aaa
                fake_status_bar.background.mutate().alpha = aaa

            } else {
                tvTitle.background.mutate().alpha = 255
                fake_status_bar.background.mutate().alpha = 255
            }
        }
    }

    private fun initData(){
        mPresenter.goodsDetail(GoodsDetailReq(id))
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
    private val api by lazy {
        WXAPIFactory.createWXAPI(act, BaseConstant.APP_WXID, true)
    }
    private var bitmap: Bitmap? = null
    private fun shareWx(type: Int) {
        api.registerApp(BaseConstant.APP_WXID)
        //初始化一个WXWebpageObject，填写url
        val webpage = WXWebpageObject()
        webpage.webpageUrl = "http://www.hcjiankang.com/home/index/downloadApp"

//用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        val msg = WXMediaMessage(webpage)
        msg.title = result.name
        msg.description = "更多精彩请下载首要健康APP"

        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        }
        val baos = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val datas = JavaUtils.bmpToByteArray(bitmap,23000)
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
