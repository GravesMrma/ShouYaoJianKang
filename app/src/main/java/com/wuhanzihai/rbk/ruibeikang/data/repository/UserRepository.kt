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

    fun phoneNumberList(req :PhoneNumberReq): Observable<BaseResp<PhoneNumberBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).phoneNumberList(req)
    }

    fun addVipCard(req:AddVipCardReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).addVipCard(req)
    }

    fun myDistribution(req:PhoneNumberReq): Observable<BaseResp<DistributionBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).myDistribution(req)
    }

    fun agrApply(req:AgrApplyReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).agrApply(req)
    }

    fun addBankCard(req:AddBankCardReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).addBankCard(req)
    }

    fun deleteBankCard(req:DeleteBankCardReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).deleteBankCard(req)
    }

    fun myBankCard(): Observable<BaseResp<BankCardBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).myBankCard(NoParamIdDisIdReq())
    }

    fun myDisbutor(): Observable<BaseResp<UpLevelBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).myDisbutor(NoParamIdReq())
    }

    fun myTeam(): Observable<BaseResp<MyTeamBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).myTeam(NoParamDisIdReq())
    }

    fun directData(req:DirectReq): Observable<BaseResp<DirectBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).directData(req)
    }

    fun inDirectData(req:DirectReq): Observable<BaseResp<DirectBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).inDirectData(req)
    }

    fun isRebate(): Observable<BaseResp<IsRebateBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).isRebate(NoParamIdReq())
    }

    fun rebateAddress(req:RebateAddressReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).rebateAddress(req)
    }
}