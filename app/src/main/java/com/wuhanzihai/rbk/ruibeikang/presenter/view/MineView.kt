package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.MallBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.MineAdv
import com.wuhanzihai.rbk.ruibeikang.data.entity.MineBean

interface MineView : BaseView {

    fun onUserInfoResult(result: LoginData)

    fun onMineResult(result: MineBean)

    fun onMineAdvResult(result: MineAdv)
}