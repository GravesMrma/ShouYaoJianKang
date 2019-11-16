package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface MineView : BaseView {

    fun onUserInfoResult(result: LoginData)

    fun onMineResult(result: MineBean)

    fun onMineAdvResult(result: MineAdv)

    fun onIsRebateResult(result: IsRebateBean)
}