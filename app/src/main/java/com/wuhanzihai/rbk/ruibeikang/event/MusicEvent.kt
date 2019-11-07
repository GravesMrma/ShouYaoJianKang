package com.wuhanzihai.rbk.ruibeikang.event

import com.android.mltcode.blecorelib.manager.Callback
import com.wuhanzihai.rbk.ruibeikang.data.entity.MusicItem
import java.io.Serializable

class MusicEvent(var data: MutableList<MusicItem>,var position:Int = 0):Serializable