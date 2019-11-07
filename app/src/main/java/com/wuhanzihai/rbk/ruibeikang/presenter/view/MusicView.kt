package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface MusicView : BaseView {

    fun onMusicClassResult(result: MutableList<MusicClassBean>) {}

    fun onMusicDetailResult(result: MusicDetailBean) {}

    fun onMusicBannerResult(result: MusicBannerBean) {}

}