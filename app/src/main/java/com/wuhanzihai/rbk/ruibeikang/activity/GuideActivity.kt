package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.kotlin.base.utils.AppPrefsUtils
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.widgets.ViewPagerIndicatorGuide
import kotlinx.android.synthetic.main.activity_guide.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class GuideActivity : AppCompatActivity() {
    private var viewsTop = mutableListOf<View>()
    private lateinit var viewPageAdapter: ViewPageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        StatusBarUtil.setTranslucentForImageView(act, 0, null)

        var imgs = mutableListOf(R.mipmap.pic_guide1,R.mipmap.pic_guide2,R.mipmap.pic_guide3)
        for (i in 0 until 3) {
            var view = ImageView(act)
            view.setImageResource(imgs[i])
            viewsTop.add(view)
        }
        viewPageAdapter = ViewPageAdapter(viewsTop)

        vpView.adapter = viewPageAdapter
        vpView.addOnPageChangeListener(ViewPagerIndicatorGuide(act, llIndicator, viewsTop.size,tvEnter))

        tvEnter.onClick {
            AppPrefsUtils.putBoolean(BaseConstant.IS_FIRST,true)
            startActivity<LoginActivity>()
            finish()
        }
    }

    class ViewPageAdapter(var list: MutableList<View>) : PagerAdapter() {
        override fun getCount(): Int {
            return list.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(list[position])
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(list[position])
            return list[position]
        }
    }
}
