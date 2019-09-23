package com.hhjt.baselibrary.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment

/*
    Fragment基类，业务无关
 */
open class BaseFragment : RxFragment() {

    /**
     * Fragment的view是否已创建
     */
    private var mIsViewCreated = false

    private var isVisibleToUser = false;// 当前可见状态
    private var isLastVisibleToUser = false;// 最后一次可见状态(onPause前的最后一次状态)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mIsViewCreated = true
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mIsViewCreated = false
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!mIsViewCreated)
        //view没有创建的时候不进行操作
        {
            return
        }

        isLastVisibleToUser = isVisibleToUser;
        onVisibleToUser(isLastVisibleToUser);
        setChildVisibleToUser(isLastVisibleToUser);
    }


    /**
     * 可见
     */
    open fun onVisible() {

    }

    /**
     * fragment不可见的时候操作,onPause的时候,以及不可见的时候调用
     */
    open fun onHidden() {

    }

    /**
     * 和activity的onResume绑定，Fragment初始化的时候必调用， 但切换fragment的hide和visible的时候可能不会调用！
     */
    override fun onResume() {
        super.onResume()
        if ((mIsViewCreated && userVisibleHint) || (isLastVisibleToUser && getParentFragmentLastVisibleToUser())) {
            onVisibleToUser(true);
        }
    }

    override fun onPause() {
        if (isLastVisibleToUser) {
            onVisibleToUser(false);
        }
        super.onPause()
    }

    /**
     * 获取ParentFragment isLastVisibleToUser状态(没有则为true)
     *
     * @return ParentFragment isLastVisibleToUser
     */
    private fun getParentFragmentLastVisibleToUser(): Boolean {
        val parentFragment: Fragment? = parentFragment
        if (parentFragment != null) {
            if (parentFragment is BaseFragment) {
                return parentFragment.isLastVisibleToUser
            }
        }
        return true;
    }

    /**
     * 设置子Fragment显示状态
     *
     * @param isVisibleToUser isVisibleToUser
     */
    private fun setChildVisibleToUser(isVisibleToUser: Boolean) {
        val fragmentList = childFragmentManager.fragments;
        if (!fragmentList.isEmpty()) {
            for (fragment: Fragment in fragmentList) {
                if (fragment is BaseFragment) {
                    if (fragment.isLastVisibleToUser) {
                        fragment.onVisibleToUser(isVisibleToUser);
                    }
                }
            }
        }
    }

    /**
     * 默认fragment创建的时候是可见的，但是不会调用该方法！切换可见状态的时候会调用， 但是调用onResume，onPause的时候却不会调用
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isLastVisibleToUser = !hidden
        onVisibleToUser(isLastVisibleToUser)
    }

    /**
     * 对用户可见/不可见时执行的操作(类似Activity的onResume/onPause方法)
     *
     * @param isVisibleToUser 是否对用户显示可见
     */
    private fun onVisibleToUser(isVisibleToUser: Boolean) {
        if (this.isVisibleToUser == isVisibleToUser) {
            return
        }
        this.isVisibleToUser = isVisibleToUser;
        mIsViewCreated = false
        if (isVisibleToUser) {
            isLastVisibleToUser = true
            onVisible()
        } else {
            onHidden()
        }

    }

}
