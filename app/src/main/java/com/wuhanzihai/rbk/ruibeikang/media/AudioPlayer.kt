package com.wuhanzihai.rbk.ruibeikang.media

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.wifi.WifiManager
import android.os.PowerManager
import android.util.Log
import com.hhjt.baselibrary.common.BaseApplication

import com.wuhanzihai.rbk.ruibeikang.data.entity.MusicBean

import java.io.IOException
import java.util.ArrayList
import java.util.Random

class AudioPlayer : MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener {

    private var mMediaPlayer: ManagedMediaPlayer? = null
    private var mQueue: List<MusicBean>? = null
    var queueIndex = 0
    var playMode = PlayMode.ORDER
    private var nowPlaying: MusicBean? = null
    private var wifiLock: WifiManager.WifiLock? = null
    private var audioFocusManager: AudioFocusManager? = null

    private val TAG = "AudioPlayer"

    fun getCurrentPosition(): Int {
        return if ((getStatus() == ManagedMediaPlayer.Status.STARTED || getStatus() == ManagedMediaPlayer.Status.PAUSED)) {
            mMediaPlayer!!.currentPosition
        } else 0
    }

    fun getDuration():Int{
        return if ((getStatus() == ManagedMediaPlayer.Status.STARTED || getStatus() == ManagedMediaPlayer.Status.PAUSED)) {
            mMediaPlayer!!.duration
        } else 0
    }


    fun getStatus(): ManagedMediaPlayer.Status{
        return if (mMediaPlayer != null) {
            mMediaPlayer!!.state
        } else {
            ManagedMediaPlayer.Status.STOPPED
        }
    }

    fun getNextPlaying():MusicBean?{
        when (playMode) {
            AudioPlayer.PlayMode.ORDER -> {
                queueIndex = queueIndex + 1
                return getPlaying(queueIndex)
            }
            AudioPlayer.PlayMode.LOOP -> {
                queueIndex = (queueIndex + 1) % mQueue!!.size
                return getPlaying(queueIndex)
            }
            AudioPlayer.PlayMode.RANDOM -> {
                queueIndex = Random().nextInt(mQueue!!.size) % mQueue!!.size
                return getPlaying(queueIndex)
            }
            AudioPlayer.PlayMode.REPEAT -> return getPlaying(queueIndex)
            else -> {
            }
        }
        return null
    }

    fun getPreviousPlaying():MusicBean?{
        when (playMode) {
            AudioPlayer.PlayMode.ORDER -> {
                queueIndex = queueIndex - 1
                return getPlaying(queueIndex)
            }
            AudioPlayer.PlayMode.LOOP -> {
                queueIndex = (queueIndex + mQueue!!.size - 1) % mQueue!!.size
                return getPlaying(queueIndex)
            }
            AudioPlayer.PlayMode.RANDOM -> {
                queueIndex = Random().nextInt(mQueue!!.size) % mQueue!!.size
                return getPlaying(queueIndex)
            }
            AudioPlayer.PlayMode.REPEAT -> return getPlaying(queueIndex)
            else -> {
            }
        }
        return null
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

    override fun onCompletion(mp: MediaPlayer) {}

    override fun onPrepared(mp: MediaPlayer) {
        start()
    }

    override fun onBufferingUpdate(mp: MediaPlayer, percent: Int) {}

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        Log.e(TAG, "MediaPlayer onError what $what extra $extra")
        release()
        next()
        return false
    }

    fun init() {
        mMediaPlayer = ManagedMediaPlayer()
        // 使用唤醒锁
        mMediaPlayer!!.setWakeMode(BaseApplication.context, PowerManager.PARTIAL_WAKE_LOCK)
        mMediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mMediaPlayer!!.setOnCompletionListener(this)
        mMediaPlayer!!.setOnPreparedListener(this)
        mMediaPlayer!!.setOnBufferingUpdateListener(this)
        mMediaPlayer!!.setOnErrorListener(this)
        // 初始化wifi锁
        wifiLock = (BaseApplication.context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager).createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock")
        // 初始化音频焦点管理器
        audioFocusManager = AudioFocusManager(BaseApplication.context)
        // 初始化AndroidVideoCache
    }

    fun setPlayIndex(index: Int) {
        this.queueIndex = index
    }

    fun setQueueAndIndex(mQueue: List<MusicBean>, mQueueIndex: Int) {
        this.mQueue = mQueue
        this.queueIndex = mQueueIndex
    }

    private fun play(source: MusicBean?) {
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
        play(source.url)
    }

    private fun play(dataSource: String) {
        if (mMediaPlayer!!.state == ManagedMediaPlayer.Status.PAUSED) {
            mMediaPlayer!!.start()
        } else {
            mMediaPlayer!!.reset()
            try {
                mMediaPlayer!!.setDataSource(dataSource)
                mMediaPlayer!!.prepareAsync()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e(TAG, "该资源无法播放")
            }

        }

    }

    private fun start() {
        // 获取音频焦点
        if (!audioFocusManager!!.requestAudioFocus()) {
            Log.e(TAG, "获取音频焦点失败")
        }
        mMediaPlayer!!.start()
        // 启用wifi锁
        wifiLock!!.acquire()
        // 更新notification
        // 向MainActivity发送EventBus
        //        EventBus.getDefault().post(new MainActivityEvent());
        // 发送播放状态的广播
    }

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
            //            EventBus.getDefault().post(new MainActivityEvent());
            // 取消音频焦点
            if (audioFocusManager != null) {
                audioFocusManager!!.abandonAudioFocus()
            }
        }
    }

    fun resume() {
        if (getStatus() == ManagedMediaPlayer.Status.PAUSED) {
            start()
        }
    }

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

    fun seekTo(msec: Int) {
        if (getStatus() == ManagedMediaPlayer.Status.STARTED
                || getStatus() == ManagedMediaPlayer.Status.PAUSED
                || getStatus() == ManagedMediaPlayer.Status.COMPLETED) {
            mMediaPlayer!!.seekTo(msec)
        }
    }

    /**
     * 播放
     */
    fun play() {
        val MusicBean = getPlaying(queueIndex)
        play(MusicBean)
    }

    operator fun next() {
        var MusicBean = getNextPlaying()
        play(MusicBean)
    }

    fun previous() {
        val MusicBean = getPreviousPlaying()
        play(MusicBean)
    }

    fun getNowPlaying(): MusicBean? {
        return if (nowPlaying != null) {
            nowPlaying
        } else {
            getPlaying(queueIndex)
        }
    }

    private fun getPlaying(index: Int): MusicBean? {
        return if (mQueue != null && !mQueue!!.isEmpty() && index >= 0 && index < mQueue!!.size) {
            mQueue!!.get(index)
        } else {
            null
        }
    }

    companion object {
        private val audioPlayer by lazy { AudioPlayer() }
        fun getInstance(): AudioPlayer {
            return audioPlayer
        }
    }
}