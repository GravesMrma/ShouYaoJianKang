package com.wuhanzihai.rbk.ruibeikang.presenter

import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsResult
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthClassBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthListBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.SearchBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.SearchReq
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SearchView
import com.wuhanzihai.rbk.ruibeikang.service.impl.InfoServiceImpl
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import javax.inject.Inject

class SearchPresenter @Inject constructor() : BasePresenter<SearchView>() {

    @Inject
    lateinit var mallServiceImpl: MallServiceImpl

    @Inject
    lateinit var infoServiceImpl: InfoServiceImpl

    //   商品搜索热词
    fun searchGoodsWords() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.searchWords()
                .excute(object : BaseSubscriber<SearchBean>(mView) {
                    override fun onNext(t: SearchBean) {
                        super.onNext(t)
                        mView.onSearchBean(t)
                    }
                }, lifecycleProvider)
    }

    //   商品搜索
    fun searchGoods(req: SearchReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.searchGoods(req)
                .excute(object : BaseSubscriber<GoodsResult>(mView) {
                    override fun onNext(t: GoodsResult) {
                        super.onNext(t)
                        mView.onSearchGoodsBean(t)
                    }
                }, lifecycleProvider)
    }

    // 课程搜索 热词
    fun searchClassWords() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.searchClassWords()
                .excute(object : BaseSubscriber<SearchBean>(mView) {
                    override fun onNext(t: SearchBean) {
                        super.onNext(t)
                        mView.onSearchBean(t)
                    }
                }, lifecycleProvider)
    }

    // 课程搜索
    fun searchClass(req: SearchReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.searchClass(req)
                .excute(object : BaseSubscriber<HealthClassBean>(mView) {
                    override fun onNext(t: HealthClassBean) {
                        super.onNext(t)
                        mView.onHealthClassResult(t)
                    }
                }, lifecycleProvider)
    }

    // 文章搜索 热词
    fun searchInfoWords() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.searchWords()
                .excute(object : BaseSubscriber<SearchBean>(mView) {
                    override fun onNext(t: SearchBean) {
                        super.onNext(t)
                        mView.onSearchBean(t)
                    }
                }, lifecycleProvider)
    }

    // 文章搜索
    fun searchInfo(req: SearchReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.searchInfo(req)
                .excute(object : BaseSubscriber<HealthListBean>(mView) {
                    override fun onNext(t: HealthListBean) {
                        super.onNext(t)
                        mView.onHealthListResult(t)
                    }
                }, lifecycleProvider)
    }
}