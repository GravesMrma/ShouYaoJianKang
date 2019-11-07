package com.wuhanzihai.rbk.ruibeikang.presenter

import com.google.gson.Gson
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import com.wuhanzihai.rbk.ruibeikang.service.impl.InfoServiceImpl
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class MusicPresenter @Inject constructor() : BasePresenter<MusicView>() {

    @Inject
    lateinit var infoServiceImpl: InfoServiceImpl

    fun musicBanner(req: MusicBannerReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.musicBanner(req)
                .excute(object : BaseSubscriber<MusicBannerBean>(mView) {
                    override fun onNext(t: MusicBannerBean) {
                        super.onNext(t)
                        mView.onMusicBannerResult(t)
                    }
                }, lifecycleProvider)
    }

    fun musicClass() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.musicClass()
                .excute(object : BaseSubscriber<MutableList<MusicClassBean>>(mView) {
                    override fun onNext(t: MutableList<MusicClassBean>) {
                        super.onNext(t)
                        mView.onMusicClassResult(t)
                    }
                }, lifecycleProvider)
    }

    fun musicList(req: MusicReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.musicList(req)
                .excute(object : BaseSubscriber<MusicDetailBean>(mView) {
                    override fun onNext(t: MusicDetailBean) {
                        super.onNext(t)
                        mView.onMusicDetailResult(t)
                    }
                }, lifecycleProvider)
    }
}