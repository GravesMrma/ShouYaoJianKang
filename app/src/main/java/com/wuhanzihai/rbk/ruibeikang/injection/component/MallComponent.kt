package com.wuhanzihai.rbk.ruibeikang.injection.component

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.fragment.HealthCareFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.MallFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.SearchGoodsFragment
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import dagger.Component

@PerComponentScope
@Component(dependencies = [(ActivityComponent::class)], modules = [(MallModule::class)])
interface MallComponent {
    fun inject(fragment: MallFragment)

    fun inject(fragment: HealthCareFragment)

    fun inject(activity: MainActivity)

    fun inject(activity: GoodsDetailActivity)

    fun inject(activity: GoodsClassActivity)

    fun inject(activity: ShoppingCartActivity)

    fun inject(activity: SureOrderActivity)

    fun inject(activity: PayActivity)

    fun inject(activity: HealthCareActivity)

    fun inject(activity: GoodsServiceDetailActivity)

    fun inject(activity: SureOrderBuyActivity)

    fun inject(activity: SearchActivity)

    fun inject(activity: SearchDetailActivity)

    fun inject(activity: SingleTravelActivity)

    fun inject(fragment: SearchGoodsFragment)

    fun inject(activity: SureServiceOrderActivity)

}