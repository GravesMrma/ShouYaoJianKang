package com.wuhanzihai.rbk.ruibeikang.utils

import java.util.*


class TimeUtils {

    companion object {
        private val ins by lazy { TimeUtils() }
        fun getInstance(): TimeUtils {
            return ins
        }
    }

    fun getHour(): String {
        var canld = Calendar.getInstance()
        if (canld.get(Calendar.HOUR_OF_DAY) >= 23 || canld.get(Calendar.HOUR_OF_DAY) < 1) {
            return "23:00-01:00"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 1..2) {
            return "01:00-03:00"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 3..4) {
            return "03:00-05:00"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 5..6) {
            return "05:00-07:00"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 7..8) {
            return "07:00-09:00"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 9..10) {
            return "09:00-11:00"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 11..12) {
            return "11:00-13:00"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 13..14) {
            return "13:00-15:00"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 15..16) {
            return "15:00-17:00"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 17..18) {
            return "17:00-19:00"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 19..20) {
            return "19:00-21:00"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 21..22) {
            return "21:00-23:00"
        }
        return ""
    }
}