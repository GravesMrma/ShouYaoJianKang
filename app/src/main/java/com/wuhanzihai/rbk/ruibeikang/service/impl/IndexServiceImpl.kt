package com.wuhanzihai.rbk.ruibeikang.service.impl

import com.hhjt.baselibrary.ext.convert
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GetCodeReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.LoginReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.TimeReq
import com.wuhanzihai.rbk.ruibeikang.data.repository.IndexRepository
import com.wuhanzihai.rbk.ruibeikang.data.repository.InfoRepository
import com.wuhanzihai.rbk.ruibeikang.service.IndexService
import io.reactivex.Observable
import javax.inject.Inject

class IndexServiceImpl @Inject constructor() : IndexService {

    @Inject
    lateinit var repository: IndexRepository

    override fun login(loginReq: LoginReq): Observable<LoginData> {
        return repository.login(loginReq).convert()
    }

    override fun indexInfo(): Observable<IndexBean> {
        return repository.indexInfo().convert()
    }

    override fun indexBanner(): Observable<IndexBannerBean> {
        return repository.indexBanner().convert()
    }

    override fun indexAdv(): Observable<IndexAdvBean> {
        return repository.indexAdv().convert()
    }

    override fun indexClock(): Observable<IndexClockBean> {
        return repository.indexClock().convert()
    }
}
