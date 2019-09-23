package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem12_14_12
import kotlinx.android.synthetic.main.activity_accompanying.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

// 陪诊服务页面
class AccompanyingActivity : AppCompatActivity() {
    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>
    private lateinit var list: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accompanying)

        initView()

        initData()
    }


    private fun initView(){
        list = mutableListOf()
        for (i in 0..3) {
            list.add("")
        }

        adapter = object :BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_accompanying,list){
            override fun convert(helper: BaseViewHolder?, item: String?) {
                when(helper!!.layoutPosition){
                    0->{
                        helper.setImageResource(R.id.ivImg,R.mipmap.ic_acc1)
                                .setText(R.id.tvText,"规划流程 节省时间")
                                .setText(R.id.tvText1,"成人陪诊")
                    }
                    1->{
                        helper.setImageResource(R.id.ivImg,R.mipmap.ic_acc2)
                                .setText(R.id.tvText,"省心省力 安心待产")
                                .setText(R.id.tvText1,"孕妇陪诊")
                    }
                    2->{
                        helper.setImageResource(R.id.ivImg,R.mipmap.ic_acc3)
                                .setText(R.id.tvText,"贴心陪伴 家人放心")
                                .setText(R.id.tvText1,"老人陪诊")
                    }
                    3->{
                        helper.setImageResource(R.id.ivImg,R.mipmap.ic_acc4)
                                .setText(R.id.tvText,"专业沟通 合理安排")
                                .setText(R.id.tvText1,"儿童陪诊")
                    }
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        rvView.addItemDecoration(DividerItem12_14_12(act))

        adapter.setOnItemClickListener { adapter, view, position ->
            when(position){
                0-> startActivity<AdultAccompanimentActivity>("title" to "成人陪诊")
                1-> startActivity<AdultAccompanimentActivity>("title" to "孕妇陪诊")
                2-> startActivity<AdultAccompanimentActivity>("title" to "老人陪诊")
                3-> startActivity<AdultAccompanimentActivity>("title" to "儿童陪诊")
            }
        }

    }

    private fun initData(){

    }
}
