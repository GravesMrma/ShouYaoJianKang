package com.wuhanzihai.rbk.ruibeikang.service

import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.LoginReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.TimeReq
import io.reactivex.Observable

interface IndexService {

    fun login(loginReq: LoginReq): Observable<LoginData>

    fun indexInfo(): Observable<IndexBean>

    fun indexBanner(): Observable<IndexBannerBean>

    fun indexAdv(): Observable<IndexAdvBean>

    fun indexClock(): Observable<IndexClockBean>

}