package com.wuhanzihai.rbk.ruibeikang.data.protocal

import android.util.Log
import com.hhjt.baselibrary.utils.LoginUtils
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * Created by wx on 2018/7/10
 */
class AddQuestionReq(content: String, order_id: String) : BaseReq() {
    var content = URLEncoder.encode(content, "UTF-8")
    var order_id = order_id
    var time: String = System.currentTimeMillis().toString()
    var token = LoginUtils.getAuthId()
    var user_id = LoginUtils.getUserId()

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
        this.content = URLDecoder.decode(this.content, "UTF-8")
    }
}
