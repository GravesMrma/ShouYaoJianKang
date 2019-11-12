package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_apply_cash_process.*

class ApplyCashProcessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_cash_process)



        initView()

        initData()
    }

    private fun initView(){
        val str = "平台会在24小时内审核完毕，审核通过后账号会在48小时内到账您可以关注首要健康微信公众号-<font color=#FFA200>首要健康服务平台</font>获取到账通知,<font color=#FFA200>点击下方按钮复制微信公众号去微信搜索关注！</font>"
        tvDesc.text = Html.fromHtml(str)
        ivState2.setImageResource(R.mipmap.ic_pron)
    }

    private fun initData(){

    }
}
