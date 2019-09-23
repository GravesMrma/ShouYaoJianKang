package com.wuhanzihai.rbk.ruibeikang.injection.component

import android.support.v4.app.Fragment
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.fragment.HealthHabitsFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.HealthInfoFragment
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import dagger.Component

@PerComponentScope
@Component(dependencies = [(ActivityComponent::class)], modules = [(InfoModule::class)])
interface InfoComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: HealthInfoActivity)

    fun inject(fragment: HealthInfoFragment)

    fun inject(activity: HealthHabitsActivity)

    fun inject(activity: HealthHabitsDetailActivity)

    fun inject(fragment: HealthHabitsFragment)


    fun inject(activity: UnifiedWebActivity)

}