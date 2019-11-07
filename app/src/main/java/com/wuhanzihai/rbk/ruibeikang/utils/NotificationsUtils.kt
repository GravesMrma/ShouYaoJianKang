package com.wuhanzihai.rbk.ruibeikang.utils

import android.app.AppOpsManager
import android.content.pm.ApplicationInfo
import android.content.Context.APP_OPS_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.annotation.SuppressLint
import android.content.Context
import android.R
import android.widget.TextView
import android.support.v4.content.ContextCompat.startActivity
import com.wuhanzihai.rbk.ruibeikang.activity.MainActivity
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION
import android.net.Uri.fromParts
import android.os.Build
import android.view.View


class NotificationsUtils {

    companion object {

        private val CHECK_OP_NO_THROW = "checkOpNoThrow"
        private val OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION"

        fun isNotificationEnabled(context: Context): Boolean {

            val mAppOps = context.getSystemService(APP_OPS_SERVICE) as AppOpsManager
            val appInfo = context.getApplicationInfo()
            val pkg = context.getApplicationContext().getPackageName()
            val uid = appInfo.uid
            var appOpsClass: Class<*>? = null

            /* Context.APP_OPS_MANAGER */
            try {
                appOpsClass = Class.forName(AppOpsManager::class.java.name)

                val checkOpNoThrowMethod = appOpsClass!!.getMethod(CHECK_OP_NO_THROW,
                        Integer.TYPE, Integer.TYPE, String::class.java)

                val opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION)
                val value = opPostNotificationValue.get(Int::class.java) as Int

                return checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) as Int == AppOpsManager.MODE_ALLOWED
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

        fun toSetting(context: Context) {
            val localIntent = Intent()
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                localIntent.data = Uri.fromParts("package", context.packageName, null)
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.action = Intent.ACTION_VIEW

                localIntent.setClassName("com.android.settings",
                        "com.android.settings.InstalledAppDetails")

                localIntent.putExtra("com.android.settings.ApplicationPkgName",
                        context.packageName)
            }
            context.startActivity(localIntent)
        }
    }
}