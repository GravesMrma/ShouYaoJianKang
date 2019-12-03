package com.wuhanzihai.rbk.ruibeikang.service.impl

import com.hhjt.baselibrary.ext.convert
import com.wuhanzihai.rbk.ruibeikang.data.repository.UserRepository
import com.wuhanzihai.rbk.ruibeikang.service.UserService
import com.hhjt.baselibrary.ext.convertT
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {

    @Inject
    lateinit var repository: UserRepository

    override fun sendCode(getCodeReq: GetCodeReq): Observable<BaseData> {
        return repository.sendCode(getCodeReq).convertT()
    }

    override fun login(loginReq: LoginReq): Observable<LoginData> {
        return repository.login(loginReq).convert()
    }

    override fun activation(req: ActivationReq): Observable<BaseData> {
        return repository.activation(req).convertT()
    }

    override fun saveInfo(userInfoReq: UserInfoReq): Observable<BaseData> {
        return repository.saveInfo(userInfoReq).convertT()
    }

    override fun getUserInfo(): Observable<LoginData> {
        return repository.getUserInfo().convert()
    }

    override fun addAddress(req: AddAddressReq): Observable<BaseData> {
        return repository.addAddress(req).convertT()
    }

    override fun getAddressList(): Observable<MutableList<AddressBean>> {
        return repository.getAddressList().convert()
    }

    override fun getDefAddress(): Observable<AddressBean> {
        return repository.getDefAddress().convert()
    }

    override fun getAddressInfo(req: AddressReq): Observable<AddressBean> {
        return repository.getAddressInfo(req).convert()
    }

    override fun upAddress(req: UpdateAddressReq): Observable<BaseData> {
        return repository.upAddress(req).convertT()
    }

    override fun getOrder(req: OrderReq): Observable<OrderBean> {
        return repository.getOrder(req).convert()
    }

    override fun userAdv(): Observable<MineAdv> {
        return repository.userAdv().convert()
    }

    override fun mineBanner(): Observable<Banner> {
        return repository.mineBanner().convert()
    }

    override fun mineIndex(): Observable<MineBean> {
        return repository.mineIndex().convert()
    }

    override fun getVersion(): Observable<VersionBean> {
        return repository.getVersion().convert()
    }

    override fun disbutorIndex(): Observable<RebateBean> {
        return repository.disbutorIndex().convert()
    }

    override fun phoneNumberList(req: PhoneNumberReq): Observable<PhoneNumberBean> {
        return repository.phoneNumberList(req).convert()
    }

    override fun phoneNumberDetail(req: PhoneNumberReq): Observable<PhoneNumberBean> {
        return repository.phoneNumberDetail(req).convert()
    }

    override fun addVipCard(req: AddVipCardReq): Observable<BaseData> {
        return repository.addVipCard(req).convertT()
    }

    override fun myDistribution(req: PhoneNumberReq): Observable<DistributionBean> {
        return repository.myDistribution(req).convert()
    }

    override fun agrApply(req: AgrApplyReq): Observable<BaseData> {
        return repository.agrApply(req).convertT()
    }


    override fun addBankCard(req: AddBankCardReq): Observable<BaseData> {
        return repository.addBankCard(req).convertT()
    }

    override fun myBankCard(): Observable<BankCardBean> {
        return repository.myBankCard().convert()
    }

    override fun myDisbutor(): Observable<UpLevelBean> {
        return repository.myDisbutor().convert()
    }

    override fun deleteBankCard(req: DeleteBankCardReq): Observable<BaseData> {
        return repository.deleteBankCard(req).convertT()
    }

    override fun myTeam(): Observable<MyTeamBean> {
        return repository.myTeam().convert()
    }

    override fun directData(req: DirectReq): Observable<DirectBean> {
        return repository.directData(req).convert()
    }

    override fun inDirectData(req: DirectReq): Observable<DirectBean> {
        return repository.inDirectData(req).convert()
    }

    override fun isRebate(): Observable<IsRebateBean> {
        return repository.isRebate().convert()
    }

    override fun authRebate(): Observable<BaseData> {
        return repository.authRebate().convertT()
    }

    override fun rebateAddress(req: RebateAddressReq): Observable<BaseData> {
        return repository.rebateAddress(req).convertT()
    }

    override fun doctorList(): Observable<MutableList<ArchivesBean>> {
        return repository.doctorList().convert()
    }

    override fun createDoctor(req: AddArchivesReq): Observable<IsRebateBean> {
        return repository.createDoctor(req).convert()
    }

    override fun deleteDoctor(req: DelArchivesReq): Observable<BaseData> {
        return repository.deleteDoctor(req).convertT()
    }

    override fun deleteRecord(req: NoParamOrderIdReq): Observable<BaseData> {
        return repository.deleteRecord(req).convertT()
    }

    override fun editDoctor(req: NoParamIdReq): Observable<IsRebateBean> {
        return repository.editDoctor(req).convert()
    }

    override fun createQuestion(fileReq: MutableList<File>, req: CreateDoctorReq): Observable<OrderIdBean> {
        return repository.createQuestion(fileReq, req).convert()
    }

    override fun chosePeople(req: ChosePeopleReq): Observable<OrderIdBean> {
        return repository.chosePeople(req).convert()
    }

    override fun addQuestion(fileReq: MutableList<File>,req: AddQuestionReq): Observable<IsRebateBean> {
        return repository.addQuestion(fileReq,req).convert()
    }

    override fun doctorRecord(req: NoParamIdReq): Observable<InterrogationBean> {
        return repository.doctorRecord(req).convert()
    }

    override fun chatRecord(req: ChatRecordReq): Observable<ChatBean> {
        return repository.chatRecord(req).convert()
    }

    override fun applyLevel(): Observable<MutableList<RebateLevelBean>> {
        return repository.applyLevel().convert()
    }

    override fun applyLevelAction(req: ApplyLevelReq): Observable<BaseData> {
        return repository.applyLevelAction(req).convertT()
    }

    override fun applyLevelRecord(req: NoParamIdDisIdPageReq): Observable<RebateLevelRecordBean> {
        return repository.applyLevelRecord(req).convert()
    }

    override fun myCard(req: NoParamIdTypeReq): Observable<MyCardBean> {
        return repository.myCard(req).convert()
    }

    override fun payInterrogation(req: PayInterrogationReq): Observable<OrderPayBean> {
        return repository.payInterrogation(req).convert()
    }

    override fun applyCard(req: ApplyCardReq): Observable<BaseData> {
        return repository.applyCard(req).convertT()
    }

    override fun applyCardRecord(req: NoParamIdDisIdPageReq): Observable<CardRecord> {
        return repository.applyCardRecord(req).convert()
    }

    override fun applyCash(req: ApplyCashReq): Observable<BaseData> {
        return repository.applyCash(req).convertT()
    }

    override fun applyCashDetail(): Observable<CashDetailBean> {
        return repository.applyCashDetail().convert()
    }

    override fun applyCashList(req: ApplyCashListReq): Observable<ApplyCashListBean> {
        return repository.applyCashList(req).convert()
    }

    override fun applyCashListDetail(): Observable<ApplyCashListDetail> {
        return repository.applyCashListDetail().convert()
    }

    override fun applyCashRecord(req: NoParamIdPageReq): Observable<ApplyCashRecordBean> {
        return repository.applyCashRecord(req).convert()
    }

    override fun shareRecord(req: NoParamIdPageReq): Observable<ShareBean> {
        return repository.shareRecord(req).convert()
    }
}
