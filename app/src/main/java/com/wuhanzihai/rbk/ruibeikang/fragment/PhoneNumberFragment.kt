package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.onClick
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.fragment_phone_number.*
import org.jetbrains.anko.act
import org.jetbrains.anko.support.v4.act
import per.goweii.anylayer.AnyLayer

@SuppressLint("ValidFragment")
class PhoneNumberFragment(var type: Int) : Fragment() {

    private val dialog by lazy {
        val anyLayer =
                AnyLayer.with(act)
                        .contentView(R.layout.layout_apply_phone)
                        .gravity(Gravity.BOTTOM)
                        .backgroundResource(R.color.clarity_40)
                        .cancelableOnTouchOutside(true)
                        .onClick(R.id.tvCommit) { anyLayer, v ->
                            anyLayer.dismiss()
                        }
        anyLayer
    }

    private lateinit var adapter: BaseQuickAdapter<String, BaseViewHolder>
    private lateinit var list: MutableList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_phone_number, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_jugde_item, list) {
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }

        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        adapter.setOnItemClickListener { _, _, position ->

        }

        tvApply.onClick {
            dialog.show()
        }
    }

    private fun initData() {
        if (type == 1) {
            llText.visibility = View.VISIBLE
            llBt.visibility = View.VISIBLE
        }
        if (type == 2) {
            llText.visibility = View.GONE
            llBt.visibility = View.GONE
        }
    }
}