package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.utils.LoginUtils
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.utils.NotificationsUtils
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_set.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        btLogout.onClick {
            LoginUtils.saveLoginStatus(false, "")
            startActivity<LoginActivity>()
        }
        tvUserInfo.onClick {
            startActivity<EditUserInfoActivity>()
        }
        tvAuth.onClick {
            startActivity<AuthListActivity>()
        }
        tvAddress.onClick {
            startActivity<AddressActivity>()
        }
        rlUpdate.onClick {
            toast("检查更新")
        }
        rlAbout.onClick {
            startActivity<AboutActivity>()
        }
        tvDesc.onClick {
            NotificationsUtils.toSetting(act)
        }
    }

    private fun initData() {
        tvState.isSelected = !NotificationManagerCompat.from(act).areNotificationsEnabled()
        if (NotificationManagerCompat.from(act).areNotificationsEnabled()) {
            tvDesc.visibility = View.GONE
            tvState.text = "已开启"
        } else {
            tvDesc.visibility = View.VISIBLE
            tvState.text = "去开启"
        }
        tvCache.text = "0.00M"
        tvVersion.text = "V${packageManager.getPackageInfo(packageName, 0).versionName}"
    }
}
