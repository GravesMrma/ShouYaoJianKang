package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hhjt.baselibrary.ext.onClick
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_gene_archives.*
import org.jetbrains.anko.startActivity

class GeneArchivesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gene_archives)

        initView()

        initData()
    }

    private fun initView(){
        tvBtn.onClick {
            startActivity<HealthCareActivity>("fatherId" to 3, "title" to "健康医疗")
        }
    }

    private fun initData(){

    }
}
