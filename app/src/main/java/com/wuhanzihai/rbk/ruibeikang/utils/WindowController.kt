package com.wuhanzihai.rbk.ruibeikang.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.ImageView
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.hhjt.baselibrary.common.BaseApplication
import com.hhjt.baselibrary.ext.onClick
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.toDoubleInt
import com.wuhanzihai.rbk.ruibeikang.event.MusicStateEvent
import com.wuhanzihai.rbk.ruibeikang.media.AudioPlayer
import com.wuhanzihai.rbk.ruibeikang.media.ManagedMediaPlayer
import kotlinx.android.synthetic.main.notification_window.view.*

class WindowController private constructor() : View.OnTouchListener {

    private var windowManager: WindowManager? = null
    private var layoutParams: WindowManager.LayoutParams? = null
    private val mContext: Context = BaseApplication.context
    private lateinit var sysView: View
    private var isShow = false

    private var mLastY: Int = 0
    private var mLastX: Int = 0

    private fun initView() {

        sysView = LayoutInflater.from(mContext).inflate(R.layout.notification_window, null)
        sysView.ivClose.onClick {
            destroyThumbWindow()
        }

        sysView.ivPlay.onClick {
            sysView.ivPlay.isSelected = !sysView.ivPlay.isSelected
            if (sysView.ivPlay.isSelected) {
                AudioPlayer.getInstance().resume()
            } else {
                AudioPlayer.getInstance().pause()
            }
            updateWindowLayout()
        }

        sysView.ivClose.setOnTouchListener(this)
        sysView.ivPlay.setOnTouchListener(this)
        sysView.setOnTouchListener(this)

        Bus.observe<MusicStateEvent>().subscribe {
            if (it.state == ManagedMediaPlayer.Status.STARTED) {
                startSeek()
                sysView.ivPlay.setImageResource(R.mipmap.window_zanting)
            } else {
                sysView.ivPlay.setImageResource(R.mipmap.window_play)
            }
        }.registerInBus(this)
    }

    private var handler = Handler()

    private var run = Runnable {
        startSeek()
    }

    private fun startSeek() {
        Log.e("总时长", "${AudioPlayer.getInstance().getDuration().toDouble() / 1000}")
        Log.e("已经播放到", "${AudioPlayer.getInstance().getCurrentPosition().toDouble() / 1000}")
        sysView.tvTime.text = toDoubleInt(AudioPlayer.getInstance().getCurrentPosition() / 1000 / 60) + ":" + toDoubleInt(AudioPlayer.getInstance().getCurrentPosition() / 1000 % 60)
        sysView.tvAllTime.text = toDoubleInt(AudioPlayer.getInstance().getDuration() / 1000 / 60) + ":" + toDoubleInt(AudioPlayer.getInstance().getDuration() / 1000 % 60)
        sysView.tvName.text = AudioPlayer.getInstance().getNowPlaying()!!.music_title
        sysView.tvDesc.text = AudioPlayer.getInstance().getNowPlaying()!!.music_desc
        handler.postDelayed(run, 500)
    }

    /**
     * 显示悬浮窗
     */
    @SuppressLint("ClickableViewAccessibility")
    fun showThumbWindow() {
        if (isShow) {
            return
        }
        initView()

        windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var screenWidth = 0
        var screenHeight = 0
        if (windowManager != null) {
            //获取屏幕的宽和高
            val point = Point()
            windowManager!!.defaultDisplay.getSize(point)
            screenWidth = point.x
            screenHeight = point.y
            layoutParams = WindowManager.LayoutParams()
            layoutParams!!.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams!!.height = WindowManager.LayoutParams.WRAP_CONTENT
            //            layoutParams.width = 200;
            //            layoutParams.height = 200;
            //设置type
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //26及以上必须使用TYPE_APPLICATION_OVERLAY   @deprecated TYPE_PHONE
                layoutParams!!.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                layoutParams!!.type = WindowManager.LayoutParams.TYPE_PHONE
            }
            //设置flags
            layoutParams!!.flags = (WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
            layoutParams!!.gravity = Gravity.START or Gravity.TOP
            //背景设置成透明
            layoutParams!!.format = PixelFormat.TRANSPARENT
            layoutParams!!.x = screenWidth
            layoutParams!!.y = screenHeight / 2
            //将View添加到屏幕上
            windowManager!!.addView(sysView, layoutParams)
        }
        isShow = true
    }

    /**
     * 更新window
     */
    fun updateWindowLayout() {
        isShow = true
        if (windowManager != null && layoutParams != null) {
            windowManager!!.updateViewLayout(sysView, layoutParams)
        }
    }

    /**
     * 关闭悬浮窗
     */
    fun destroyThumbWindow() {
        if (windowManager != null && sysView != null) {
            windowManager!!.removeView(sysView)
        }
        isShow = false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val mInScreenX = event.rawX.toInt()
        val mInScreenY = event.rawY.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = event.rawX.toInt()
                mLastY = event.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                layoutParams!!.x += mInScreenX - mLastX
                layoutParams!!.y += mInScreenY - mLastY
                mLastX = mInScreenX
                mLastY = mInScreenY
                updateWindowLayout()
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return false
    }

    companion object {
        private val windowController by lazy {
            WindowController()
        }

        fun getInstance(): WindowController {
            return windowController
        }
    }
}
