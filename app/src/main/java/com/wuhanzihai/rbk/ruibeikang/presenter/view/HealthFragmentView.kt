package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthIndexBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData

interface HealthFragmentView : BaseView {

    fun onHealthResult(result: HealthIndexBean)

}