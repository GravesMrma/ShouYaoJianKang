package com.wuhanzihai.rbk.ruibeikang.data.repository

import com.wuhanzihai.rbk.ruibeikang.data.api.UserApi
import com.hhjt.baselibrary.data.net.RetrofitFactory
import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.tencent.bugly.crashreport.crash.CrashDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
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

    fun logistics(req: LogisticsReq): Observable<BaseResp<Logistics>> {
        return RetrofitFactory.instance.create(UserApi::class.java).logistics(req)
    }

    fun userAdv(): Observable<BaseResp<MineAdv>> {
        return RetrofitFactory.instance.create(UserApi::class.java).userAdv(NoParamReq())
    }

    fun mineBanner(): Observable<BaseResp<Banner>> {
        return RetrofitFactory.instance.create(UserApi::class.java).mineBanner(NoParamReq())
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

    fun phoneNumberList(req: PhoneNumberReq): Observable<BaseResp<PhoneNumberBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).phoneNumberList(req)
    }

    fun phoneNumberDetail(req: PhoneNumberReq): Observable<BaseResp<PhoneNumberBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).phoneNumberDetail(req)
    }

    fun addVipCard(req: AddVipCardReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).addVipCard(req)
    }

    fun myDistribution(req: PhoneNumberReq): Observable<BaseResp<DistributionBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).myDistribution(req)
    }

    fun agrApply(req: AgrApplyReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).agrApply(req)
    }

    fun addBankCard(req: AddBankCardReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).addBankCard(req)
    }

    fun deleteBankCard(req: DeleteBankCardReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).deleteBankCard(req)
    }

    fun myBankCard(): Observable<BaseResp<BankCardBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).myBankCard(NoParamIdDisIdReq())
    }

    fun myDisbutor(): Observable<BaseResp<UpLevelBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).myDisbutor(NoParamDisIdReq())
    }

    fun myTeam(): Observable<BaseResp<MyTeamBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).myTeam(NoParamDisIdReq())
    }

    fun directData(req: DirectReq): Observable<BaseResp<DirectBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).directData(req)
    }

    fun inDirectData(req: DirectReq): Observable<BaseResp<DirectBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).inDirectData(req)
    }

    fun isRebate(): Observable<BaseResp<IsRebateBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).isRebate(NoParamIdReq())
    }

    fun authRebate(): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).authRebate(NoParamIdReq())
    }

    fun rebateAddress(req: RebateAddressReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).rebateAddress(req)
    }

    fun applyLevel(): Observable<BaseResp<MutableList<RebateLevelBean>>> {
        return RetrofitFactory.instance.create(UserApi::class.java).applyLevel(NoParamIdReq())
    }

    fun applyLevelAction(req: ApplyLevelReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).applyLevelAction(req)
    }

    fun applyLevelRecord(req: NoParamIdDisIdPageReq): Observable<BaseResp<RebateLevelRecordBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).applyLevelRecord(req)
    }

    fun myCard(req: NoParamIdTypeReq): Observable<BaseResp<MyCardBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).myCard(req)
    }

    fun applyCardRecord(req: NoParamIdDisIdPageReq): Observable<BaseResp<CardRecord>> {
        return RetrofitFactory.instance.create(UserApi::class.java).applyCardRecord(req)
    }

    fun applyCash(req: ApplyCashReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).applyCash(req)
    }

    fun applyCashDetail(): Observable<BaseResp<CashDetailBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).applyCashDetail(NoParamIdReq())
    }

    fun applyCashRecord(req: NoParamIdPageReq): Observable<BaseResp<ApplyCashRecordBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).applyCashRecord(req)
    }

    fun applyCashList(req: ApplyCashListReq): Observable<BaseResp<ApplyCashListBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).applyCashList(req)
    }

    fun applyCashListDetail(): Observable<BaseResp<ApplyCashListDetail>> {
        return RetrofitFactory.instance.create(UserApi::class.java).applyCashListDetail(NoParamIdReq())
    }

    fun shareRecord(req: NoParamIdPageReq): Observable<BaseResp<ShareBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).shareRecord(req)
    }


    //  春雨医生

    fun doctorList(): Observable<BaseResp<MutableList<ArchivesBean>>> {
        return RetrofitFactory.instance.create(UserApi::class.java).doctorList(NoParamIdReq())
    }

    fun createDoctor(req: AddArchivesReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).createDoctor(req)
    }

    fun deleteDoctor(req: DelArchivesReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).deleteDoctor(req)
    }

    fun deleteRecord(req: NoParamOrderIdReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).deleteRecord(req)
    }

    fun editDoctor(req: EditArchivesReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).editDoctor(req)
    }

    fun createQuestion(fileReq: MutableList<File>, req: CreateDoctorReq): Observable<BaseResp<OrderIdBean>> {
        var map = HashMap<String, RequestBody>()
        for (file in fileReq) {
            map["file\"; filename=\"" + file.name] = RequestBody.create(MediaType.parse("image/jpg"), file)
        }
        map["content"] = RequestBody.create(MediaType.parse("application/json"), req.content)
        map["time"] = RequestBody.create(MediaType.parse("application/json"), req.time)
        map["token"] = RequestBody.create(MediaType.parse("application/json"), req.token)
        map["user_id"] = RequestBody.create(MediaType.parse("application/json"), req.user_id.toString())
        map["sign"] = RequestBody.create(MediaType.parse("application/json"), req.sign!!)
        return RetrofitFactory.instance.create(UserApi::class.java).createQuestion(map)
    }

    fun chosePeople(req: ChosePeopleReq): Observable<BaseResp<OrderIdBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).chosePeople(req)
    }

    fun addQuestion(fileReq: MutableList<File>, req: AddQuestionReq): Observable<BaseResp<IsRebateBean>> {
        var map = HashMap<String, RequestBody>()
        for (file in fileReq) {
            map["file\"; filename=\"" + file.name] = RequestBody.create(MediaType.parse("image/jpg"), file)
        }
        if (req.content.isNotEmpty()) {
        }
        map["content"] = RequestBody.create(MediaType.parse("application/json"), req.content)
        map["order_id"] = RequestBody.create(MediaType.parse("application/json"), req.order_id.toString())
        map["time"] = RequestBody.create(MediaType.parse("application/json"), req.time)
        map["token"] = RequestBody.create(MediaType.parse("application/json"), req.token)
        map["user_id"] = RequestBody.create(MediaType.parse("application/json"), req.user_id.toString())
        map["sign"] = RequestBody.create(MediaType.parse("application/json"), req.sign!!)
        return RetrofitFactory.instance.create(UserApi::class.java).addQuestion(map)
    }

    fun doctorRecord(req: NoParamIdReq): Observable<BaseResp<InterrogationBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).doctorRecord(req)
    }

    fun chatRecord(req: ChatRecordReq): Observable<BaseResp<ChatBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).chatRecord(req)
    }

    fun payInterrogation(req: PayInterrogationReq): Observable<BaseResp<OrderPayBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).payInterrogation(req)
    }

    fun applyCard(req: ApplyCardReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).applyCard(req)
    }

    fun archivesDetail(req: ArchivesDetailReq): Observable<BaseResp<ArchivesDetail>> {
        return RetrofitFactory.instance.create(UserApi::class.java).archivesDetail(req)
    }

    fun doctorDetail(req: NoParamOrderReq): Observable<BaseResp<DoctorDetail>> {
        return RetrofitFactory.instance.create(UserApi::class.java).doctorDetail(req)
    }
}