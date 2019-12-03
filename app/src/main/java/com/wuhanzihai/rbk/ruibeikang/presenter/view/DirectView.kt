package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface DirectView : BaseView {

    fun onDirectResult(result:DirectBean)

    fun onInDirectResult(result:DirectBean)

    fun onMyTeamResult(result:MyTeamBean)

}