package com.wuhanzihai.rbk.ruibeikang.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_apply_cash_process.*
import org.jetbrains.anko.act

class ApplyCashProcessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_cash_process)
        StatusBarUtil.setTranslucentForImageView(act, 0, null)
        StatusBarUtil.setLightMode(act)

        initView()

        initData()
    }

    private fun initView(){
        val str = "平台会在24小时内审核完毕，审核通过后账号会在48小时内到账您可以关注首要健康微信公众号-<font color=#FFA200>首要健康服务平台</font>获取到账通知,<font color=#FFA200>点击下方按钮复制微信公众号去微信搜索关注！</font>"
        tvDesc.text = Html.fromHtml(str)
        ivState2.setImageResource(R.mipmap.ic_pron)

        tvCopy.onClick {
            var cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var mClipData = ClipData.newPlainText("Label", "公众号复制")
            cm.primaryClip = mClipData
        }
    }

    private fun initData(){

    }
}
