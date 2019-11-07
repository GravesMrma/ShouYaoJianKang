package com.wuhanzihai.rbk.ruibeikang.media

import android.app.*
import android.content.Intent
import android.os.IBinder
import android.os.Build
import android.support.annotation.RequiresApi
import com.wuhanzihai.rbk.ruibeikang.R
import android.widget.RemoteViews
import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.wuhanzihai.rbk.ruibeikang.event.MusicEvent
import com.wuhanzihai.rbk.ruibeikang.event.MusicStateEvent

@RequiresApi(Build.VERSION_CODES.N)
class MusicService : Service() {
    private var remoteViews: RemoteViews? = null
    private var notificationManager: NotificationManager? = null
    private var ivImg: ImageView? = null

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }

    override fun onCreate() {
        super.onCreate()
        // 第一次进来  之后不再调用  启动前台服务
        startForeground()

        startMusic()
    }

    private val handler = Handler()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 每一次用  开始播放音乐 首先注册数据监听  然后 在设置一个状态监听
        var time = intent!!.getIntExtra("time", 0)
        if (time != 0) {
            handler.postDelayed({
                stopForeground(true)
                AudioPlayer.getInstance().stop()
            }, time.toLong())
        } else if (time == -1) {
            stopForeground(STOP_FOREGROUND_REMOVE)
            AudioPlayer.getInstance().stop()
        } else {
            var data = intent.getSerializableExtra("data") as MusicEvent
            Bus.send(data)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForeground() {
        Log.d("消息通知", "")
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent1 = Intent(baseContext, MusicOperationService::class.java)
        intent1.putExtra("type", 1)
        val pendingIntent1 = PendingIntent.getService(baseContext, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT)

        val intent2 = Intent(baseContext, MusicOperationService::class.java)
        intent2.putExtra("type", 2)
        val pendingIntent2 = PendingIntent.getService(baseContext, 2, intent2, PendingIntent.FLAG_UPDATE_CURRENT)

        val intent3 = Intent(baseContext, MusicOperationService::class.java)
        intent3.putExtra("type", 3)
        val pendingIntent3 = PendingIntent.getService(baseContext, 3, intent3, PendingIntent.FLAG_UPDATE_CURRENT)

        val CHANNEL_ID = "channel_id"   //通道渠道id
        val CHANEL_NAME = "F_notice" //通道渠道名称

        remoteViews = RemoteViews(packageName, R.layout.notification_music)
        remoteViews!!.setOnClickPendingIntent(R.id.ivLast, pendingIntent1)
        remoteViews!!.setOnClickPendingIntent(R.id.ivPlay, pendingIntent2)
        remoteViews!!.setOnClickPendingIntent(R.id.ivNext, pendingIntent3)
        notification = Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("音频播放")
                .setContentText("音频播放")
                .setTicker("音频播放")
                .setNumber(1)
                .setCustomContentView(remoteViews)
                .setTicker("音频播放")
        //收到信息后状态栏显示的文字信息.setAutoCancel(true)//用户点击Notification点击面板后是否让通知取消(默认不取消)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel: NotificationChannel? = null
            channel = NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager!!.createNotificationChannel(channel)
            notification!!.setChannelId(CHANNEL_ID);
        }
        build = notification!!.build()
        startForeground(1, build)
    }

    private var build: Notification? = null
    private var notification: Notification.Builder? = null
    private fun startMusic() {
        Bus.observe<MusicEvent>().subscribe {
            //            AudioPlayer.getInstance().release()
            AudioPlayer.getInstance().setQueueAndIndex(it.data, it.position)
            AudioPlayer.getInstance().play()
        }.registerInBus(this)

        Bus.observe<MusicStateEvent>().subscribe {
            when (it.state) {
                ManagedMediaPlayer.Status.STARTED -> {
                    var source = AudioPlayer.getInstance().getNowPlaying()
                    remoteViews!!.setTextViewText(R.id.tvName, source!!.music_title)
                    remoteViews!!.setImageViewResource(R.id.ivPlay, R.mipmap.ic_zanting)
                    remoteViews!!.setImageViewResource(R.id.ivImg, R.mipmap.noty_music)
                    notificationManager!!.notify(1, notification!!.build())
                }
                ManagedMediaPlayer.Status.PAUSED -> {
                    remoteViews!!.setImageViewResource(R.id.ivPlay, R.mipmap.yy_top_bofang_icon)
                    notificationManager!!.notify(1, notification!!.build())
                }
                ManagedMediaPlayer.Status.STOPPED -> {
                    remoteViews!!.setImageViewResource(R.id.ivPlay, R.mipmap.yy_top_bofang_icon)
                    notificationManager!!.notify(1, notification!!.build())
                }
                ManagedMediaPlayer.Status.COMPLETED -> {

                }
            }
        }.registerInBus(this)
    }
}