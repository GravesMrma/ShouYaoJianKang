package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import kotlinx.android.synthetic.main.activity_sign.*
import org.jetbrains.anko.act

class SignActivity : AppCompatActivity() {
    private lateinit var list: MutableList<String>
    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>

    private lateinit var listRecord: MutableList<String>
    private lateinit var adapterRecord: BaseQuickAdapter<String,BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)


        initView()

        initData()
    }

    private fun initView(){
        list = mutableListOf()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        adapter = object  : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_sign,list){
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,7)

        listRecord = mutableListOf()
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")
        listRecord.add("")

        adapterRecord = object  : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_sign_record,listRecord){
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }
        rvView1.adapter = adapterRecord
        rvView1.layoutManager = GridLayoutManager(act,1)
        rvView1.addItemDecoration(DividerItem14_14_14(act))
    }

    private fun initData(){}

}
