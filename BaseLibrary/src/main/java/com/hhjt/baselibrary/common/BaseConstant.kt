package com.hhjt.baselibrary.common

/*
    基础常量
 */
class BaseConstant {
    companion object {

        const val MIME_TYPE = "text/html; charset=utf-8"
        const val ENCODING = "utf-8"
        const val STYLE = "style=\" max-width: 100%; min-width: 100px;\""

        //本地服务器地址
        const val BASE_URL = "http://api.hcjiankang.com/"
//        const val BASE_URL = "http://xidebao.xiangmu1.hhjtwl.com"
//        const val BASE_URL = "http://47.103.70.42:88"

        const val SERVER_ADDRESS = BASE_URL+"api/"

        //        const val IMAGE_ADDRESS = "http://pn9ku8m83.bkt.clouddn.com/"
        const val IMAGE_ADDRESS = "$BASE_URL/"

        //SP表名
        const val TABLE_PREFS = "zhicheng"

        //登录状态
        const val LOGIN_STATUS = "login_status"

        // 用户ID
        const val USER_ID = "ouyasfouask"

        // 手环数据ID
        const val BRACELET_ID = "fahsasjnasid"
        // 手环Mac
        const val BRACELET_MAC = "auyfgqac"

        const val BRACELET_TODAY_STEP = "aiuykjsahca"
        const val BRACELET_TODAY_CAL = "zuiqwmxhaus"

        const val AUTH = "auth"
        //微信appid
//        const val APP_ID = "wx8658139886055417"
        const val APP_ID = "wx16145410bf10ba75"
        //客服key
        const val SOBOT_APP_KEY = "991f9e4dc883485c8f13fab2b141cae7"

        const val IS_FIRST = "first"
        //院区名称
        const val HOSPITAL_NAME = "hospital_name"
        //院区id
        const val HOSPITAL_ID = "hospital_id"

        const val PROVINCE = "province"

        const val VOICE_CACHE = "voice_cache"

        const val PARAM_KEY = "Firstone-Healthy"



        const val BUGLY_APP_ID = "97b4b70c1f"
    }
}
