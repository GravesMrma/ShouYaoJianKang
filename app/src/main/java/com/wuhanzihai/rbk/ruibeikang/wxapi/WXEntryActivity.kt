package com.wuhanzihai.rbk.ruibeikang.wxapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hhjt.baselibrary.common.BaseConstant
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import org.jetbrains.anko.toast

class WXEntryActivity : AppCompatActivity(), IWXAPIEventHandler {
    override fun onResp(p0: BaseResp?) {
        if (p0!!.errCode == 0){
            if (p0.type == 1){
                // 登录回调
                toast("登录授权成功")
                finish()
            }
            if (p0.type == 2){
                // 分享回调
                toast("分享成功")
                finish()
            }
        }

        Log.e("微信回调onResp", p0!!.toString())
        Log.e("微信回调onResp", p0.type.toString())
    }

    override fun onReq(p0: BaseReq?) {
        Log.e("微信回调onReq", p0!!.toString())
        Log.e("微信回调onReq", p0.type.toString())
    }

    private var api: IWXAPI? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        api = WXAPIFactory.createWXAPI(this, BaseConstant.APP_WXID)
        api!!.handleIntent(intent, this)
    }
}
