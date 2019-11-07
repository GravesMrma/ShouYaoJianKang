package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_appoint_travel.*
import org.jetbrains.anko.act

class AppointTravelActivity : AppCompatActivity() {
    private lateinit var list: MutableList<String>
    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appoint_travel)

        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        val str = "预约服务代表您已同意<font color=#FFA200>《首要健康远程咨询服务》</font>相关条款！"
        tvText.text = Html.fromHtml(str)

        list = mutableListOf()
        adapter= object :BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_appoint_check,list){
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
    }

    private fun initData() {

    }
}
