package com.wuhanzihai.rbk.ruibeikang.service

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable

interface InfoService {

    fun healthIndex(): Observable<HealthIndexBean>

    fun healthInfoClass(): Observable<MutableList<HealthTitleBean>>

    fun healthFoodClass(req:IdReq): Observable<HealthFoodBean>

    fun healthInfoList(req: HealthListReq): Observable<HealthListBean>

    fun searchWords(): Observable<SearchBean>

    fun searchInfo(req: SearchReq): Observable<HealthListBean>

    fun searchClassWords(): Observable<SearchBean>

    fun searchClass(req: SearchReq): Observable<HealthClassBean>

    fun articleDetail(req: ArticleDetailReq): Observable<ArticleDetailBean>

    fun healthInfoBanner(req: HealthBannerReq): Observable<HealthBannerBean>

    fun healthHabitsList(): Observable<MutableList<HealthHabitsBean>>

    fun healthHabitsDetailList(req: HealthHabitsDetailReq): Observable<MutableList<HealthHabitsBean>>

    fun CollectAtr(req:CollectAtrReq): Observable<BaseData>

    fun collectList(req:CollectListReq): Observable<MutableList<CollectBean>>

    fun likeAtr(req:LikeAtrReq): Observable<BaseData>

    fun musicBanner(req: MusicBannerReq): Observable<MusicBannerBean>

    fun musicClass(): Observable<MutableList<MusicClassBean>>

    fun musicList(req:MusicReq): Observable<MusicDetailBean>

    fun healthClass(req: NoParamPageReq): Observable<HealthClassBean>

    fun healthBanner(): Observable<HealthClassBannerBean>

    fun healthClassDetail(req: HealthClassDetailReq): Observable<HealthClassDetailBean>

    fun healthClassDetailMusic(req: HealthClassDetailMusicReq): Observable<HealthClassDetailMusicBean>

    fun keyWords(): Observable<MineAdv>

}