package com.wuhanzihai.rbk.ruibeikang.injection.module

import com.wuhanzihai.rbk.ruibeikang.service.InfoService
import com.wuhanzihai.rbk.ruibeikang.service.impl.InfoServiceImpl
import dagger.Module
import dagger.Provides

@Module
class InfoModule {

    @Provides
    fun providesInfoService(infoServiceImpl: InfoServiceImpl): InfoService {
        return infoServiceImpl
    }
}