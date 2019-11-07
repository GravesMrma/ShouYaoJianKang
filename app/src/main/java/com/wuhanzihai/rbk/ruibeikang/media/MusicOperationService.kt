package com.wuhanzihai.rbk.ruibeikang.media

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.app.Notification
import android.os.Build
import android.support.annotation.RequiresApi
import com.wuhanzihai.rbk.ruibeikang.R
import android.widget.RemoteViews
import android.app.NotificationManager
import android.content.Context
import android.app.NotificationChannel
import android.util.Log
import android.widget.ImageView
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.wuhanzihai.rbk.ruibeikang.event.MusicEvent
import com.wuhanzihai.rbk.ruibeikang.event.MusicStateEvent

class MusicOperationService : Service() {

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }

    override fun onCreate() {
        super.onCreate()
        // 第一次进来  之后不再调用  启动前台服务

        Log.e("", "")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 每一次用  开始播放音乐 首先注册数据监听  然后 在设置一个状态监听
        when (intent!!.getIntExtra("type", -1)) {
            1 -> {
                AudioPlayer.getInstance().previous()
            }
            2 -> {
                if (AudioPlayer.getInstance().getStatus() == ManagedMediaPlayer.Status.STARTED) {
                    AudioPlayer.getInstance().pause()
                } else {
                    AudioPlayer.getInstance().resume()
                }
            }
            3 -> {
                AudioPlayer.getInstance().next()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

}