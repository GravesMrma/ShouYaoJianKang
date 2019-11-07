package com.wuhanzihai.rbk.ruibeikang.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.just.agentweb.AgentWeb
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXTextObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.ArticleDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ArticleDetailReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.CollectAtrReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.LikeAtrReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.presenter.UnifiedWebPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ArticleDetailView
import kotlinx.android.synthetic.main.activity_unified_web.*
import org.jetbrains.anko.act
import org.jetbrains.anko.support.v4.act
import per.goweii.anylayer.AnyLayer
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.bumptech.glide.request.target.SimpleTarget
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.wuhanzihai.rbk.ruibeikang.common.GlideApp
import com.wuhanzihai.rbk.ruibeikang.utils.JavaUtils
import okhttp3.*
import java.io.*
import java.net.URL
import java.nio.ByteBuffer


class UnifiedWebActivity : BaseMvpActivity<UnifiedWebPresenter>(), ArticleDetailView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onColloectResult() {
        ivCollect.isSelected = !ivCollect.isSelected
    }

    override fun onLikeResult() {
        ivLike.isSelected = !ivLike.isSelected
        if (ivLike.isSelected) {
            ivLike.text = (ivLike.text.toString().toInt() + 1).toString()
        } else {
            ivLike.text = (ivLike.text.toString().toInt() - 1).toString()
        }

        var intent = Intent()
        intent.putExtra("like",ivLike.text.toString().toInt())
        setResult(4321, intent)
    }

    override fun onArticleDetailResult(result: ArticleDetailBean) {
        this.result = result
        ivLike.text = result.follow.toString()
        ivLike.isSelected = result.iszan == 1
        ivCollect.isSelected = result.isshouchan == 1
        tvText.text = result.title
        tvReadNum.text = result.click.toString()
        tvTime.text = result.publish_time
        llView.loadUrl(BaseConstant.BASE_URL + "api/Web/article?id=" + result.article_id)
    }

    private lateinit var result: ArticleDetailBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unified_web)

        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        val settings = llView.settings
        settings.javaScriptEnabled = false  // js jiaohu
        settings.setSupportZoom(false) //支持缩放，默认为true。是下面那个的前提。
        settings.builtInZoomControls = false //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.displayZoomControls = true //隐藏原生的缩放控件
        llView.webViewClient = WebViewClient()

        ivLike.onClick {
            mPresenter.likeAtr(LikeAtrReq(intent.getIntExtra("id", -1), 1))
        }
        ivCollect.onClick {
            mPresenter.CollectAtr(CollectAtrReq(intent.getIntExtra("id", -1), 1))
        }
        tvTitle.setShareAction {
            getBitmap(BaseConstant.BASE_URL + result.thumb)
            dialog.show()
        }
    }

    private fun initData() {
        mPresenter.articleDetail(ArticleDetailReq(intent.getIntExtra("id", -1)))
    }

    private val dialog by lazy {
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
        webpage.webpageUrl = "http://api.hcjiankang.com/api/Web/article?id=${intent.getIntExtra("id", -1)}"

//用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        val msg = WXMediaMessage(webpage)
        msg.title = result.title
        msg.description = "更多精彩请下载首要健康APP"

        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        }

        val baos = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val datas = JavaUtils.bmpToByteArray(bitmap,32000)
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
