package com.wuhanzihai.rbk.ruibeikang.event

import com.android.mltcode.blecorelib.manager.Callback
import com.wuhanzihai.rbk.ruibeikang.data.entity.MusicItem
import com.wuhanzihai.rbk.ruibeikang.media.AudioPlayer
import com.wuhanzihai.rbk.ruibeikang.media.ManagedMediaPlayer
import java.io.Serializable

class MusicStateEvent(var state: ManagedMediaPlayer.Status):Serializable