package com.wuhanzihai.rbk.ruibeikang.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.utils.LoginUtils
import com.jaeger.library.StatusBarUtil
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
import per.goweii.anylayer.AnyLayer
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class ShareHealthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_health)
        StatusBarUtil.setTranslucentForImageView(act, 0, null)

        initView()

        initData()
    }

    private fun initView() {
        ivBack.onClick {
            finish()
        }

        ivShare.onClick {
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
        msg.title = "马云:下一个风口【大健康】，首要健康APP启动3.88亿巨额补贴！"
        msg.description = "怕生病,上首要健康APP,管吃、管睡、管运动、管情绪、管健康"

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
