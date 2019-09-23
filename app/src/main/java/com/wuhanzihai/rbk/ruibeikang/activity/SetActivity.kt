package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.wuhanzihai.rbk.ruibeikang.R
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_set.*
import org.jetbrains.anko.act

class SetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)

        var list = mutableListOf<String>()
        list.add("diuasda")
        list.add("diuasda")
        list.add("diuasda")
        list.add("diuasda")
        list.add("diuasda")
        list.add("diuasda")
        list.add("diuasda")
        list.add("diuasda")
        val  adapter =object : TagAdapter<String>(list) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_spces_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }

        flView.adapter = adapter
    }
}
