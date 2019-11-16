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

        const val SERVER_ADDRESS = BASE_URL + "api/"

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
        const val BRACELET_NAME = "auyf12gqac"

        // 手环Mac
        const val BRACELET_MAC = "auyfgqac"

        const val BRACELET_TODAY_STEP = "aiuykjsahca"
        const val BRACELET_TODAY_CAL = "zuiqwmxhaus"

        const val AUTH = "auth"
        //微信 AppID
        const val APP_WXID = "wx2c21a8a5abc71e19"
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

        const val FAHJASJK = "97b4b70JASDc1f"

        //血压计型号
        const val BP_DEVICE_NAME = "BP826"
        //血糖仪型号
        const val GLU_DEVICE_NAME = "Yasee_GLM76"

        const val BLOODPRE_DATA = "xueyashuju"

        const val BLOODSUG_DATA = "xyeyangshu"

        const val BODYFAT_DATA = "tizhongdeshuju"

        const val STEP_DATA = "suijibushu"

        var COUNT_REAL_TIME = 0L
        var COUNT_TIME = 0L

        const val REBATE_INFO = "rebateinfo"
        const val REBATE_ID = "rebateinfoid"

    }
}
