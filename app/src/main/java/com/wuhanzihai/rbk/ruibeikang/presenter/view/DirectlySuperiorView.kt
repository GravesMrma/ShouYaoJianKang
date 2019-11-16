package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface DirectlySuperiorView : BaseView {

    fun onUplevelResult(result: UpLevelBean)

}