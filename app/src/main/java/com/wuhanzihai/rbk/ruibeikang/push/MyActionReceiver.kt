package com.wuhanzihai.rbk.ruibeikang.push

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import cn.jpush.android.api.JPushInterface
import com.wuhanzihai.rbk.ruibeikang.activity.MainActivity
import com.wuhanzihai.rbk.ruibeikang.activity.UnifiedWebActivity
import org.json.JSONException
import org.json.JSONObject

class MyActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            val bundle = intent.extras

            if (JPushInterface.ACTION_REGISTRATION_ID == intent.action) {
                //SDK 向 JPush Server 注册所得到的注册 ID
                val regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID)
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action) {
                //收到了自定义消息 Push
                //1.消息标题
                val title = bundle.getString(JPushInterface.EXTRA_TITLE)
                //2.消息内容
                val content = bundle.getString(JPushInterface.EXTRA_MESSAGE)
                //3.消息附加字段
                val extra = bundle.getString(JPushInterface.EXTRA_EXTRA)
                //4.消息唯一标识ID
                val msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID)

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action) {
                //收到了通知 Push
                //1.消息标题
                val title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE)
                //2.消息内容
                val content = bundle.getString(JPushInterface.EXTRA_ALERT)
                //3.消息附加字段
                val extra = bundle.getString(JPushInterface.EXTRA_EXTRA)
                //4.通知栏的Notification ID，可以用于清除Notification
                try {
                    val notificationId = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_ID)
                } catch (e: Exception) {
                }

                //5.富媒体通知推送下载的HTML的文件路径,用于展现WebView。
                val fileHtml = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH)
                //6.富媒体通知推送下载的图片资源的文件名
                val fileStr = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_RES)
                val fileNames = fileStr!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                //7.唯一标识通知消息的 ID, 可用于上报统计等。
                val msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID)
                //8.大文本通知样式中大文本的内容。
                val bigText = bundle.getString(JPushInterface.EXTRA_BIG_TEXT)
                //9.大图片通知样式中大图片的路径/地址
                val bigPicPath = bundle.getString(JPushInterface.EXTRA_BIG_PIC_PATH)
                //10.收件箱通知样式中收件箱的内容
                val inboxJson = bundle.getString(JPushInterface.EXTRA_INBOX)
                //11.通知的优先级。默认为0，范围为 -2～2 ，其他值将会被忽略而采用默认。
                val priority = bundle.getString(JPushInterface.EXTRA_NOTI_PRIORITY)
                //12.通知分类。
                val category = bundle.getString(JPushInterface.EXTRA_NOTI_CATEGORY)
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) {
                //(1)用户点击了通知。 一般情况下，用户不需要配置此 receiver action。
                //(2)如果开发者在 AndroidManifest.xml 里未配置此 receiver action，那么，SDK 会默认打开应用程序的主 Activity，相当于用户点击桌面图标的效果。
                //(3)如果开发者在 AndroidManifest.xml 里配置了此 receiver action，那么，当用户点击通知时，SDK 不会做动作。开发者应该在自己写的 BroadcastReceiver 类里处理，比如打开某 Activity 。
                //1.消息标题
                val title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE)
                //2.消息内容
                val content = bundle.getString(JPushInterface.EXTRA_ALERT)
                //3.消息附加字段
                val extra = bundle.getString(JPushInterface.EXTRA_EXTRA)

                val jsonObject = JSONObject(extra)

                val isJump = jsonObject.getString("isJump")

                //4.知栏的Notification ID，可以用于清除Notification
                val notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID)
                //5.唯一标识调整消息的 ID, 可用于上报统计等。

                //跳转到新闻列表页
                val msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID)

                if (isJump == "1") {
                    val actid = jsonObject.getString("actid")
                    val emailIntent = Intent(context, UnifiedWebActivity::class.java)
                    emailIntent.putExtra("id", Integer.parseInt(actid))
                    emailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(emailIntent)
                } else {
                    val emailIntent = Intent(context, MainActivity::class.java)
                    emailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(emailIntent)
                }

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK == intent.action) {
                //(1)用户点击了通知栏中自定义的按钮。(SDK 3.0.0 以上版本支持)
                //(2)使用普通通知的开发者不需要配置此 receiver action。只有开发者使用了 MultiActionsNotificationBuilder 构建携带按钮的通知栏的通知时，可通过该 action 捕获到用户点击通知栏按钮的操作，并自行处理。


            } else if (JPushInterface.ACTION_CONNECTION_CHANGE == intent.action) {
                //JPush 服务的连接状态发生变化。（注：不是指 Android 系统的网络连接状态。）
                val connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false)
            }
        } catch (e: Exception) {
        }

    }

    // 打印所有的 intent extra 数据
    private fun printBundle(bundle: Bundle): String {
        val sb = StringBuilder()
        for (key in bundle.keySet()) {
            if (key == JPushInterface.EXTRA_NOTIFICATION_ID) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key))
            } else if (key == JPushInterface.EXTRA_CONNECTION_CHANGE) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key))
            } else if (key == JPushInterface.EXTRA_EXTRA) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    continue
                }
                try {
                    val json = JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA))
                    val it = json.keys()

                    while (it.hasNext()) {
                        val myKey = it.next()
                        sb.append("\nkey:" + key + ", value: [" + myKey + " - " + json.optString(myKey) + "]")
                    }
                } catch (e: JSONException) {
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key))
            }
        }
        return sb.toString()
    }
}