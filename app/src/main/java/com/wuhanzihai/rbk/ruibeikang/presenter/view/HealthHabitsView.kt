package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface HealthHabitsView :BaseView {

    fun onHealthHabitsResult(result: MutableList<HealthHabitsBean>){}

    fun onHealthHabitsDetailResult(result: MutableList<HealthHabitsBean>){}

}