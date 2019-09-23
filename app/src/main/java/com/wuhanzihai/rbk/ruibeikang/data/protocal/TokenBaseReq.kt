package com.wuhanzihai.rbk.ruibeikang.data.protocal

import android.util.Log
import com.google.gson.Gson
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.utils.LoginUtils
import java.net.URLEncoder

open class TokenBaseReq {
    private var token = LoginUtils.getAuthId()

}