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

    fun getHourImgUrl(): String {
        var canld = Calendar.getInstance()
        if (canld.get(Calendar.HOUR_OF_DAY) >= 23 || canld.get(Calendar.HOUR_OF_DAY) < 1) {

            return "http://www.hcjiankang.com/androidimg/pic_clock1.png"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 1..2) {
            return "http://www.hcjiankang.com/androidimg/pic_clock2.png"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 3..4) {
            return "http://www.hcjiankang.com/androidimg/pic_clock3.png"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 5..6) {
            return "http://www.hcjiankang.com/androidimg/pic_clock4.png"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 7..8) {
            return "http://www.hcjiankang.com/androidimg/pic_clock5.png"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 9..10) {
            return "http://www.hcjiankang.com/androidimg/pic_clock6.png"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 11..12) {
            return "http://www.hcjiankang.com/androidimg/pic_clock7.png"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 13..14) {
            return "http://www.hcjiankang.com/androidimg/pic_clock8.png"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 15..16) {
            return "http://www.hcjiankang.com/androidimg/pic_clock9.png"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 17..18) {
            return "http://www.hcjiankang.com/androidimg/pic_clock10.png"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 19..20) {
            return "http://www.hcjiankang.com/androidimg/pic_clock11.png"
        }
        if (canld.get(Calendar.HOUR_OF_DAY) in 21..22) {
            return "http://www.hcjiankang.com/androidimg/pic_clock12.png"
        }
        return ""
    }
}