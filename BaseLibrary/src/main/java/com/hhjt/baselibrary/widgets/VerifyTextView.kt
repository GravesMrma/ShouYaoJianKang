package com.hhjt.baselibrary.widgets

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.TextView

/*
    获取验证码按钮
    带倒计时
 */
class VerifyTextView(mContext: Context, attrs: AttributeSet) : TextView(mContext, attrs) {
    private val mHandler: Handler
    private var mCount = 60
    private var mOnVerifyBtnClick: OnVerifyBtnClick? = null

    init {
        this.text = "发送"
        mHandler = Handler()

    }

    /*
        倒计时，并处理点击事件
     */
    fun requestSendVerifyNumber() {
        mHandler.postDelayed(countDown, 0)
        if (mOnVerifyBtnClick != null) {
            mOnVerifyBtnClick!!.onClick()
        }

    }

    /*
        倒计时
     */
    private val countDown = object : Runnable {
        override fun run() {
            this@VerifyTextView.text = mCount.toString() + "s "
//            this@VerifyTextView.setBackgroundColor(resources.getColor(R.color.transparent))
//            this@VerifyTextView.setTextColor(resources.getColor(R.color.blue))
            this@VerifyTextView.isEnabled = false

            if (mCount > 0) {
                mHandler.postDelayed(this, 1000)
            } else {
                resetCounter()
            }
            mCount--
        }
    }

    fun removeRunable() {
        mHandler.removeCallbacks(countDown)
    }

    /*
        恢复到初始状态
     */
    fun resetCounter(vararg text: String) {
        this.isEnabled = true
        if (text.isNotEmpty() && "" != text[0]) {
            this.text = text[0]
        } else {
            this.text = "发送"
        }
//        this.setBackgroundColor(resources.getColor(R.color.transparent))
//        this.setTextColor(resources.getColor(R.color.blue))
        mCount = 60
    }

    /*
        点击事件接口
     */
    interface OnVerifyBtnClick {
        fun onClick()
    }

    fun setOnVerifyBtnClick(onVerifyBtnClick: OnVerifyBtnClick) {
        this.mOnVerifyBtnClick = onVerifyBtnClick
    }
}
