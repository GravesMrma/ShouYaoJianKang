package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface MainView :BaseView {

    fun onIndexDate(result: IndexBean){}

    fun onIndexAdv(result: IndexAdvBean){}

    fun onIndexBanner(result: IndexBannerBean){}

    fun onIndexClock(result: IndexClockBean){}

    fun onUserInfoResult(result: LoginData){}

}