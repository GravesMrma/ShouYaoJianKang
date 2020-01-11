package com.wuhanzihai.rbk.ruibeikang.data.api

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface UserApi {

    @POST("Entrance/loginsendsms")
    fun sendCode(@Body req: GetCodeReq): Observable<BaseData>

    @POST("Entrance/quickLogin")
    fun login(@Body req: LoginReq): Observable<BaseResp<LoginData>>

    @POST("User/usertocard")
    fun activation(@Body req: ActivationReq): Observable<BaseData>

    @POST("User/saveinfo")
    fun saveInfo(@Body userInfoReq: UserInfoReq): Observable<BaseData>

    @POST("user/userinfo")
    fun getUserInfo(@Body req: GetUserInfoReq): Observable<BaseResp<LoginData>>

    @POST("User/useradd")
    fun addAddress(@Body req: AddAddressReq): Observable<BaseData>

    @POST("User/addresslist")
    fun getAddressList(@Body req: NoParamIdReq): Observable<BaseResp<MutableList<AddressBean>>>

    @POST("User/addressdefault")
    fun getDefAddress(@Body req: NoParamIdReq): Observable<BaseResp<AddressBean>>

    @POST("User/getoneadress")
    fun getAddressInfo(@Body req: AddressReq): Observable<BaseResp<AddressBean>>

    @POST("User/modifiaddress")
    fun upAddress(@Body req: UpdateAddressReq): Observable<BaseData>

    @POST("User/order")
    fun getOrder(@Body req: OrderReq): Observable<BaseResp<OrderBean>>

    @POST("User/overorder")
    fun sureOrder(@Body req: NoParamOrderIdReq): Observable<BaseData>

    @POST("User/delorder")
    fun deleteOrder(@Body req: NoParamOrderIdReq): Observable<BaseData>

    @POST("User/orderrollback")
    fun closeOrder(@Body req: CloseOrderReq): Observable<BaseData>

    @POST("User/userexpress")
    fun logistics(@Body req: LogisticsReq): Observable<BaseResp<Logistics>>

    @POST("User/usertost")
    fun userAdv(@Body req: NoParamReq): Observable<BaseResp<MineAdv>>

    @POST("user/userbanner")
    fun mineBanner(@Body req: NoParamReq): Observable<BaseResp<Banner>>


    //个人中心健康推荐
    @POST("User/healthrommend")
    fun mineIndex(@Body req: NoParamIdReq): Observable<BaseResp<MineBean>>

    @POST("Web/version")
    fun getVersion(@Body req: NoParamReq): Observable<BaseResp<VersionBean>>

    @Multipart
    @POST("HomeApi/UploadImg")
    fun uploadImg(@PartMap maps: HashMap<String, RequestBody>): Observable<BaseData>

    // 分销系统


    @POST("Disbutor/applycardlist") // 代理商申请卡列表
    fun phoneNumberList(@Body req: PhoneNumberReq): Observable<BaseResp<PhoneNumberBean>>

    @POST("Disbutor/directInferiorsinfo") //
    fun phoneNumberDetail(@Body req: PhoneNumberReq): Observable<BaseResp<PhoneNumberBean>>

    @POST("Disbutor/addvipcard")
    fun addVipCard(@Body req: AddVipCardReq): Observable<BaseData>

    @POST("Disbutor/mydistribution")
    fun myDistribution(@Body req: PhoneNumberReq): Observable<BaseResp<DistributionBean>>

    @POST("Disbutor/okapply")
    fun agrApply(@Body req: AgrApplyReq): Observable<BaseData>


    @POST("Agent/myperson")
    fun myDisbutor(@Body req: NoParamDisIdReq): Observable<BaseResp<UpLevelBean>>

    @POST("Agent/myteam")
    fun myTeam(@Body req: NoParamDisIdReq): Observable<BaseResp<MyTeamBean>>

    @POST("Agent/myteamdirect")
    fun directData(@Body req: DirectReq): Observable<BaseResp<DirectBean>>

    @POST("Agent/myteamindirect")
    fun inDirectData(@Body req: DirectReq): Observable<BaseResp<DirectBean>>


    @POST("Disbutor/completionaddress")
    fun rebateAddress(@Body req: RebateAddressReq): Observable<BaseData>

    // new  rebate
    @POST("Agent/issettledin")
    fun isRebate(@Body req: NoParamIdReq): Observable<BaseResp<IsRebateBean>>

    @POST("Agent/settledin")
    fun authRebate(@Body req: NoParamIdReq): Observable<BaseData>

    @POST("Agent/agentinfo")
    fun disbutorIndex(@Body req: NoParamIdReq): Observable<BaseResp<RebateBean>>

    @POST("Agent/agentgrade")
    fun applyLevel(@Body req: NoParamIdReq): Observable<BaseResp<MutableList<RebateLevelBean>>>

    @POST("Agent/applyleval")
    fun applyLevelAction(@Body req: ApplyLevelReq): Observable<BaseData>

    @POST("Agent/applylevalrecord")
    fun applyLevelRecord(@Body req: NoParamIdDisIdPageReq): Observable<BaseResp<RebateLevelRecordBean>>

    @POST("User/userpackage")
    fun myCard(@Body req: NoParamIdTypeReq): Observable<BaseResp<MyCardBean>>

    @POST("Agent/addbankcard")
    fun addBankCard(@Body req: AddBankCardReq): Observable<BaseData>

    @POST("Agent/nobank")
    fun deleteBankCard(@Body req: DeleteBankCardReq): Observable<BaseData>

    @POST("Disbutor/disbutorcard")
    fun myBankCard(@Body req: NoParamIdDisIdReq): Observable<BaseResp<BankCardBean>>

    @POST("Agent/applystock")
    fun applyCard(@Body req: ApplyCardReq): Observable<BaseData>

    @POST("Agent/stockapply")
    fun applyCardRecord(@Body req: NoParamIdDisIdPageReq): Observable<BaseResp<CardRecord>>

    @POST("draw/cash_out_appliy")
    fun applyCash(@Body req: ApplyCashReq): Observable<BaseData>

    @POST("draw/detail")
    fun applyCashDetail(@Body req: NoParamIdReq): Observable<BaseResp<CashDetailBean>>

    @POST("draw/detail_list")
    fun applyCashRecord(@Body req: NoParamIdPageReq): Observable<BaseResp<ApplyCashRecordBean>>

    @POST("draw/draw_records")
    fun applyCashList(@Body req: ApplyCashListReq): Observable<BaseResp<ApplyCashListBean>>

    @POST("draw/draw_record")
    fun applyCashListDetail(@Body req: NoParamIdReq): Observable<BaseResp<ApplyCashListDetail>>

    @POST("draw/share")
    fun shareRecord(@Body req: NoParamIdPageReq): Observable<BaseResp<ShareBean>>

    @POST("Agent/directpush")
    fun rebateRecord(@Body req: NoParamDisIdPageReq): Observable<BaseResp<RebateRecordBean>>

    @POST("draw/draw_record")
    fun rebateRecord1(@Body req: NoParamIdReq): Observable<BaseResp<ApplyCashListDetail>>

    // 分销系统 结束

    //春雨医生

    @POST("doctor/records_price")
    fun getPrice(@Body req: NoParamReq): Observable<BaseResp<PriceBean>>

    @POST("doctor/choose_person")
    fun doctorList(@Body req: NoParamIdReq): Observable<BaseResp<MutableList<ArchivesBean>>>

    @POST("doctor/mk_person")
    fun createDoctor(@Body req: AddArchivesReq): Observable<BaseData>

    @POST("doctor/mk_person")
    fun deleteDoctor(@Body req: DelArchivesReq): Observable<BaseData>

    @POST("doctor/mk_person")
    fun editDoctor(@Body req: EditArchivesReq): Observable<BaseData>

    @Multipart
    @POST("doctor/mk_questions")
    fun createQuestion(@PartMap maps: HashMap<String, RequestBody>): Observable<BaseResp<OrderIdBean>>

    @POST("doctor/choose_person")
    fun chosePeople(@Body req: ChosePeopleReq): Observable<BaseResp<OrderIdBean>>

    @Multipart
    @POST("doctor/continue_question")
    fun addQuestion(@PartMap maps: HashMap<String, RequestBody>): Observable<BaseResp<IsRebateBean>>

    @POST("doctor/records")
    fun doctorRecord(@Body req: NoParamIdPageReq): Observable<BaseResp<InterrogationBean>>

    @POST("doctor/del_records")
    fun deleteRecord(@Body req: NoParamOrderIdReq): Observable<BaseData>

    @POST("doctor/load_read")
    fun chatRecord(@Body req: ChatRecordReq): Observable<BaseResp<ChatBean>>

    @POST("doctor/mk_alipay")
    fun payInterrogation(@Body req: PayInterrogationReq): Observable<BaseResp<OrderPayBean>>

    @POST("doctor/person_detail")
    fun archivesDetail(@Body req: ArchivesDetailReq): Observable<BaseResp<ArchivesDetail>>

    @POST("doctor/get_order_doctor")
    fun doctorDetail(@Body req: NoParamOrderReq): Observable<BaseResp<DoctorDetail>>

    //优惠券
    @POST("User/usercoupons")
    fun getCoupons(@Body req: CouponReq): Observable<BaseResp<MutableList<CouponBean>>>

    @POST("user/userrecivecoupon")
    fun takeCoupon(@Body req: NoParamIdIdReq): Observable<BaseData>

    @POST("Marketing/extendscoupon")
    fun getExchangeCoupons(@Body req: NoParamIdReq): Observable<BaseResp<MutableList<GoodsCouponBean>>>

    @POST("User/actityrecevecoupon")
    fun takeExchangeCoupons(@Body req: CouponIdReq): Observable<BaseData>

}