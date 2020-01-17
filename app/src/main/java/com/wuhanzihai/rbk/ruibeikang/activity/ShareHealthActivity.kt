package com.wuhanzihai.rbk.ruibeikang.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.utils.LoginUtils
import com.jaeger.library.StatusBarUtil
import com.just.agentweb.AgentWeb
import com.orhanobut.hawk.Hawk
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateBean
import com.wuhanzihai.rbk.ruibeikang.utils.JavaUtils
import kotlinx.android.synthetic.main.activity_share_health.*
import okhttp3.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import per.goweii.anylayer.AnyLayer
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class ShareHealthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_health)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        AgentWeb.with(this)
                .setAgentWebParent(llView as LinearLayout, LinearLayout.LayoutParams(-1, -1) as ViewGroup.LayoutParams)
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("http://api.hcjiankang.com/api/Web/article?id=860")

        btCommit1.onClick {
            startActivity<CouponActivity>()
        }

        btCommit.onClick {
            dialog.show()
        }
    }

    private fun initData() {

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
        var url = "http://api.hcjiankang.com/wechat/index/share?share_id=${LoginUtils.getRebateId()}"
        webpage.webpageUrl = url

//用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        val msg = WXMediaMessage(webpage)
        msg.title = "299元成为首要健康会员即可获得万元健康大礼"
        msg.description = "此链接为首要健康平台官方发布，每一项会员权益均为真实有效！"

        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_share_money)
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

//    private fun getBitmap(url: String) {
//        var httpClient = OkHttpClient()
//        var request = Request.Builder().url(url)
//                .build()
//        httpClient.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {}
//            override fun onResponse(call: Call, response: Response) {
//                var file = File(externalCacheDir, url.substring(url.lastIndexOf("/") + 1))
//                if (!file.exists()) {
//                    file.createNewFile()
//                }
//                bitmap = JavaUtils().getFile(response, file)
//            }
//        })
//    }
}
