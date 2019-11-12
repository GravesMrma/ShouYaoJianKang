package com.wuhanzihai.rbk.ruibeikang.data.repository

import com.wuhanzihai.rbk.ruibeikang.data.api.UserApi
import com.hhjt.baselibrary.data.net.RetrofitFactory
import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable
import javax.inject.Inject

class UserRepository @Inject constructor() {

    fun sendCode(getCodeReq: GetCodeReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).sendCode(getCodeReq)
    }

    fun login(loginReq: LoginReq): Observable<BaseResp<LoginData>> {
        return RetrofitFactory.instance.create(UserApi::class.java).login(loginReq)
    }

    fun activation(getCodeReq: ActivationReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).activation(getCodeReq)
    }

    fun saveInfo(userInfoReq: UserInfoReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).saveInfo(userInfoReq)
    }

    fun getUserInfo(): Observable<BaseResp<LoginData>> {
        return RetrofitFactory.instance.create(UserApi::class.java).getUserInfo(GetUserInfoReq())
    }

    fun addAddress(req: AddAddressReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).addAddress(req)
    }

    fun getAddressList(): Observable<BaseResp<MutableList<AddressBean>>> {
        return RetrofitFactory.instance.create(UserApi::class.java).getAddressList(NoParamIdReq())
    }

    fun getDefAddress(): Observable<BaseResp<AddressBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).getDefAddress(NoParamIdReq())
    }

    fun getAddressInfo(req: AddressReq): Observable<BaseResp<AddressBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).getAddressInfo(req)
    }

    fun upAddress(req: UpdateAddressReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).upAddress(req)
    }

    fun getOrder(req: OrderReq): Observable<BaseResp<OrderBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).getOrder(req)
    }

    fun userAdv(): Observable<BaseResp<MineAdv>> {
        return RetrofitFactory.instance.create(UserApi::class.java).userAdv(NoParamReq())
    }

    fun mineIndex(): Observable<BaseResp<MineBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).mineIndex(NoParamIdReq())
    }

    fun getVersion(): Observable<BaseResp<VersionBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).getVersion(NoParamReq())
    }

    // 分销
    fun disbutorIndex(): Observable<BaseResp<RebateBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).disbutorIndex(NoParamIdReq())
    }

    fun disbutorIndex1(): Observable<BaseResp<MineBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).disbutorIndex1(NoParamIdReq())
    }

    fun disbutorIndex2(): Observable<BaseResp<MineBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).disbutorIndex2(NoParamIdReq())
    }

    fun disbutorIndex3(): Observable<BaseResp<MineBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).disbutorIndex3(NoParamIdReq())
    }

    fun disbutorIndex4(): Observable<BaseResp<MineBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).disbutorIndex4(NoParamIdReq())
    }

    fun disbutorIndex5(): Observable<BaseResp<MineBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).disbutorIndex5(NoParamIdReq())
    }

    fun disbutorIndex6(): Observable<BaseResp<MineBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).disbutorIndex6(NoParamIdReq())
    }

    fun disbutorIndex7(): Observable<BaseResp<MineBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).disbutorIndex7(NoParamIdReq())
    }

    fun disbutorIndex8(): Observable<BaseResp<MineBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).disbutorIndex8(NoParamIdReq())
    }

    fun disbutorIndex9(): Observable<BaseResp<MineBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).disbutorIndex9(NoParamIdReq())
    }
}