package com.wuhanzihai.rbk.ruibeikang.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.hhjt.baselibrary.ext.onClick
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.dialog_bluetooth.view.*

class BluetoothDialog(context: Context, cancelable: Boolean, backCancelable: Boolean) : Dialog(context, R.style.MyDialog) {

    private var view: View = LayoutInflater.from(context).inflate(R.layout.dialog_bluetooth, null)
    private var iscancelable = cancelable
    private var isbackCancelable = backCancelable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view)//这行一定要写在前面
        setCancelable(iscancelable)//点击外部不可dismiss
        setCanceledOnTouchOutside(isbackCancelable)
        val window = this.window
        window!!.setGravity(Gravity.CENTER)
        val params = window.attributes
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = params

    }

    fun setName(name: String): BluetoothDialog {
        view.tvName.text = name
        return this
    }

    fun setNoBluetooth(): BluetoothDialog {
        view.rlSearching.visibility = View.GONE
        view.ivNoBluetooth.visibility = View.VISIBLE

        view.lView.visibility = View.VISIBLE
        return this
    }

    fun setCancelListener(onClickListener: View.OnClickListener): BluetoothDialog {
        view.tvClose.setOnClickListener(onClickListener)
        return this
    }

    fun setReconnectListener(onClickListener: View.OnClickListener): BluetoothDialog {
        view.tvReconnect.setOnClickListener(onClickListener)
        return this
    }
}