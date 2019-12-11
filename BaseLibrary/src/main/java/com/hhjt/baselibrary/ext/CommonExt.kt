package com.hhjt.baselibrary.ext

import android.content.Context
import android.net.Uri
import android.support.design.widget.TabLayout
import android.util.Base64
import android.util.Patterns
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.*
import com.hhjt.baselibrary.widgets.DefaultTabSelectedListener
import com.hhjt.baselibrary.widgets.DefaultTextWatcher
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

//Kotlin通用扩展


/*
    扩展Observable执行
 */
fun <T> Observable<T>.excute(subscriber: BaseSubscriber<T>, lifecycleProvider: LifecycleProvider<*>) {
    this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribe(subscriber)
}

/*
    扩展数据转换
 */
fun <T> Observable<BaseResp<T>>.convert(): Observable<T> {
    return this.flatMap(BaseFunc())
}

/*
    扩展Boolean类型数据转换
 */
fun <T> Observable<BaseResp<T>>.convertBoolean(): Observable<Boolean> {
    return this.flatMap(BaseFuncBoolean())
}

/*
    扩展T类型数据转换
 */
fun <T : BaseData> Observable<T>.convertT(): Observable<T> {
    return this.flatMap(BaseFuncT())
}

/*
    扩展点击事件
 */
fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/*
    扩展点击事件，参数为方法
 */
fun View.onClick(method: () -> Unit): View {
    setOnClickListener { method() }
    return this
}

fun TabLayout.onTabSelectedListener(method: (tab: TabLayout.Tab) -> Unit): TabLayout {
    addOnTabSelectedListener(object : DefaultTabSelectedListener() {
        override fun onTabSelected(tab: TabLayout.Tab) {
            method(tab)
        }
    })
    return this
}

/*
    扩展Button可用性
 */
fun Button.enable(et: EditText, method: () -> Boolean) {
    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}

/*
    扩展视图可见性
 */
fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.setInVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}


fun WebView.loadData(data: String) {
    this.loadData(data, "text/html; charset=UTF-8", null)
}

/**
 * 根据文件地址转成base64
 */
fun imageFileToBase64(path: String): String {
    val inputStream: InputStream?
    var data: ByteArray? = null
    // 读取图片字节数组
    try {
        inputStream = FileInputStream(path)
        data = ByteArray(inputStream.available())
        inputStream.read(data)
        inputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return Base64.encodeToString(data, Base64.DEFAULT)
}

/**
 * 将文件转成base64
 */
fun imageFileToBase64(path: File): String {
    val inputStream: InputStream?
    var data: ByteArray? = null
    // 读取图片字节数组
    try {
        inputStream = FileInputStream(path)
        data = ByteArray(inputStream.available())
        inputStream.read(data)
        inputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return Base64.encodeToString(data, Base64.DEFAULT)
}

fun EditText.checkIsEmpty(context: Context, msg: String = "请完善资料"): Boolean {
    return if (text.toString().isEmpty()) {
        context.toast(msg)
        false
    } else true
}

fun TextView.checkIsEmpty(context: Context, msg: String = "请完善资料"): Boolean {
    return if (text.toString().isEmpty()) {
        context.toast(msg)
        false
    } else true
}

fun CheckBox.checkIsChecked(context: Context, msg: String = "请完善资料"): Boolean {
    return if (isChecked) {
        true
    } else {
        context.toast(msg)
        false
    }
}

fun checkIsEqual(context: Context, et1: EditText, et2: EditText, msg: String = "两次密码不一致"): Boolean {
    return if (et1.text.toString() == et2.text.toString()) {
        true
    } else {
        context.toast(msg)
        false
    }
}

/**
 * 图片加载
 */
fun SimpleDraweeView.loadImage(path: String) {
    if (Patterns.WEB_URL.matcher(path).matches()) {
        this.controller = getPicController(path)
    } else {
        this.controller = getPicController(BaseConstant.IMAGE_ADDRESS + path)
    }
}

fun getPicController(photoUrl: String): DraweeController {
    var request =
            ImageRequestBuilder.newBuilderWithSource(Uri.parse(photoUrl)).build()
//                    .setResizeOptions(ResizeOptions(400, 400)).build()
    return Fresco.newDraweeControllerBuilder()
            .setImageRequest(request)
            .setAutoPlayAnimations(true)
            .setTapToRetryEnabled(true).build()
}

/**
 * 上下拉刷新
 */
fun SmartRefreshLayout.refresh(refresh: () -> Unit, loadMore: () -> Unit) {
    setOnRefreshListener { refresh() }
    setOnLoadMoreListener { loadMore() }
}

/**
 * 上下拉刷新
 */
fun SmartRefreshLayout.finish() {
    finishRefresh()
    finishLoadMore()
}
