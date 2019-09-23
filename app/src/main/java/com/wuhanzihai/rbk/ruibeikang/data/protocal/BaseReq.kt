package com.wuhanzihai.rbk.ruibeikang.data.protocal

import android.util.Log
import com.google.gson.Gson
import com.hhjt.baselibrary.common.BaseConstant
import java.net.URLEncoder

open class BaseReq {
    var sign: String? = null

    fun formatParam(obj: Any): String {
        val gson = Gson()
        var str = gson.toJson(obj)
        str = str.replace("\\{".toRegex(), "").replace("\\}".toRegex(), "").replace("\"", "")
                .replace(":", "=").replace(",", "&")

        str = str.replace("测试地址".toRegex(), "%E6%B5%8B%E8%AF%95%E5%9C%B0%E5%9D%80")
                .replace("武汉市".toRegex(), "%E6%AD%A6%E6%B1%89%E5%B8%82")
                .replace("张三", "%E5%BC%A0%E4%B8%89")
                .replace("东西湖区", "%E4%B8%9C%E8%A5%BF%E6%B9%96%E5%8C%BA")
                .replace("湖北省", "%E6%B9%96%E5%8C%97%E7%9C%81")
        return str
    }

    fun getKey(): String {
        return BaseConstant.PARAM_KEY
    }
}