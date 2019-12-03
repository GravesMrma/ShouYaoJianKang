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
class AddArchivesReq(name: String, birthday: String, connections: String, sex: Int) : BaseReq() {
    private var birthday = birthday
    private var connections = URLEncoder.encode(connections,"UTF-8")
    private var name = URLEncoder.encode(name,"UTF-8")
    private var sex = sex
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()
    private var user_id = LoginUtils.getUserId()

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
        this.connections = URLDecoder.decode(this.connections,"UTF-8")
        this.name = URLDecoder.decode(this.name,"UTF-8")
    }
}
