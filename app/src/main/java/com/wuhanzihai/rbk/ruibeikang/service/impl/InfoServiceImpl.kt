package com.wuhanzihai.rbk.ruibeikang.service.impl

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.ext.convert
import com.wuhanzihai.rbk.ruibeikang.data.repository.UserRepository
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.data.repository.InfoRepository
import com.wuhanzihai.rbk.ruibeikang.service.IndexService
import com.wuhanzihai.rbk.ruibeikang.service.InfoService
import io.reactivex.Observable
import javax.inject.Inject

class InfoServiceImpl @Inject constructor() : InfoService {

    @Inject
    lateinit var repository: InfoRepository

    override fun healthInfoClass(): Observable<MutableList<HealthTitleBean>> {
        return repository.healthInfoClass().convert()
    }

    override fun healthInfoList(req: HealthListReq): Observable<HealthListBean> {
        return repository.healthInfoList(req).convert()
    }

    override fun articleDetail(req: ArticleDetailReq): Observable<ArticleDetailBean> {
        return repository.articleDetail(req).convert()
    }

    override fun healthInfoBanner(req: HealthBannerReq): Observable<HealthBannerBean> {
        return repository.healthInfoBanner(req).convert()
    }

    override fun healthHabitsList(): Observable<MutableList<HealthHabitsBean>> {
        return repository.healthHabitsList().convert()
    }

    override fun healthHabitsDetailList(req: HealthHabitsDetailReq): Observable<MutableList<HealthHabitsBean>> {
        return repository.healthHabitsDetailList(req).convert()
    }
}
