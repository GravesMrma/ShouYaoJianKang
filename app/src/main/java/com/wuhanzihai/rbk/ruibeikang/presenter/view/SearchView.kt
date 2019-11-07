package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface SearchView : BaseView {

    fun onSearchBean(result: SearchBean)

    fun onSearchGoodsBean(result: GoodsResult)

    fun onHealthListResult(result: HealthListBean)

    fun onHealthClassResult(result: HealthClassBean)

    fun onMusicDetailResult(result: MusicDetailBean) {}

}