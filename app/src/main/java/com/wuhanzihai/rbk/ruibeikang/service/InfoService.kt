package com.wuhanzihai.rbk.ruibeikang.service

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable

interface InfoService {

    fun healthInfoClass(): Observable<MutableList<HealthTitleBean>>

    fun healthInfoList(req: HealthListReq): Observable<HealthListBean>

    fun articleDetail(req: ArticleDetailReq): Observable<ArticleDetailBean>

    fun healthInfoBanner(req: HealthBannerReq): Observable<HealthBannerBean>

    fun healthHabitsList(): Observable<MutableList<HealthHabitsBean>>

    fun healthHabitsDetailList(req: HealthHabitsDetailReq): Observable<MutableList<HealthHabitsBean>>

}