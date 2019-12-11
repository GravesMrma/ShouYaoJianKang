package com.wuhanzihai.rbk.ruibeikang.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.net.Uri
import android.os.Environment
import android.view.View
import com.hhjt.baselibrary.common.BaseConstant
import java.util.*
import java.io.IOException
import java.io.File
import java.io.FileOutputStream


class MyUtils {

    companion object {
        val instance by lazy { MyUtils() }

        fun toHour(str: String): String {
            return str.substring(0, str.indexOf(":"))
        }

        fun toMui(str: String): String {
            return str.substring(str.indexOf(":") + 1)
        }

        fun getWidth(context: Activity): Int {
            val defaultDisplay = context.windowManager.defaultDisplay
            val point = Point()
            defaultDisplay.getSize(point)
            return point.x
        }

        fun getHeight(context: Activity): Int {
            val defaultDisplay = context.windowManager.defaultDisplay
            val point = Point()
            defaultDisplay.getSize(point)
            return point.y
        }

        fun getViewHeight(view: View): Int {
            view.measure(0, 0)
            return  view.measuredWidth
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
                r = toDouble(day)+"天" + toDouble(hour)+"时" + toDouble(minute)+"分"
            } else if (t >= 3600)
            //时,
            {
                hour = t / 3600
                minute = t % 3600 / 60
                second = t % 3600 % 60
                r = toDouble(hour)+"时" + toDouble(minute)+"分" + toDouble(second)+"秒"
            } else if (t >= 60)
            //分
            {
                minute = t / 60
                second = t % 60
                r = "00时" + toDouble(minute)+"分" + toDouble(second)+"秒"
            } else {
                second = t
                r = "00时00分" + toDouble(second)+"秒"
            }
            return r
        }

        fun toDouble(int: Int): String {
            if (int < 10) {
                return "0" + int.toString()
            }
            return int.toString()
        }
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

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    fun callPhone(context: Context,phoneNum: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse("tel:$phoneNum")
        intent.data = data
        context.startActivity(intent)
    }
}