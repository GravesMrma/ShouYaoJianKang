package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface ApplyCardView : BaseView {

    fun onApplyCard(){}

    fun onLevelResult(result: MutableList<RebateLevelBean>){}

    fun onApplyCardRecord(result: CardRecord){}

}