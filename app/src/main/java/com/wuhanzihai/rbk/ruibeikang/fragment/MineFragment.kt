package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemTen
import com.wuhanzihai.rbk.ruibeikang.presenter.MinePresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MineView
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

class MineFragment : BaseMvpFragment<MinePresenter>(), MineView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onUserInfoResult(result: LoginData) {
        GlobalBaseInfo.setBaseInfo(result)
        if (result.sex == 1) {
            ivImg.loadImage("http://www.hcjiankang.com/androidimg/mid_icon_shuaige_s.png")
        } else {
            ivImg.loadImage("http://www.hcjiankang.com/androidimg/mid_icon_meinv_s.png")
        }
//        ivImg.loadImage(result.head_pic)
        tvName.text = result.nickname
        lvView.setScore(result.level.toFloat())
        when (result.level) {
            1 -> ivLevel.setImageResource(R.mipmap.ic_minelevel1)
            2 -> ivLevel.setImageResource(R.mipmap.ic_minelevel2)
            3 -> ivLevel.setImageResource(R.mipmap.ic_minelevel3)
            4 -> ivLevel.setImageResource(R.mipmap.ic_minelevel4)
            5 -> ivLevel.setImageResource(R.mipmap.ic_minelevel5)
        }
    }

    override fun onMineResult(result: MineBean) {
        list.clear()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
        srView.finishRefresh()
    }

    override fun onMineAdvResult(result: MineAdv) {
        mineAdv = result
        handler.removeCallbacks(run)
        startAnimation()
        isRun = true
    }

    override fun onIsRebateResult(result: IsRebateBean) {
        if (result.isinfo == 0){
            startActivity<RebateAuthActivity>()
        }else{
            startActivity<RebateActivity>()
        }
    }

    private lateinit var list: MutableList<BannerEntity>
    private lateinit var adapter: BaseQuickAdapter<BannerEntity, BaseViewHolder>

    private lateinit var serList: MutableList<MineServiceBean>
    private lateinit var serAdapter: BaseQuickAdapter<MineServiceBean, BaseViewHolder>

    private var mineAdv: MineAdv? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StatusBarUtil.setLightMode(act)

        ivSign.loadImage("http://www.hcjiankang.com/androidimg/ic_fanli.png")

        initView()

        initData()
    }

    private fun initView() {
        clOrder1.onClick { startActivity<OrderActivity>("index" to 1) }
        clOrder2.onClick { startActivity<OrderActivity>("index" to 2) }
        clOrder3.onClick { startActivity<OrderActivity>("index" to 3) }
        clOrder4.onClick { startActivity<OrderActivity>("index" to 4) }
        clOrder5.onClick {
            startActivity<StandardWebActivity>("title" to "在线客服"
                    , "data" to "http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT35316404&lng=cn")
        }
        ivMsg.onClick {
            startActivity<SysMsgActivity>()
//            startActivity<WelcomeActivity>()
//            startActivity<SetTagActivity>()
        }
        tvMore.onClick {
            startActivity<OrderActivity>()
        }
        lCoupon.onClick {
            startActivity<CouponActivity>()
        }
        ivSign.onClick {
//            startActivity<GoodsDetailActivity>("id" to 145)
            mPresenter.isRebate()
        }
        ivSet.onClick {
            startActivity<SetActivity>()
        }
        llCollect.onClick {
            startActivity<CollectActivity>()
        }

        serList = mutableListOf()
        serList.add(MineServiceBean("服务订单", R.mipmap.fw_mid_icon_dd))
        serList.add(MineServiceBean("检测报告", R.mipmap.fw_mid_icon_tjbg))
        serList.add(MineServiceBean("地址管理", R.mipmap.fw_mid_icon_dzgl))
        serList.add(MineServiceBean("实名认证", R.mipmap.fw_mid_icon_smrz))
        serList.add(MineServiceBean("健康档案", R.mipmap.fw_mid_icon_jkda))
        serList.add(MineServiceBean("兑换中心", R.mipmap.fw_mid_icon_dhzx))
        serList.add(MineServiceBean("常见问题", R.mipmap.fw_mid_icon_cjwt))
        serList.add(MineServiceBean("电话客服", R.mipmap.fw_mid_icon_zxkf))
        serAdapter = object : BaseQuickAdapter<MineServiceBean, BaseViewHolder>(R.layout.item_mine_service, serList) {
            override fun convert(helper: BaseViewHolder?, item: MineServiceBean?) {
                helper!!.setImageResource(R.id.ivImg, item!!.res)
                        .setText(R.id.tvText, item.title)
            }
        }
        rvView.adapter = serAdapter
        rvView.layoutManager = GridLayoutManager(act, 4)
        rvView.addItemDecoration(DividerItemTen(act))
        serAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> startActivity<OrderServiceActivity>()
                1 -> startActivity<CheckReportActivity>()
                2 -> startActivity<AddressActivity>()
                3 -> startActivity<AuthActivity>()
                4 -> startActivity<HealthArchivesActivity>()
                5 -> startActivity<CouponActivity>()
                6 -> startActivity<StandardWebActivity>("title" to "常见问题"
                        , "data" to "http://api.hcjiankang.com/api/Web/article?id=732")
                7 -> MyUtils.myUtils.callPhone(act, "4000186617")
            }
        }

        list = mutableListOf()
        adapter = object : BaseQuickAdapter<BannerEntity, BaseViewHolder>(R.layout.item_image_mine, list) {
            override fun convert(helper: BaseViewHolder?, item: BannerEntity?) {
                helper!!.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item!!.url)
            }
        }
        dvView.adapter = adapter
        dvView.setItemTransformer(ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.95f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build())
        adapter.setOnItemClickListener { adapter, view, position ->
            startActivity<GoodsDetailActivity>("id" to list[position].link)
        }

        srView.setOnRefreshListener {
            initData()
        }
    }

    private fun initData() {
        mPresenter.getUserInfo()
        mPresenter.mineIndex()
        mPresenter.userAdv()
    }

    private var index = 0
    private var handler = Handler()
    private var isRun = false

    private fun startAnimation() {
        if (!isRun){
            return
        }
        if (mineAdv != null) {
            tvAdv.text = mineAdv!!.item[index % mineAdv!!.item.size]
        }
        val animationSet = AnimationSet(true)
        val animation = TranslateAnimation(0f, 0f, 40f, 0f)
        animation.duration = 800
        animationSet.addAnimation(animation)
        animationSet.fillAfter = true
        tvAdv.startAnimation(animationSet)
        index++
        handler.postDelayed(run, 3000)
    }

    private fun stopAnimation(){
        handler.removeCallbacks(run)
    }

    private var run = Runnable {
        startAnimation()
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            isRun = false
            stopAnimation()
        } else {
            isRun = true
            startAnimation()
        }
    }

}