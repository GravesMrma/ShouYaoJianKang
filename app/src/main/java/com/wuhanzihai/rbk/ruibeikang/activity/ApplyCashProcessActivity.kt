package com.wuhanzihai.rbk.ruibeikang.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Html
import android.view.View
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.ApplyCashListDetail
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.ApplyCashPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ApplyCashView
import kotlinx.android.synthetic.main.activity_apply_cash_process.*
import org.jetbrains.anko.act

class ApplyCashProcessActivity : BaseMvpActivity<ApplyCashPresenter>(), ApplyCashView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onApplyCashListDetail(result: ApplyCashListDetail) {
        if (result.money != null) {
            if (result.money.isNotEmpty()) {
                rlEmpty.visibility = View.GONE
                tvMoney.text = result.money
                tvState1.text = result.list[0].short_desc
                tvTime1.text = result.list[0].create_time

                tvState2.text = result.list[1].short_desc
                tvTime2.text = result.list[1].create_time

                when (result.list[0].now_status) {
                    "0" -> {
                    }
                    "1" -> {
                    }
                    "2" -> {
                    }
                    "3" -> {
                    }
                    "4" -> {

                    }
                    "5" -> {

                    }
                }
                when (result.list[1].now_status) {
                    "0" -> {
                    }
                    "1" -> {
                    }
                    "2" -> {
                    }
                    "3" -> {
                    }
                    "4" -> {
                        tvState2.setTextColor(ContextCompat.getColor(act,R.color.green_08))
                    }
                    "5" -> {

                    }
                }
            } else {
                rlEmpty.visibility = View.VISIBLE
            }
        } else {
            rlEmpty.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_cash_process)
        StatusBarUtil.setTranslucentForImageView(act, 0, null)
        StatusBarUtil.setLightMode(act)

        initView()

        initData()
    }

    private fun initView() {
        val str = "平台会在24小时内审核完毕，审核通过后预计48小时内到账，您可以关注首要健康微信服务号---><font color=#FFA200>首要平台</font>,获取转账通知,<font color=#FFA200>点击下方按钮复制微信服务号去微信搜索关注！</font>"
        tvDesc.text = Html.fromHtml(str)
        ivState2.setImageResource(R.mipmap.ic_pron)

        tvCopy.onClick {
            var cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var mClipData = ClipData.newPlainText("Label", "首要平台")
            cm.primaryClip = mClipData
            showTextDesc(act,"复制成功")
        }
    }

    private fun initData() {
        mPresenter.applyCashListDetail()
    }
}
