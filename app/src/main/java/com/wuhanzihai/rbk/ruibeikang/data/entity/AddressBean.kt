package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

/**
 * Created by Zuriel Cotter on 2019/2/26.
 */
data class AddressBean(
        val address: String,
        val address_id: Int,
        val city: String,
        val consignee: String,
        val district: String,
        val is_default: Int,
        val mobile: String,
        val province: String,
        val user_id: Int
):Serializable
