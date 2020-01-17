package com.wuhanzihai.rbk.ruibeikang.wxapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hhjt.baselibrary.common.BaseConstant
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.OrderActivity
import com.wuhanzihai.rbk.ruibeikang.activity.PayResultActivity
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class WXPayEntryActivity : AppCompatActivity(), IWXAPIEventHandler {
    override fun onResp(p0: BaseResp?) {
        Log.e("微信回调onResp", p0!!.toString())
        Log.e("微信回调onResp", p0.type.toString())
        Log.e("微信回调onResp", p0.errCode.toString())

        if (p0.errCode == 0){
            startActivity<PayResultActivity>()
            finish()
        }else if (p0.errCode == -1){
            startActivity<OrderActivity>()
            finish()
        }else{
            toast("支付失败")
            finish()
        }
    }

    override fun onReq(p0: BaseReq?) {
        Log.e("微信回调onReq", p0!!.toString())
        Log.e("微信回调onReq", p0.type.toString())
    }

    private val api by lazy {
        val api = WXAPIFactory.createWXAPI(act, BaseConstant.APP_WXID,true)
        api.registerApp(BaseConstant.APP_WXID)
        api
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_wxpay_entry)
        api.handleIntent(intent, this)

    }
}
