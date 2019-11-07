package com.hhjt.baselibrary.ui.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import com.hhjt.baselibrary.common.BaseApplication
import com.hhjt.baselibrary.widgets.ProgressLoading
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.component.DaggerActivityComponent
import com.kotlin.base.injection.module.ActivityModule
import com.kotlin.base.injection.module.LifecycleProviderModule
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.presenter.view.BaseView
import org.jetbrains.anko.toast
import javax.inject.Inject

/*
    Activity基类，业务相关
 */
abstract class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity(), BaseView {

    //Presenter泛型，Dagger注入
    @Inject
    lateinit var mPresenter: T

    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        initActivityInjection()
        injectComponent()

        //初始加载框
        mLoadingDialog = ProgressLoading.create(this)
    }

    /*
        Dagger注册
     */
    protected abstract fun injectComponent()

    /*
        初始Activity Component
     */
    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
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

    override fun onDataIsNull() {
        toast("暂无数据")
    }

    override fun onTokenInvalid(text: String, code: Int) {
        toast(text)
    }
}
