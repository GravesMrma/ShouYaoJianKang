package com.wuhanzihai.rbk.ruibeikang.service

import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable
import java.io.File

interface UserService {

    fun sendCode(getCodeReq: GetCodeReq): Observable<BaseData>

    fun login(loginReq: LoginReq): Observable<LoginData>

    fun activation(getCodeReq: ActivationReq): Observable<BaseData>

    fun saveInfo(userInfoReq: UserInfoReq): Observable<BaseData>

    fun getUserInfo(): Observable<LoginData>

    fun addAddress(req: AddAddressReq): Observable<BaseData>

    fun getAddressList(): Observable<MutableList<AddressBean>>

    fun getDefAddress(): Observable<AddressBean>

    fun getAddressInfo(req: AddressReq): Observable<AddressBean>

    fun upAddress(req: UpdateAddressReq): Observable<BaseData>

    fun getOrder(req: OrderReq): Observable<OrderBean>

    fun userAdv(): Observable<MineAdv>

    fun mineBanner(): Observable<Banner>

    fun mineIndex(): Observable<MineBean>

    fun getVersion(): Observable<VersionBean>

    fun disbutorIndex(): Observable<RebateBean>

    fun phoneNumberList(req: PhoneNumberReq): Observable<PhoneNumberBean>

    fun phoneNumberDetail(req: PhoneNumberReq): Observable<PhoneNumberBean>

    fun addVipCard(req: AddVipCardReq): Observable<BaseData>

    fun myDistribution(req: PhoneNumberReq): Observable<DistributionBean>

    fun agrApply(req: AgrApplyReq): Observable<BaseData>

    fun addBankCard(req: AddBankCardReq): Observable<BaseData>

    fun deleteBankCard(req: DeleteBankCardReq): Observable<BaseData>

    fun myBankCard(): Observable<BankCardBean>

    fun myDisbutor(): Observable<UpLevelBean>

    fun myTeam(): Observable<MyTeamBean>

    fun directData(req: DirectReq): Observable<DirectBean>

    fun inDirectData(req: DirectReq): Observable<DirectBean>

    fun isRebate(): Observable<IsRebateBean>

    fun authRebate(): Observable<BaseData>

    fun rebateAddress(req: RebateAddressReq): Observable<BaseData>

    fun doctorList(): Observable<MutableList<ArchivesBean>>

    fun createDoctor(req: AddArchivesReq): Observable<IsRebateBean>

    fun deleteDoctor(req: DelArchivesReq): Observable<BaseData>

    fun deleteRecord(req: NoParamOrderIdReq): Observable<BaseData>

    fun editDoctor(req: NoParamIdReq): Observable<IsRebateBean>

    fun createQuestion(fileReq: MutableList<File>, req: CreateDoctorReq): Observable<OrderIdBean>

    fun chosePeople(req: ChosePeopleReq): Observable<OrderIdBean>

    fun addQuestion(fileReq: MutableList<File>,req: AddQuestionReq): Observable<IsRebateBean>

    fun doctorRecord(req: NoParamIdReq): Observable<InterrogationBean>

    fun chatRecord(req: ChatRecordReq): Observable<ChatBean>

    fun applyLevel(): Observable<MutableList<RebateLevelBean>>

    fun applyLevelAction(req: ApplyLevelReq): Observable<BaseData>

    fun applyLevelRecord(req: NoParamIdDisIdPageReq): Observable<RebateLevelRecordBean>

    fun myCard(req:NoParamIdTypeReq): Observable<MyCardBean>

    fun applyCardRecord(req:NoParamIdDisIdPageReq): Observable<CardRecord>

    fun payInterrogation(req:PayInterrogationReq): Observable<OrderPayBean>

    fun applyCard(req: ApplyCardReq): Observable<BaseData>

    fun applyCash(req: ApplyCashReq): Observable<BaseData>

    fun applyCashDetail(): Observable<CashDetailBean>

    fun applyCashRecord(req:NoParamIdPageReq): Observable<ApplyCashRecordBean>

    fun applyCashList(req:ApplyCashListReq): Observable<ApplyCashListBean>

    fun applyCashListDetail(): Observable<ApplyCashListDetail>

    fun shareRecord(req:NoParamIdPageReq): Observable<ShareBean>

}