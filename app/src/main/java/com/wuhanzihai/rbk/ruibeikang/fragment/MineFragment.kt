package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.jaeger.library.StatusBarUtil
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoader
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.common.setOnBannerListener
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
        tvCouponNumber.text = result.usercouponcount.toString()
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
        tvAdv.startSlide(result.item)
    }

    override fun onIsRebateResult(result: IsRebateBean) {
        Hawk.put(BaseConstant.ISREBATE_DATA, result)
        is_agent = result.is_agent
    }

    override fun onMineBanner(result: Banner) {
        bannerList.clear()
        bannerList.addAll(result.item)
        banSign.update(result.item)
    }

    private lateinit var list: MutableList<BannerEntity>
    private lateinit var adapter: BaseQuickAdapter<BannerEntity, BaseViewHolder>

    private lateinit var serList: MutableList<MineServiceBean>
    private lateinit var serAdapter: BaseQuickAdapter<MineServiceBean, BaseViewHolder>

    private lateinit var bannerList: MutableList<BannerEntity>

    private var is_agent = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StatusBarUtil.setLightMode(act)

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
        }
        tvMore.onClick {
            startActivity<OrderActivity>()
        }
        lCoupon.onClick {
            startActivity<CouponActivity>()
        }
        llCard.onClick {
            startActivity<MyCardActivity>()
        }

        bannerList = mutableListOf()
        banSign.setImageLoader(FrescoBannerLoader(false)).start()
        banSign.setOnBannerListener { setOnBannerListener(act, bannerList[it]) }
//        ivSign.onClick {
////            startActivity<GoodsDetailActivity>("id" to 145)
//            mPresenter.isRebate()
//        }
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
        serList.add(MineServiceBean("分享赚钱", R.mipmap.fw_mid_icon_smrz))
        serList.add(MineServiceBean("健康档案", R.mipmap.fw_mid_icon_jkda))
        serList.add(MineServiceBean("领券中心", R.mipmap.ic_shabi1))
        serList.add(MineServiceBean("常见问题", R.mipmap.fw_mid_icon_cjwt))
        serList.add(MineServiceBean("电话客服", R.mipmap.fw_mid_icon_zxkf))
        serAdapter = object : BaseQuickAdapter<MineServiceBean, BaseViewHolder>(R.layout.item_mine_service, serList) {
            override fun convert(helper: BaseViewHolder?, item: MineServiceBean?) {
                helper!!.setImageResource(R.id.ivImg, item!!.res)
                        .setText(R.id.tvText, item.title)
                if (helper.layoutPosition == 3) {
                    helper.getView<TextView>(R.id.tvTag).visibility = View.VISIBLE
                }
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
                3 -> {
                    if (is_agent == 1) {
                        startActivity<ShareHealthActivity>()  //分享赚钱
                    } else {
                        startActivity<RebateAuthActivity>()
                    }
                }
                4 -> startActivity<HealthArchivesActivity>()
                5 -> startActivity<ExchangeCouponActivity>()
                6 -> startActivity<StandardWebActivity>("title" to "常见问题"
                        , "data" to "http://api.hcjiankang.com/api/Web/article?id=732")
                7 -> MyUtils.instance.callPhone(act, "4000186617")
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
        mPresenter.mineBanner()
        mPresenter.isRebate()
    }
}