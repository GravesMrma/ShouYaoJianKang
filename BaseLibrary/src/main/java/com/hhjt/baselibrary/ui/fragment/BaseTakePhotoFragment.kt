package com.hhjt.baselibrary.ui.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.hhjt.baselibrary.common.BaseApplication
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.utils.DateUtils
import com.hhjt.baselibrary.utils.ImageUtils
import com.hhjt.baselibrary.widgets.ProgressLoading
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.component.DaggerActivityComponent
import com.kotlin.base.injection.module.ActivityModule
import com.kotlin.base.injection.module.LifecycleProviderModule
import org.devio.takephoto.app.TakePhoto
import org.devio.takephoto.app.TakePhotoImpl
import org.devio.takephoto.compress.CompressConfig
import org.devio.takephoto.model.TResult
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import javax.inject.Inject

/*
    存在选择图片的Activity基础封装
 */
abstract class BaseTakePhotoFragment<T : BasePresenter<*>> : BaseFragment(), BaseView,
    TakePhoto.TakeResultListener, EasyPermissions.PermissionCallbacks {

    private lateinit var mTakePhoto: TakePhoto

    private lateinit var mTempFile: File

    @Inject
    lateinit var mPresenter: T

    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading

    var limit = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()

        mTakePhoto = TakePhotoImpl(this, this)
        mTakePhoto.onCreate(savedInstanceState)

        mLoadingDialog = ProgressLoading.create(act)
    }

    /*
        Dagger注册
     */
    protected abstract fun injectComponent()

    /*
        初始化Activity Component
     */
    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
            .appComponent((activity!!.application as BaseApplication).appComponent)
            .activityModule(ActivityModule(activity!!))
            .lifecycleProviderModule(LifecycleProviderModule(this))
            .build()

    }

    /*
        显示加载框，默认实现
     */
    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    /*
        隐藏加载框，默认实现
     */
    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    /*
        错误信息提示，默认实现
     */
    override fun onError(text: String, code: Int) {
        toast(text)
    }

    /*
        弹出选择框，默认实现
        可根据实际情况，自行修改
     */
    private fun showAlertView() {
        AlertView("选择图片", "", "取消", null, arrayOf("拍照", "相册"), act,
            AlertView.Style.ActionSheet, OnItemClickListener { _, position ->
                mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false)
                when (position) {
                    0 -> {
                        createTempFile()
                        mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))
                    }
                    1 -> mTakePhoto.onPickMultiple(limit)
                }
            }).setCancelable(true)
            .show()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        alert("拒绝权限无法正常使用APP，是否前往设置？", "提示") {
            yesButton {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:${activity!!.packageName}")
                startActivity(intent)
            }
            noButton { }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        showAlertView()
    }

    /*
        获取图片，成功回调
     */
    override fun takeSuccess(result: TResult?) {

        val degree = ImageUtils.readPictureDegree(result?.image?.originalPath)
        if (degree != 0) {
            ImageUtils.rotaingDegreeImage(degree, result?.image?.compressPath)
        }

        Log.d("TakePhoto", result?.image?.originalPath)
        Log.d("TakePhoto", result?.image?.compressPath)
    }

    /*
        获取图片，取消回调
     */
    override fun takeCancel() {
    }

    /*
        获取图片，失败回调
     */
    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("takePhoto", msg)
    }

    /*
        TakePhoto默认实现
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    /*
        新建临时文件
     */
    private fun createTempFile() {
        val tempFileName = "${DateUtils.curTime}.png"
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            this.mTempFile = File(Environment.getExternalStorageDirectory(), tempFileName)
            return
        }

        this.mTempFile = File(activity!!.filesDir, tempFileName)
    }

    override fun onDataIsNull() {
        toast("暂无数据")
    }

    override fun onTokenInvalid(text: String, code: Int) {
        toast(text)
    }

    /**
     * 检查是否有权限
     */
    protected fun checkPermission() {
        val perms = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (EasyPermissions.hasPermissions(act, *perms)) {
            showAlertView()
        } else {
            EasyPermissions.requestPermissions(
                this@BaseTakePhotoFragment,
                "你好,选取需要获取摄像头权限和读写内存权限。你能允许吗?", 0, *perms
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}
