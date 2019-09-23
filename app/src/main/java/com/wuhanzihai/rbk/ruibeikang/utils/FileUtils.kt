package com.wuhanzihai.rbk.ruibeikang.utils

import com.hhjt.baselibrary.common.BaseApplication
import com.hhjt.baselibrary.common.BaseConstant
import java.io.File

class FileUtils {

    companion object {
        private val fileUtils by lazy { FileUtils() }
        fun getInstance(): FileUtils {
            return fileUtils
        }
    }

    fun getVoiceCachePath(): File {
        val path = BaseApplication.context.getExternalFilesDir(BaseConstant.VOICE_CACHE).absolutePath
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
    }
}