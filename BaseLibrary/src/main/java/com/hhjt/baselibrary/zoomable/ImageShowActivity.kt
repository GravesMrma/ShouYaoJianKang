package com.hhjt.baselibrary.zoomable

import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.hhjt.baselibrary.R
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ui.activity.BaseActivity
import com.hhjt.baselibrary.widgets.ViewPagerAdapter
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_image_show.*
import org.jetbrains.anko.act
import org.jetbrains.anko.find
import java.io.File


class ImageShowActivity : BaseActivity() {

    private var count = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_show)
//        StatusBarUtil.setColor(act, ContextCompat.getColor(act, R.color.colorPrimary))
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))


        var imgs = intent.getStringArrayListExtra("urls")
        var views = mutableListOf<View>()

        count = intent.getIntExtra("count", 0)

        for (i in 0 until imgs.size) {
            var view = layoutInflater.inflate(R.layout.layout_image, null)
            var mIvShow = view.find<ZoomableDraweeView>(R.id.mIvShow)
            mIvShow.setAllowTouchInterceptionWhileZoomed(true)
            mIvShow.setIsLongpressEnabled(false)
            mIvShow.setTapListener(DoubleTapGestureListener(mIvShow))
            val controller =
                    if (imgs[i].contains("http")) {
                        Fresco.newDraweeControllerBuilder()
                                .setUri(imgs[i])
                                .setCallerContext("ZoomableApp-MyPagerAdapter")
                                .build()
                    } else {
                        Fresco.newDraweeControllerBuilder()
                                .setUri(BaseConstant.BASE_URL + imgs[i])
                                .setCallerContext("ZoomableApp-MyPagerAdapter")
                                .build()
//                    Fresco.newDraweeControllerBuilder()
//                        .setUri(Uri.fromFile(File(imgs[i])))
//                        .setCallerContext("ZoomableApp-MyPagerAdapter")
//                        .build()
                    }
            mIvShow.controller = controller
            views.add(view)
        }
        tvAll.text = toDouble(imgs.size)
        vpView.adapter = ViewPagerAdapter(views as ArrayList<View>)
        vpView.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(p0: Int) {
                tvCount.text = toDouble(p0 + 1)
            }
        })
        vpView.currentItem = count
    }

    private fun toDouble(int: Int): String {
        if (int < 10) {
            return "0" + int.toString()
        }
        return int.toString()
    }
}
