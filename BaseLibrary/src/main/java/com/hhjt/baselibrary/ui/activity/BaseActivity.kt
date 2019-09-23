package com.hhjt.baselibrary.ui.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.FrameLayout
import com.hhjt.baselibrary.R
import com.hhjt.baselibrary.common.AppManager
import com.jaeger.library.StatusBarUtil
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.jetbrains.anko.act
import org.jetbrains.anko.find

/*
    Activity基类，业务无关
 */
open class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }

    //获取Window中视图content
    val contentView: View
        get() {
            val content = find<FrameLayout>(android.R.id.content)
            return content.getChildAt(0)
        }
}
