package com.wuhanzihai.rbk.ruibeikang.data.repository

import com.wuhanzihai.rbk.ruibeikang.data.api.UserApi
import com.hhjt.baselibrary.data.net.RetrofitFactory
import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.api.IndexApi
import com.wuhanzihai.rbk.ruibeikang.data.api.InfoApi
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable
import javax.inject.Inject

class InfoRepository @Inject constructor() {

    fun healthIndex(): Observable<BaseResp<HealthIndexBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).healthIndex(NoParamReq())
    }

    fun healthInfoClass(): Observable<BaseResp<MutableList<HealthTitleBean>>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).healthInfoClass(NoParamReq())
    }

    fun healthFoodClass(req:IdReq): Observable<BaseResp<HealthFoodBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).healthFoodClass(req)
    }

    fun healthInfoList(req:HealthListReq): Observable<BaseResp<HealthListBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).healthInfoList(req)
    }

    fun searchWords(): Observable<BaseResp<SearchBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).searchWords(NoParamIdReq())
    }

    fun searchInfo(req:SearchReq): Observable<BaseResp<HealthListBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).searchInfo(req)
    }

    fun searchClassWords(): Observable<BaseResp<SearchBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).searchClassWords(NoParamIdReq())
    }

    fun searchClass(req:SearchReq): Observable<BaseResp<HealthClassBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).searchClass(req)
    }

    fun articleDetail(req:ArticleDetailReq): Observable<BaseResp<ArticleDetailBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).articleDetail(req)
    }

    fun healthInfoBanner(req:HealthBannerReq): Observable<BaseResp<HealthBannerBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).healthInfoBanner(req)
    }

    fun healthHabitsList(): Observable<BaseResp<MutableList<HealthHabitsBean>>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).healthHabitsList(NoParamReq())
    }

    fun healthHabitsDetailList(req:HealthHabitsDetailReq): Observable<BaseResp<MutableList<HealthHabitsBean>>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).healthHabitsDetailList(req)
    }

    fun CollectAtr(req:CollectAtrReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(InfoApi::class.java).CollectAtr(req)
    }

    fun collectList(req:CollectListReq): Observable<BaseResp<MutableList<CollectBean>>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).collectList(req)
    }

    fun LikeAtr(req:LikeAtrReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(InfoApi::class.java).LikeAtr(req)
    }

    fun musicBanner(req: MusicBannerReq): Observable<BaseResp<MusicBannerBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).musicBanner(req)
    }

    fun musicClass(): Observable<BaseResp<MutableList<MusicClassBean>>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).musicClass(NoParamReq())
    }

    fun musicList(req:MusicReq): Observable<BaseResp<MusicDetailBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).musicList(req)
    }

    fun healthClass(req: NoParamPageReq): Observable<BaseResp<HealthClassBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).healthClass(req)
    }

    fun healthBanner(req: NoParamReq): Observable<BaseResp<HealthClassBannerBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).healthBanner(req)
    }

    fun healthClassDetail(req: HealthClassDetailReq): Observable<BaseResp<HealthClassDetailBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).healthClassDetail(req)
    }

    fun healthClassDetailMusic(req: HealthClassDetailMusicReq): Observable<BaseResp<HealthClassDetailMusicBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).healthClassDetailMusic(req)
    }
}