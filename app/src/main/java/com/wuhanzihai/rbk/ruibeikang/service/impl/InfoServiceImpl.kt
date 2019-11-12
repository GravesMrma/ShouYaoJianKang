package com.wuhanzihai.rbk.ruibeikang.service.impl

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.ext.convert
import com.hhjt.baselibrary.ext.convertT
import com.hhjt.baselibrary.rx.BaseData
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

    override fun healthIndex(): Observable<HealthIndexBean> {
        return repository.healthIndex().convert()
    }

    override fun healthInfoClass(): Observable<MutableList<HealthTitleBean>> {
        return repository.healthInfoClass().convert()
    }

    override fun healthFoodClass(req: IdReq): Observable<HealthFoodBean> {
        return repository.healthFoodClass(req).convert()
    }

    override fun healthInfoList(req: HealthListReq): Observable<HealthListBean> {
        return repository.healthInfoList(req).convert()
    }

    override fun searchWords(): Observable<SearchBean> {
        return repository.searchWords().convert()
    }

    override fun searchInfo(req: SearchReq): Observable<HealthListBean> {
        return repository.searchInfo(req).convert()
    }

    override fun searchClassWords(): Observable<SearchBean> {
        return repository.searchClassWords().convert()
    }

    override fun searchClass(req: SearchReq): Observable<HealthClassBean> {
        return repository.searchClass(req).convert()
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

    override fun CollectAtr(req: CollectAtrReq): Observable<BaseData> {
        return repository.CollectAtr(req).convertT()
    }

    override fun collectList(req: CollectListReq): Observable<MutableList<CollectBean>> {
        return repository.collectList(req).convert()
    }

    override fun likeAtr(req: LikeAtrReq): Observable<BaseData> {
        return repository.LikeAtr(req).convertT()
    }

    override fun musicBanner(req: MusicBannerReq): Observable<MusicBannerBean> {
        return repository.musicBanner(req).convert()
    }

    override fun musicClass(): Observable<MutableList<MusicClassBean>> {
        return repository.musicClass().convert()
    }

    override fun musicList(req: MusicReq): Observable<MusicDetailBean> {
        return repository.musicList(req).convert()
    }

    override fun healthClass(req: NoParamPageReq): Observable<HealthClassBean> {
        return repository.healthClass(req).convert()
    }

    override fun healthBanner(): Observable<HealthClassBannerBean> {
        return repository.healthBanner(NoParamReq()).convert()
    }

    override fun healthClassDetail(req: HealthClassDetailReq): Observable<HealthClassDetailBean> {
        return repository.healthClassDetail(req).convert()
    }

    override fun healthClassDetailMusic(req: HealthClassDetailMusicReq): Observable<HealthClassDetailMusicBean> {
        return repository.healthClassDetailMusic(req).convert()
    }

    override fun keyWords(): Observable<MineAdv> {
        return repository.tagWords().convert()
    }
}
