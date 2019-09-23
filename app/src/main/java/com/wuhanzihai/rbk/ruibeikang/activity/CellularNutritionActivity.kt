package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.CellsBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsBean
import kotlinx.android.synthetic.main.activity_cellular_nutrition.*
import org.jetbrains.anko.act

//  细胞营养素
class CellularNutritionActivity : AppCompatActivity() {
    private lateinit var list: MutableList<CellsBean>
    private lateinit var adapter: BaseQuickAdapter<CellsBean,BaseViewHolder>

    private lateinit var listGoods: MutableList<GoodsBean>
    private lateinit var adapterGoods: BaseQuickAdapter<GoodsBean,BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cellular_nutrition)


        initView()

        initData()
    }

    private fun initView(){
        list = mutableListOf()
        list.add(CellsBean(""))
        list.add(CellsBean(""))
        list.add(CellsBean(""))
        list.add(CellsBean(""))
        list.add(CellsBean(""))
        list.add(CellsBean(""))
        list.add(CellsBean(""))

        adapter = object : BaseQuickAdapter<CellsBean,BaseViewHolder>(R.layout.item_cells_top,list){
            override fun convert(helper: BaseViewHolder?, item: CellsBean?) {

            }
        }
        rvTop.adapter = adapter
        rvTop.layoutManager = GridLayoutManager(act,1)



    }

    private fun initData(){

    }

}
