package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsClassBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.MallBean

interface GoodsClassView :BaseView {

    fun onGoodsClassResult(result: MutableList<GoodsClassBean>)

}