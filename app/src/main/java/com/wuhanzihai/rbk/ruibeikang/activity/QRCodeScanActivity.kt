package com.xidebao.activity

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.Gravity
import android.widget.TextView
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.hhjt.baselibrary.utils.LoginUtils
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.QRCodeScanPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.QRCodeScanView
import kotlinx.android.synthetic.main.activity_qrcode_scan.*
import org.jetbrains.anko.*
import org.json.JSONException
import org.json.JSONObject
import per.goweii.anylayer.AnyLayer
import kotlin.concurrent.thread

/**
 * 扫一扫
 */
class QRCodeScanActivity : BaseMvpActivity<QRCodeScanPresenter>(), QRCodeView.Delegate, QRCodeScanView {

    private var id = ""
    private var user_id = ""
    private var join = false
    private var apply = false

//    private val dialog by lazy {
//        val anyLayer =
//                AnyLayer.with(act)
//                        .contentView(R.layout.layout_action_charge_off)
//                        .gravity(Gravity.CENTER)
//                        .backgroundResource(R.color.clarity_40)
//                        .cancelableOnTouchOutside(true)
//                        .onClick(R.id.mTvConfirm) { anyLayer, v ->
//                            mPresenter.chargeOffAction(id, user_id, LoginUtils.getAuthId())
//                            anyLayer.dismiss()
//                        }
//                        .onClick(R.id.mTvCancel) { anyLayer, v ->
//                            anyLayer.dismiss()
//                            reScan()
//                        }
//        anyLayer
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_scan)

        join = intent.getBooleanExtra("join", false)
        apply = intent.getBooleanExtra("apply", false)

        mQRCodeView.setDelegate(this)

        initQRCodeView()

        mIvBack.onClick { finish() }
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)

        mPresenter.mView = this
    }

    override fun onStart() {
        super.onStart()
        initQRCodeView()
    }

    override fun onResume() {
        super.onResume()
        mQRCodeView.startSpot()
    }

    private fun initQRCodeView() {
        mQRCodeView.startCamera()//打开相机
        mQRCodeView.showScanRect()//显示扫描框
        mQRCodeView.startSpot()//开始识别二维码
    }

    override fun onScanQRCodeSuccess(result: String?) {
        vibrate()
        if (!result.isNullOrEmpty()) {
            toast("扫码成功")

        } else {
            mQRCodeView.startSpot()
        }
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {

    }

    override fun onScanQRCodeOpenCameraError() {
        toast("扫码错误")
    }

    override fun onStop() {
        mQRCodeView.stopCamera()
        super.onStop()
    }

    override fun onDestroy() {
        mQRCodeView.onDestroy()
        super.onDestroy()
    }

    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(200)
    }

    override fun onIsApplyResult(result: String) {

    }

    override fun onApplyResult(result: Int) {
    }

    override fun onIsBeginResult(result: String) {

    }

    override fun onJoinResult() {

    }

    override fun onError(text: String, code: Int) {
        super.onError(text, code)
        reScan()
    }

    private fun reScan() {
        thread {
            Thread.sleep(2000)
            mQRCodeView.startSpot()
        }
    }

}
