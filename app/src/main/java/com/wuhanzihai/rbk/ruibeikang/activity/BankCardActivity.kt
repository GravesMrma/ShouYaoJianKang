package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hhjt.baselibrary.ext.onClick
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_bank_card.*
import org.jetbrains.anko.startActivity

class BankCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_card)

        initView()

        initData()
    }

    private fun initView() {
        tvAdd.onClick {
            startActivity<AddBankCardActivity>()
        }
    }

    private fun initData() {

    }
}
