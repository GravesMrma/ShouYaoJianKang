package com.wuhanzihai.rbk.ruibeikang.presenter

import com.google.gson.Gson
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.ArticleDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.MallBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import com.wuhanzihai.rbk.ruibeikang.service.impl.InfoServiceImpl
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class UnifiedWebPresenter @Inject constructor() : BasePresenter<ArticleDetailView>() {

    @Inject
    lateinit var infoServiceImpl: InfoServiceImpl

    fun articleDetail(req: ArticleDetailReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.articleDetail(req)
                .excute(object : BaseSubscriber<ArticleDetailBean>(mView) {
                    override fun onNext(t: ArticleDetailBean) {
                        super.onNext(t)
                        mView.onArticleDetailResult(t)
                    }
                }, lifecycleProvider)
    }

    fun CollectAtr(req: CollectAtrReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.CollectAtr(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onColloectResult()
                    }
                }, lifecycleProvider)
    }

    fun likeAtr(req: LikeAtrReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.likeAtr(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onLikeResult()
                    }
                }, lifecycleProvider)
    }
}