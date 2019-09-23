package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface HealthInfoView :BaseView {

    fun onHealthTitleResult(result: MutableList<HealthTitleBean>){}

    fun onHealthListResult(result: HealthListBean){}

    fun onHealthBannerResult(result: HealthBannerBean){}

}