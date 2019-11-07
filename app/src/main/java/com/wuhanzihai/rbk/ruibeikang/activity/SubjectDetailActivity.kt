package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemOrderItem
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemSubject
import kotlinx.android.synthetic.main.activity_subject_detail.*
import org.jetbrains.anko.act

class SubjectDetailActivity : AppCompatActivity() {
    private lateinit var list: MutableList<String>
    private lateinit var adapter: BaseQuickAdapter<String, BaseViewHolder>

    private lateinit var dividerItemFourteen: DividerItemOrderItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_detail)


        initView()

        initData()
    }

    private fun initView() {

        list = mutableListOf()
        list.add("")
        list.add("")
        list.add("")
        dividerItemFourteen = DividerItemOrderItem(act)
        adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_subject_detail, list) {
            override fun convert(helper: BaseViewHolder?, item: String?) {
                helper!!.getView<RecyclerView>(R.id.rvView).run {
                    adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_subject_detail_item, list) {
                        override fun convert(helper: BaseViewHolder?, item: String?) {
                            helper!!.getView<TextView>(R.id.tvText).isSelected = true
                        }
                    }
                    layoutManager = GridLayoutManager(act, 1)
                    addItemDecoration(dividerItemFourteen)
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemSubject(act))
    }

    private fun initData() {

    }
}
