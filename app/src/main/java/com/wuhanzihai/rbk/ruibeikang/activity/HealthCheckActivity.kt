package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem12_10_12
import kotlinx.android.synthetic.main.activity_health_check.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

// 健康自检页面
class HealthCheckActivity : AppCompatActivity() {
    private lateinit var list: MutableList<String>
    private lateinit var adapter: BaseQuickAdapter<String, BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_check)

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        list.add("")
        list.add("")
        list.add("")
        list.add("")

        adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_image,list){
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        rvView.addItemDecoration(DividerItem12_10_12(act))
        adapter.setOnItemClickListener { _, _, position ->
            when(position){
                0->{
                    startActivity<QuestionActivity>()
                }
                1->{
                    startActivity<BodyFatActivity>()
                }
                2->{
                    startActivity<BIMCheckActivity>(
                            "type" to 1
                    )
                }
                3->{
                    startActivity<BIMCheckActivity>(
                            "type" to 2
                    )
                }
            }
        }
    }

    private fun initData() {

    }

}
