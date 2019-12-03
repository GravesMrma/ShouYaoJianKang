package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomDatePicker
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomSinglePicker
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_archives_detail.*
import org.jetbrains.anko.act

class ArchivesDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archives_detail)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        rlGuanxi.onClick {
            CustomSinglePicker(act) {
                tvGuanxi.text = it
            }.setData(mutableListOf("关系1", "关系2", "关系3")).setIsLoop(false).show()
        }

        rlBirthday.onClick {
            CustomDatePicker(act){
                tvBirthday.text = it
            }.setIsLoop(false).showSpecificTime(false).show()
        }

        var height = mutableListOf<String>()
        for (i in 100..250) {
            height.add("${i}cm")
        }
        rlHeight.onClick {
            CustomSinglePicker(act){
                tvHeight.text = it
            }.setData(height).setIsLoop(false).show()
        }

        var weight = mutableListOf<String>()
        for (i in 30..200) {
            weight.add("${i}Kg")
        }
        rlWeight.onClick {
            CustomSinglePicker(act){
                tvWeight.text = it
            }.setData(weight).setIsLoop(false).show()
        }

        rlXiyan.onClick {
            CustomSinglePicker(act) {
                tvXiyan.text = it
            }.setData(mutableListOf("吸烟史1", "吸烟史2", "吸烟史3")).setIsLoop(false).show()
        }

        rvDrink.onClick {
            CustomSinglePicker(act) {
                tvDrink.text = it
            }.setData(mutableListOf("饮酒1", "饮酒2", "饮酒3")).setIsLoop(false).show()
        }

        tfView1.adapter = object : TagAdapter<String>(mutableListOf("aaa","asdasd","dasda","dasdsad")) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        tfView2.adapter = object : TagAdapter<String>(mutableListOf("aaa","asdasd","dasda","dasdsad")) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }

        tfView3.adapter = object : TagAdapter<String>(mutableListOf("aaa","asdasd","dasda","dasdsad")) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }

        tfView4.adapter = object : TagAdapter<String>(mutableListOf("aaa","asdasd","dasda","dasdsad")) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }

        tfView5.adapter = object : TagAdapter<String>(mutableListOf("aaa","asdasd","dasda","dasdsad")) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }

        tfView6.adapter = object : TagAdapter<String>(mutableListOf("aaa","asdasd","dasda","dasdsad")) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }

        tfView7.adapter = object : TagAdapter<String>(mutableListOf("aaa","asdasd","dasda","dasdsad")) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }

        tfView8.adapter = object : TagAdapter<String>(mutableListOf("aaa","asdasd","dasda","dasdsad")) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }

        tfView9.adapter = object : TagAdapter<String>(mutableListOf("aaa","asdasd","dasda","dasdsad")) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
    }

    private fun initData() {

    }

}
