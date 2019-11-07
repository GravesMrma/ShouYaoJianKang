package com.wuhanzihai.rbk.ruibeikang.injection.component

import android.support.v4.app.Fragment
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.fragment.*
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

    fun inject(fragment: HealthHabitsTimeFragment)

    fun inject(fragment: HealthHabitsDetailFragment)

    fun inject(fragment: HealthHabitsDetailSportFragment)

    fun inject(fragment: HealthyFragment)

    fun inject(activity: UnifiedWebActivity)

    fun inject(activity: MusicTherapyActivity)

    fun inject(fragment: MusicFragment)

    fun inject(fragment: WebFragment)

    fun inject(fragment: HealthClassMusicFragment)

    fun inject(activity: HealthClassActivity)

    fun inject(activity: HealthCallActivity)

    fun inject(activity: SingleTravelActivity)

    fun inject(activity: TravelWebActivity)

    fun inject(fragment: SearchClassFragment)

    fun inject(fragment: SearchGoodsFragment)

    fun inject(fragment: SearchInfoFragment)

    fun inject(fragment: CollectFragment)

    fun inject(activity: HealthFoodActivity)
}