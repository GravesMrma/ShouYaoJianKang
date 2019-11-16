package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable


data class BankCardBean(
    val bank_card_name: String,
    val bank_name: String,
    val card_id: Int,
    val card_number: String,
    val card_type: String,
    val degree: Int,
    val degreename: String,
    val user_id: Int
)