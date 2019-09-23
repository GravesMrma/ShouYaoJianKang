package com.wuhanzihai.rbk.ruibeikang.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.common.util.UriUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.utils.DensityUtils
import java.text.SimpleDateFormat
import java.util.*
import java.io.IOException
import com.hhjt.baselibrary.utils.LoginUtils
import java.io.File
import java.io.FileOutputStream


class MyUtils {

    companion object {
        val myUtils by lazy { MyUtils() }
    }

    fun htmlFormat(content: String): String {
        var content = content
        content = content.replace("&lt;", "<")
        content = content.replace("&gt;", ">")
        content = content.replace("&le;", "≤")
        content = content.replace("&ge;", "≥")
        content = content.replace("&quot;", "\"")
        content = content.replace("&ldquo;", "“")
        content = content.replace("&rdquo;", "”")
        content = content.replace("&lsquo;", "‘")
        content = content.replace("&rsquo;", "’")

        content = content.replace("<video", "<video ${BaseConstant.STYLE}")
            .replace("<img", "<img ${BaseConstant.STYLE}")
        return content
    }


    fun parseTimeSeconds(t: Int): String {
        var r = ""
        val day: Int
        val hour: Int
        val minute: Int
        val second: Int
        if (t >= 86400)
        //天,
        {
            day = t / 86400
            hour = t % 86400 / 3600
            minute = t % 86400 % 3600 / 60
            r = toDouble(day) + toDouble(hour) + toDouble(minute)
        } else if (t >= 3600)
        //时,
        {
            hour = t / 3600
            minute = t % 3600 / 60
            second = t % 3600 % 60
            r = toDouble(hour) + toDouble(minute) + toDouble(second)
        } else if (t >= 60)
        //分
        {
            minute = t / 60
            second = t % 60
            r = "00" + toDouble(minute) + toDouble(second)
        } else {
            second = t
            r = "0000" + toDouble(second)
        }
        return r
    }

    fun toDouble(int: Int): String {
        if (int < 10) {
            return "0" + int.toString()
        }
        return int.toString()
    }

    /**
     * view截图并保存到相册
     */
    fun viewShot(context: Context, view: View, viewFileListener: ViewFileListener) {
        if (null == view) {
            return
        }
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache(true)
        val drawingCache = view.drawingCache
        val bitmap: Bitmap?
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache!!)
            view.setDrawingCacheEnabled(false)
        } else {
            bitmap = null
        }
        var path = saveBitmap(context, bitmap!!)
        viewFileListener.onCallBack(path!!)
        view.isDrawingCacheEnabled = false
        view.buildDrawingCache(false)
//        v.viewTreeObserver.addOnGlobalLayoutListener(object :
//            ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    v.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                } else {
//                    v.viewTreeObserver.removeGlobalOnLayoutListener(this)
//                }
//                // 核心代码start
//                val bitmap = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
//                val c = Canvas(bitmap)
//                v.layout(0, 0, v.layoutParams.width, v.layoutParams.height)
//                v.draw(c)
//                // end
//

//            }
//        })
    }

    /**
     * view截图
     */
    fun viewShot(context: Context, view: View): Bitmap? {
        if (null == view) {
            return null
        }
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache(true)
        val drawingCache = view.drawingCache
        val bitmap: Bitmap?
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache!!)
            view.setDrawingCacheEnabled(false)
        } else {
            bitmap = null
        }
        view.isDrawingCacheEnabled = false
        view.buildDrawingCache(false)
        return bitmap
    }

    interface ViewFileListener {
        fun onCallBack(path: String)
    }

    private var viewFileListener: ViewFileListener? = null

    private val SD_PATH = "/sdcard/DCIM/Camera/"
    private val IN_PATH = "/happyboy/pic/"

    /**
     * 随机生产文件名
     *
     * @return
     */
    private fun generateFileName(): String {
        return UUID.randomUUID().toString()
    }

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    private fun saveBitmap(context: Context, mBitmap: Bitmap): String? {
        val savePath: String
        val filePic: File
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            savePath = SD_PATH
        } else {
            savePath = context.applicationContext.filesDir
                .absolutePath + IN_PATH
        }
        try {
            filePic = File(savePath + generateFileName() + ".png")
            if (!filePic.exists()) {
                filePic.parentFile.mkdirs()
                filePic.createNewFile()
            }
            val fos = FileOutputStream(filePic)
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return filePic.absolutePath
    }
}