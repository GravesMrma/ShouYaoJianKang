package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

/**
 */
data class ArchivesBean(
        val person_id: Int,
        val name: String,
        val con: String,
        val sex: Int,
        val birthday: Long,
        var isCheck: Boolean = false
) : Serializable
