package com.hhjt.baselibrary.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hhjt.baselibrary.common.BaseApplication
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.widgets.ProgressLoading
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.component.DaggerActivityComponent
import com.kotlin.base.injection.module.ActivityModule
import com.kotlin.base.injection.module.LifecycleProviderModule
import com.hhjt.baselibrary.presenter.view.BaseView
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/*
    Fragment基类，业务相关
 */
abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading

    private val TAG = this.javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        initActivityInjection()
        injectComponent()

        //初始加载框
        mLoadingDialog = ProgressLoading.create(context!!)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /*
        Dagger注册
     */
    protected abstract fun injectComponent()

    /*
        初始化Activity级别Component
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

    override fun onDataIsNull() {
        toast("暂无数据")
    }

    override fun onTokenInvalid(text: String, code: Int) {
        toast(text)
    }
}
