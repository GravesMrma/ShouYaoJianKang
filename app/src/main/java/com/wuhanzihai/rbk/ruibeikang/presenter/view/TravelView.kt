package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.MallBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.TravelBean

interface TravelView : BaseView {

    fun onTravelResult(result: TravelBean)

}