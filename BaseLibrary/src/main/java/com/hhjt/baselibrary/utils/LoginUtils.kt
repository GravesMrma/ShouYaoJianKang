package com.hhjt.baselibrary.utils

import com.hhjt.baselibrary.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils

object LoginUtils {

    fun saveLoginStatus(status: Boolean) {
        AppPrefsUtils.putBoolean(BaseConstant.LOGIN_STATUS, status)
    }

    fun saveLoginStatus(status: Boolean, auth: String) {
        AppPrefsUtils.putBoolean(BaseConstant.LOGIN_STATUS, status)
        AppPrefsUtils.putString(BaseConstant.AUTH, auth)
    }

    fun saveLoginStatus(auth: String) {
        AppPrefsUtils.putString(BaseConstant.AUTH, auth)
    }

    fun saveLoginStatus(status: Boolean, auth: String,user_id: Int) {
        AppPrefsUtils.putBoolean(BaseConstant.LOGIN_STATUS, status)
        AppPrefsUtils.putString(BaseConstant.AUTH, auth)
        AppPrefsUtils.putInt(BaseConstant.USER_ID, user_id)
    }

    fun saveLoginStatus(user_id: Int) {
        AppPrefsUtils.putInt(BaseConstant.USER_ID, user_id)
    }


    fun getAuthId(): String {
        return AppPrefsUtils.getString(BaseConstant.AUTH)
    }

    fun getUserId(): Int {
        return AppPrefsUtils.getInt(BaseConstant.USER_ID)
    }

    fun getLoginStatus(): Boolean {
        return AppPrefsUtils.getBoolean(BaseConstant.LOGIN_STATUS)
    }

    fun saveRebateId(id: Int) {
        AppPrefsUtils.putInt(BaseConstant.REBATE_ID, id)
    }

    fun getRebateId(): Int {
        return AppPrefsUtils.getInt(BaseConstant.REBATE_ID)
    }
}