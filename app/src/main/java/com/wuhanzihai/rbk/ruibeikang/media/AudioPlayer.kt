package com.wuhanzihai.rbk.ruibeikang.media

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.PowerManager
import android.util.Log
import com.eightbitlab.rxbus.Bus
import com.hhjt.baselibrary.common.BaseApplication
import com.hhjt.baselibrary.common.BaseConstant

import com.wuhanzihai.rbk.ruibeikang.data.entity.MusicItem
import com.wuhanzihai.rbk.ruibeikang.event.MusicStateEvent

import java.io.IOException
import java.util.ArrayList
import java.util.Random

class AudioPlayer : MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener {

    private var mMediaPlayer: ManagedMediaPlayer? = null
    private var mQueue = mutableListOf<MusicItem>()
    var queueIndex = 0
    var playMode = PlayMode.ORDER
    private var nowPlaying: MusicItem? = null
    private var wifiLock: WifiManager.WifiLock? = null
    private var audioFocusManager: AudioFocusManager? = null

    private val TAG = "时长"

    override fun onCompletion(mp: MediaPlayer) {
        Log.e("播放进度", "onCompletion")
    }

    // 数据加载完毕 开始播放
    override fun onPrepared(mp: MediaPlayer) {
        startAudioPlay()
    }

    override fun onBufferingUpdate(mp: MediaPlayer, percent: Int) {
        Log.e("播放进度", "onBufferingUpdate")
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        Log.e(TAG, "MediaPlayer onError what $what extra $extra")
        release()
        next()
        return false
    }

    private fun init() {
        mMediaPlayer = ManagedMediaPlayer()
        // 使用唤醒锁
        mMediaPlayer!!.setWakeMode(BaseApplication.context, PowerManager.PARTIAL_WAKE_LOCK)
        mMediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mMediaPlayer!!.setOnCompletionListener(this) // 加载监听
        mMediaPlayer!!.setOnPreparedListener(this) //
        mMediaPlayer!!.setOnBufferingUpdateListener(this)
        mMediaPlayer!!.setOnErrorListener(this) // 错误监听

        // 初始化wifi锁
        wifiLock = (BaseApplication.context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager).createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock")
        // 初始化音频焦点管理器
        audioFocusManager = AudioFocusManager(BaseApplication.context)
        // 初始化AndroidVideoCache
    }

    // 装载音乐数据 音乐列表
    fun setQueueAndIndex(mQueue: List<MusicItem>, mQueueIndex: Int) {
        this.mQueue.clear()
        this.mQueue.addAll(mQueue)
        this.queueIndex = mQueueIndex
    }

    // 设置音乐资源
    private fun setPlayResources(source: MusicItem?) {
        if (source == null) {
            Log.e(TAG, "没有可用资源")
            return
        }
        if (mMediaPlayer == null) {
            init()
        }
        if (getStatus() == ManagedMediaPlayer.Status.INITIALIZED) {
            Log.e(TAG, "正在准备上一个资源，请稍候")
            return
        }
        // 更新播放器状态
        //        mMediaPlayer.reset();

        nowPlaying = source
        // 更新Notification
        // 向MainActivity发送EventBus
        //        EventBus.getDefault().post(new MainActivityEvent());
        // 发送初始化资源信息的广告
        // 获取音频地址（音频地址一般私有）
        playResources(BaseConstant.BASE_URL.substring(0,BaseConstant.BASE_URL.length-1) + source.music_url)
    }

    // 准备音乐资源
    private fun playResources(dataSource: String) {
        // 如果是暂停状态  那就应该是start

        Log.e("准备音乐资源", "reset")
        mMediaPlayer!!.reset()
        try {
            mMediaPlayer!!.setDataSource(dataSource)
            mMediaPlayer!!.prepareAsync()
            mMediaPlayer!!.setOnCompletionListener {
                next()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "该资源无法播放")
        }
//
//        if (mMediaPlayer!!.state == ManagedMediaPlayer.Status.PAUSED) {
//            //  暂停状态下 有两种 一种是继续播放 一种是下一首 或者上一首
//            mMediaPlayer!!.start()
//        } else {
//            Log.e("准备音乐资源", "reset")
//            mMediaPlayer!!.reset()
//            try {
//                mMediaPlayer!!.setDataSource(dataSource)
//                mMediaPlayer!!.prepareAsync()
//                mMediaPlayer!!.setOnCompletionListener {
//                    next()
//                }
//            } catch (e: IOException) {
//                e.printStackTrace()
//                Log.e(TAG, "该资源无法播放")
//            }
//        }
    }

    // 一切就绪 开始播放
    private fun startAudioPlay() {
        // 获取音频焦点
        if (!audioFocusManager!!.requestAudioFocus()) {
            Log.e(TAG, "获取音频焦点失败")
        }
        mMediaPlayer!!.start()
        // 启用wifi锁
        wifiLock!!.acquire()
        Bus.send(MusicStateEvent(ManagedMediaPlayer.Status.STARTED))

        // 更新notification
        // 向MainActivity发送EventBus
        //        EventBus.getDefault().post(new MainActivityEvent());
        // 发送播放状态的广播
    }


//    以下都是手动操作方法

    // 跳转到指定的秒数播放
    fun seekTo(msec: Int) {
        if (getStatus() == ManagedMediaPlayer.Status.STARTED
                || getStatus() == ManagedMediaPlayer.Status.PAUSED
                || getStatus() == ManagedMediaPlayer.Status.COMPLETED) {
            mMediaPlayer!!.seekTo(msec)
        }
    }

