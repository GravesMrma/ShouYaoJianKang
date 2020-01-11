package com.wuhanzihai.rbk.ruibeikang.data.protocal

import android.util.Log
import com.google.gson.Gson
import com.hhjt.baselibrary.common.BaseConstant

open class BaseReq {
    var sign: String? = null

    fun formatParam(obj: Any): String {
        val gson = Gson()
        var str = gson.toJson(obj)
        str = str.replace("\\{".toRegex(), "").replace("\\}".toRegex(), "").replace("\"", "")
                .replace(":", "=").replace(",", "&")
        return str
    }

    fun getKey(): String {
        return BaseConstant.PARAM_KEY
    }
}