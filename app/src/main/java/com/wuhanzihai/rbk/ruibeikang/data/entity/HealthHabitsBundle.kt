package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

/**
 * Created by Zuriel Cotter on 2019/2/26.
 */

class HealthHabitsBundle(var data: List<HealthHabitsListItem>):Serializable{
    var list: List<HealthHabitsListItem> = data
}