    // 播放 操作
    fun play() {
        val MusicItem = getPlaying(queueIndex)
        setPlayResources(MusicItem)
    }

    // 下一曲 操作
    operator fun next() {
        var MusicItem = getNextPlaying()
        setPlayResources(MusicItem)
    }

    // 设置下一首播放的资源
    fun setPlayIndex(index: Int) {
        this.queueIndex = index
    }

    // 上一曲  操作
    fun previous() {
        val MusicItem = getPreviousPlaying()
        setPlayResources(MusicItem)
    }

    // 暂停  操作
    fun pause() {
        if (getStatus() == ManagedMediaPlayer.Status.STARTED) {
            mMediaPlayer!!.pause()
            // 关闭wifi锁
            if (wifiLock!!.isHeld) {
                wifiLock!!.release()
            }
            // 发送播放状态的广播
            // 更新notification
            // 向MainActivity发送EventBus
            // EventBus.getDefault().post(new MainActivityEvent());
            // 取消音频焦点
            if (audioFocusManager != null) {
                audioFocusManager!!.abandonAudioFocus()
            }
            Bus.send(MusicStateEvent(ManagedMediaPlayer.Status.PAUSED))
        }
    }

    // 继续  操作
    fun resume() {
        if (getStatus() == ManagedMediaPlayer.Status.PAUSED) {
            startAudioPlay()
        }
    }

    // 停止 操作
    fun stop() {
        if (getStatus() == ManagedMediaPlayer.Status.STARTED || getStatus() == ManagedMediaPlayer.Status.PAUSED || getStatus() == ManagedMediaPlayer.Status.COMPLETED) {
            mMediaPlayer!!.stop()
            // 发送播放状态的广播
            // 更新notification
            // 向MainActivity发送EventBus
            //            EventBus.getDefault().post(new MainActivityEvent());
            // 取消音频焦点
            if (audioFocusManager != null) {
                audioFocusManager!!.abandonAudioFocus()
            }
        }
    }

    fun release() {
        if (mMediaPlayer == null) {
            return
        }
        nowPlaying = null
        Log.d(TAG, "release")
        mMediaPlayer!!.release()
        mMediaPlayer = null
        // 取消音频焦点
        if (audioFocusManager != null) {
            audioFocusManager!!.abandonAudioFocus()
        }
        // 关闭wifi锁
        if (wifiLock!!.isHeld) {
            wifiLock!!.release()
        }
        wifiLock = null
        audioFocusManager = null
        // 向MainActivity发送EventBus
        //        EventBus.getDefault().post(new MainActivityEvent());
    }

//    以下都是获取信息的

    //获取当前正在播放的资源
    fun getNowPlaying(): MusicItem? {
        return if (nowPlaying != null) {
            nowPlaying
        } else {
            getPlaying(queueIndex)
        }
    }

    private fun getPlaying(index: Int): MusicItem? {
        return if (mQueue != null && mQueue!!.isNotEmpty() && index >= 0 && index < mQueue!!.size) {
            mQueue!![index]
        } else {
            null
        }
    }

    // 获取已经播放时长
    fun getCurrentPosition(): Int {
        return if ((getStatus() == ManagedMediaPlayer.Status.STARTED || getStatus() == ManagedMediaPlayer.Status.PAUSED)) {
            mMediaPlayer!!.currentPosition
        } else 0
    }

    // 获取音乐时长
    fun getDuration(): Int {
        return if ((getStatus() == ManagedMediaPlayer.Status.STARTED || getStatus() == ManagedMediaPlayer.Status.PAUSED)) {
            mMediaPlayer!!.duration
        } else 0
    }

    // 获取当前播放状态
    fun getStatus(): ManagedMediaPlayer.Status {
        return if (mMediaPlayer != null) {
            mMediaPlayer!!.state
        } else {
            ManagedMediaPlayer.Status.STOPPED
        }
    }

    fun getData():List<MusicItem>?{
        return mQueue
    }

    // 获取下一首
    private fun getNextPlaying(): MusicItem? {
        return when (playMode) {
            PlayMode.ORDER -> {
                queueIndex += 1
                getPlaying(queueIndex)
            }
            PlayMode.LOOP -> {
                queueIndex = (queueIndex + 1) % mQueue!!.size
                getPlaying(queueIndex)
            }
            PlayMode.RANDOM -> {
                queueIndex = Random().nextInt(mQueue!!.size) % mQueue!!.size
                getPlaying(queueIndex)
            }
            PlayMode.REPEAT -> getPlaying(queueIndex)
        }
    }

    // 获取上一首
    private fun getPreviousPlaying(): MusicItem? {
        return when (playMode) {
            PlayMode.ORDER -> {
                queueIndex -= 1
                getPlaying(queueIndex)
            }
            PlayMode.LOOP -> {
                queueIndex = (queueIndex + mQueue!!.size - 1) % mQueue!!.size
                getPlaying(queueIndex)
            }
            PlayMode.RANDOM -> {
                queueIndex = Random().nextInt(mQueue!!.size) % mQueue!!.size
                getPlaying(queueIndex)
            }
            PlayMode.REPEAT -> getPlaying(queueIndex)
        }
    }


    companion object {
        val audioPlayer by lazy {
            AudioPlayer()
        }

        fun getInstance() :AudioPlayer{
            return audioPlayer
        }
    }

    /**
     * 播放方式
     */
    enum class PlayMode {
        /**
         * 顺序
         */
        ORDER,
        /**
         * 列表循环
         */
        LOOP,
        /**
         * 随机
         */
        RANDOM,
        /**
         * 单曲循环
         */
        REPEAT
    }
}