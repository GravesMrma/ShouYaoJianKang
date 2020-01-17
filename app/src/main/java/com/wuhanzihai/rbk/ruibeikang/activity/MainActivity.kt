package com.wuhanzihai.rbk.ruibeikang.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import cn.jpush.android.api.JPushInterface
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.hhjt.baselibrary.utils.LoginUtils
import com.jaeger.library.StatusBarUtil
import com.kotlin.base.utils.AppPrefsUtils
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.common.toDoubleInt
import com.wuhanzihai.rbk.ruibeikang.data.entity.IsRebateBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.VersionBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ActivationReq
import com.wuhanzihai.rbk.ruibeikang.event.MainEvent
import com.wuhanzihai.rbk.ruibeikang.event.MainFragmentEvent
import com.wuhanzihai.rbk.ruibeikang.event.MusicStateEvent
import com.wuhanzihai.rbk.ruibeikang.fragment.HealthyFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.MainFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.MallFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.MineFragment
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerIndexComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.IndexModule
import com.wuhanzihai.rbk.ruibeikang.media.AudioPlayer
import com.wuhanzihai.rbk.ruibeikang.media.ManagedMediaPlayer
import com.wuhanzihai.rbk.ruibeikang.media.MusicService
import com.wuhanzihai.rbk.ruibeikang.presenter.MainPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startService
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.toast
import per.goweii.anylayer.AnyLayer
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseMvpActivity<MainPresenter>(), View.OnClickListener, MainView {

    override fun injectComponent() {
        DaggerIndexComponent.builder().activityComponent(mActivityComponent)
                .indexModule(IndexModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onUserInfoResult(result: LoginData) {
        GlobalBaseInfo.setBaseInfo(result)
        AppPrefsUtils.putString(BaseConstant.BRACELET_ID, result.mobile)
        if (result.is_activation != 1) {
            val title = "您需要输入会员<font color=#FFA200>卡号</font>、激活<font color=#FFA200>密码</font>后<br>才能使用APP功能！"
            actDialog.getView<TextView>(R.id.tvContent).text = Html.fromHtml(title)
            actDialog.show()
        } else {
            actDialog.dismiss()
            mPresenter.isRebate()
//            if (!AppPrefsUtils.getBoolean(BaseConstant.MALL_ADV)) {
//                AppPrefsUtils.putBoolean(BaseConstant.MALL_ADV, true)
//            }
        }
    }

    override fun onActivationResult(result: BaseData) {
        if (result.code == 1) {
            toast("激活成功")
            actDialog.dismiss()
            dialog.show()
            mPresenter.getVersion()
            mPresenter.getUserInfo()
        } else {
            toast(result.msg)
        }
    }

    override fun onVersionResult(result: VersionBean) {
        if (result.version != getLocalVersion()) {
            force = result.force
            download_url = result.url
            val title = "<font color=#EC4043>89%</font>的用户已更新到最新版本<font color=#EC4043>V${result.version}</font>"
            updateDialog.getView<TextView>(R.id.tvTitle).text = Html.fromHtml(title)
            updateDialog.getView<TextView>(R.id.tvCode).text = "V${result.version}"
            val split = result.info.split(";")
            var desc = ""
            for (i in 0..split.size - 1) {
                desc = desc + "${i + 1}、${split[i]}\n"
            }
            updateDialog.getView<TextView>(R.id.tvDesc1).text = desc.substring(0, desc.length - 4)
            updateDialog.show()
        }
    }

    override fun onIsRebateResult(result: IsRebateBean) {
        Hawk.put(BaseConstant.ISREBATE_DATA, result)
    }

    private fun getLocalVersion(): String {
        var localVersionName = "1.0.0"
        try {
            val packageInfo = applicationContext.packageManager.getPackageInfo(packageName, 0)
            localVersionName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return localVersionName
    }

    val actDialog by lazy {
        val anyLayer = AnyLayer.with(this)
                .contentView(R.layout.layout_activation)
                .backgroundColorRes(R.color.clarity_50)
                .gravity(Gravity.CENTER)
                .cancelableOnTouchOutside(false)
                .cancelableOnClickKeyBack(false)
                .onClick(R.id.rlExit) { AnyLayer, v ->
                    LoginUtils.saveLoginStatus(false, "")
                    startActivity<LoginActivity>()
                    finish()
                }
                .onClick(R.id.btSure) { AnyLayer, v ->
                    if (AnyLayer.getView<EditText>(R.id.edAccount).text.isEmpty() || AnyLayer.getView<EditText>(R.id.edAccount).text.isEmpty()) {
                        toast("请填写正确激活信息")
                    } else {
                        mPresenter.activation(ActivationReq(AnyLayer.getView<EditText>(R.id.edAccount).text.toString()
                                , AnyLayer.getView<EditText>(R.id.edPwd).text.toString()))
                    }
                }
        anyLayer
    }

    private val dialog by lazy {
        val anyLayer =
                AnyLayer.with(act)
                        .contentView(R.layout.layout_main_adv)
                        .gravity(Gravity.CENTER)
                        .backgroundResource(R.color.clarity_40)
                        .cancelableOnTouchOutside(false)
                        .onClick(R.id.ivImg) { anyLayer, _ ->
                            startActivity<CouponActivity>()
                            anyLayer.dismiss()
                        }
                        .onClick(R.id.ivClose) { anyLayer, v ->
                            anyLayer.dismiss()
                        }
        anyLayer
    }

    private var force = 1
    private var download_url = ""
    val updateDialog by lazy {
        val anyLayer = AnyLayer.with(this)
                .contentView(R.layout.layout_update)
                .backgroundColorRes(R.color.clarity_50)
                .gravity(Gravity.CENTER)
                .cancelableOnTouchOutside(force != 1)
                .cancelableOnClickKeyBack(force != 1)
                .onClick(R.id.tvUpdate) { AnyLayer, v ->
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(download_url)))

                }
        anyLayer
    }

    private var fragment = emptyArray<Fragment>()
    private var last: Int = 0
    private var next: Int = 0

    private val homeFragment by lazy { MainFragment() }
    private val healthyFragment by lazy { HealthyFragment() }
    private val mallFragment by lazy { MallFragment() }
    private val mineFragment by lazy { MineFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.setTranslucentForImageView(act, 0, null)

        JPushInterface.setAlias(
                act,
                0,
                "eodeceshi"
        )
        initView()

        initData()
    }

    private fun initView() {
        rlHome.setOnClickListener(this)
        rlHeath.setOnClickListener(this)
        rlOil.setOnClickListener(this)
        rlCenter.setOnClickListener(this)

        ivPlay.onClick {
            ivPlay.isSelected = !ivPlay.isSelected
            if (ivPlay.isSelected) {
                AudioPlayer.getInstance().resume()
            } else {
                AudioPlayer.getInstance().pause()
            }
        }
        ivClose.onClick {
            startService<MusicService>("time" to -1)
            clWindow.visibility = View.INVISIBLE
        }
        clWindow.onClick {

        }
    }

    private fun initData() {
        fragment = arrayOf(homeFragment, healthyFragment, mallFragment, mineFragment)
        var ft: FragmentTransaction = supportFragmentManager.beginTransaction().add(R.id.fView, fragment[0])
        ft.commit()
        upButton(0)

        Bus.observe<MainEvent>().subscribe {
            handler.postDelayed({
                onResume()
            }, 1500)
        }.registerInBus(this)

        Bus.observe<MainFragmentEvent>().subscribe {
            if (it.index == -1) {
                finish()
            } else {
                jumpFragment(it.index)
            }
        }.registerInBus(this)

        Bus.observe<MusicStateEvent>().subscribe {
            if (it.state == ManagedMediaPlayer.Status.STARTED) {
                startSeek()
                ivPlay.setImageResource(R.mipmap.window_zanting)
            } else {
                ivPlay.setImageResource(R.mipmap.window_play)
            }
            tvMusicName.text = AudioPlayer.getInstance().getNowPlaying()!!.music_title
            tvIntor.text = AudioPlayer.getInstance().getNowPlaying()!!.music_desc
            ivMusicImg.loadImage(AudioPlayer.getInstance().getNowPlaying()!!.music_img)
        }.registerInBus(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.rlHome -> {
                next = 0
            }
            R.id.rlHeath -> {
                next = 1
            }
            R.id.rlOil -> {
                next = 2
            }
            R.id.rlCenter -> {
                next = 3
            }
        }
        jumpFragment(next)
    }

    private fun jumpFragment(next: Int) {
        if (last != next) {
            var ft: FragmentTransaction = supportFragmentManager.beginTransaction().hide(fragment[last])
            if (!fragment[next].isAdded) {
                ft.add(R.id.fView, fragment[next])
            }
            ft.show(fragment[next]).commitAllowingStateLoss()
            upButton(next)
        }
        last = next
    }

    private fun upButton(int: Int) {
        tvHome.isSelected = false
        tvHeath.isSelected = false
        tvOil.isSelected = false
        tvCenter.isSelected = false
        when (int) {
            0 -> {
                tvHome.isSelected = true
            }
            1 -> {
                tvHeath.isSelected = true
            }
            2 -> {
                tvOil.isSelected = true
            }
            3 -> {
                tvCenter.isSelected = true
            }
        }
    }

    private var handler = Handler()

    private var run = Runnable {
        startSeek()
    }

    private fun startSeek() {
        tvTime.text = toDoubleInt(AudioPlayer.getInstance().getCurrentPosition() / 1000 / 60) + ":" + toDoubleInt(AudioPlayer.getInstance().getCurrentPosition() / 1000 % 60)
        tvAllTime.text = toDoubleInt(AudioPlayer.getInstance().getDuration() / 1000 / 60) + ":" + toDoubleInt(AudioPlayer.getInstance().getDuration() / 1000 % 60)
        handler.postDelayed(run, 500)
    }

    override fun onResume() {
        super.onResume()
        if (AudioPlayer.getInstance().getStatus() == ManagedMediaPlayer.Status.STARTED) {
            clWindow.visibility = View.VISIBLE
        }
        mPresenter.getVersion()
        mPresenter.getUserInfo()
    }

//    private var mLastY: Int = 0
//    private var layoutParams: ConstraintLayout.LayoutParams? = null
//
//    @SuppressLint("ClickableViewAccessibility")
//    override fun onTouch(v: View, event: MotionEvent): Boolean {
//        val mInScreenY = event.rawY.toInt()
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                mLastY = event.rawY.toInt()
//            }
//            MotionEvent.ACTION_MOVE -> {
//                layoutParams!!.bottomMargin += mInScreenY - mLastY
//                mLastY = mInScreenY
//                Log.e("更新window的位置", "是的${mInScreenY - mLastY}")
//                upWindow()
//            }
//            MotionEvent.ACTION_UP -> {
//            }
//        }
//        return false
//    }
//
//    private fun upWindow() {
//        clWindow.layoutParams = layoutParams
//    }
}
