package com.wuhanzihai.rbk.ruibeikang.data.entity

import com.android.mltcode.blecorelib.bean.*

class BraceletData {
    companion object {
        val instance by lazy { BraceletData() }
    }

    var batteryBean:BatteryBean? = null
    var sleepBean: SleepBean? = null
    var sportsBean: SportsBean? = null
    var heartRateBean: HeartrateBean? = null
    var bloodOxygenList: List<BloodOxygen>? = null
    var bloodPressureList: List<BloodPressure>? = null
    var allDayHeartRateBean: AllDayHeartRateBean? = null
    var msgSwitchList: List<MsgSwith>? = null
}