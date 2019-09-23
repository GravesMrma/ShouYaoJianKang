package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.HealthCallActivity
import kotlinx.android.synthetic.main.fragment_health_call.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

class HealthCallFragment:Fragment() {

    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>
    private lateinit var list: MutableList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_health_call,null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        initView()

        initData()
    }

    private fun initView(){

        list = mutableListOf()

        list.add("")
        list.add("")
        list.add("")
        list.add("")
        adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_jugde_item,list){
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }

        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        adapter.setOnItemClickListener { _, _, position ->

            startActivity<HealthCallActivity>()
        }
//        rvView.setOffscreenItems(40)
//        vpView.setItemTransformer(ScaleTransformer.Builder()
//                .setMaxScale(1.05f)
//                .setMinScale(0.8f)
//                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
//                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
//                .build());
    }

    private fun initData(){

    }

}