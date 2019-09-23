package com.wuhanzihai.rbk.ruibeikang.injection.component

import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.fragment.CouponFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.MallFragment
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import dagger.Component

@PerComponentScope
@Component(dependencies = [(ActivityComponent::class)], modules = [(MallModule::class)])
interface MallComponent {
    fun inject(fragment: MallFragment)

    fun inject(activity: MainActivity)

    fun inject(activity: GoodsDetailActivity)

    fun inject(activity: ShoppingCartActivity)

    fun inject(activity: SureOrderActivity)

}