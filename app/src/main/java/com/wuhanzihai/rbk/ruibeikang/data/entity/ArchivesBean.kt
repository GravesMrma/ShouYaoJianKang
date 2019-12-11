package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

/**
 */
data class ArchivesBean(
        val birthday: String,
        val connections: String,
        val create_time: Int,
        val drink: Int,
        val heights: Int,
        val id: Int,
        val is_show: Int,
        val name: String,
        val person_id: Int,
        val pic: String,
        val sex: Int,
        val smoke: Int,
        val user_id: Int,
        val weights: Int,
        var isCheck: Boolean = false
) : Serializable

data class ArchivesDetail(
//        val add_field: String,
        val birthday: String,
        val connections: String,
        val create_time: Int,
        val drink: String,
        val heights: String,
        val id: Int,
        val is_show: Int,
        val name: String,
        val pic: String,
        val sex: Int,
        val smoke: String,
        val user_id: Int,
        val weights:String,
        val msg1: String,
        val msg2: String,
        val msg3: String,
        val msg4: String,
        val msg5: String,
        val msg6: String,
        val msg7: String,
        val msg8: String,
        val msg9: String
)
