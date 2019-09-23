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

    fun healthInfoClass(): Observable<BaseResp<MutableList<HealthTitleBean>>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).healthInfoClass(NoParamReq())
    }

    fun healthInfoList(req:HealthListReq): Observable<BaseResp<HealthListBean>> {
        return RetrofitFactory.instance.create(InfoApi::class.java).healthInfoList(req)
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
}