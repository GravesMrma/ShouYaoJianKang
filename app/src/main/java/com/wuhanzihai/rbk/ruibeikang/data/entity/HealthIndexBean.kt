package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

/**
 * Created by Zuriel Cotter on 2019/2/26.
 */

data class HealthIndexBean(
    val banner: Banner,
    val doctor: Doctor,
    val music: Music,
    val product: HealthProduct
)

data class Banner(
    val item: List<BannerEntity>,
    val totle: Int
)

data class Doctor(
    val item: List<BannerEntity>,
    val totle: Int
)

data class Music(
    val item: List<MusicItem>,
    val totle: Int
)

data class HealthProduct(
    val item: List<BannerEntity>,
    val totle: Int
)
