package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface HealthClassView :BaseView {

    fun onHealthClassResult(result:HealthClassBean)

    fun onHealthBannerResult(result:HealthClassBannerBean)

    fun onHealthClassDetailResult(result:HealthClassDetailBean)

    fun onHealthClassDetailMusicResult(result:HealthClassDetailMusicBean)
}