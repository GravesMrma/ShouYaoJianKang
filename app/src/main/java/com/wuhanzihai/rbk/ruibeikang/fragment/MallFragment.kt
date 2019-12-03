package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.AppPrefsUtils
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoader
import com.wuhanzihai.rbk.ruibeikang.common.setOnBannerListener
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemFifteen
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemMallCate
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemMallItem
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemMallMenu
import com.wuhanzihai.rbk.ruibeikang.presenter.MallPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MallView
import kotlinx.android.synthetic.main.fragment_mall.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import per.goweii.anylayer.AnyLayer

class MallFragment : BaseMvpFragment<MallPresenter>(), MallView {

    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule()).build().inject(this)
        mPresenter.mView = this
    }

    private val dialog by lazy {
        val anyLayer =
                AnyLayer.with(act)
                        .contentView(R.layout.layout_mall_adv)
                        .gravity(Gravity.CENTER)
                        .backgroundResource(R.color.clarity_40)
                        .cancelableOnTouchOutside(true)
        anyLayer
    }

    override fun onMallIndexResult(result: MallBean) {
        srView.finishRefresh()
        this.result = result
        mBanner.setImageLoader(FrescoBannerLoader(true))
                .setImages(result.banner.item)
                .start()
        mBanner.setOnBannerListener { setOnBannerListener(act, result.banner.item[it]) }

        menuList.clear()
        menuList.addAll(result.tjcategory.item)
        adapterMenu.notifyDataSetChanged()

        couponBs.clear()
        couponBs.addAll(result.coupon.item)
        mBannerCoupon.update(couponBs)
        newpeopleBs.clear()
        newpeopleBs.addAll(result.newpeople.item)
        mBannerNew.update(newpeopleBs)

        listCate.clear()
        listCate.add(result.hotcategory.item[0])
        listCate.add(result.hotcategory.item[1])
        listCate.add(result.hotcategory.item[2])
        listCate.add(result.hotcategory.item[3])
//        listCate.addAll(result.hotcategory.item)
        adapterCate.notifyDataSetChanged()
        list.clear()
        list.addAll(result.theme)
        adapter.notifyDataSetChanged()

        tvAdv.startSlide(result.proadcast.item as MutableList<String>)

        if (!AppPrefsUtils.getBoolean(BaseConstant.MALL_ADV)) {
            dialog.show()
            AppPrefsUtils.putBoolean(BaseConstant.MALL_ADV, true)
        }
    }

    private lateinit var menuList: MutableList<MallGoodsItem>
    private lateinit var adapterMenu: BaseQuickAdapter<MallGoodsItem, BaseViewHolder>

    private lateinit var list: MutableList<ThemeItemDetail>
    private lateinit var adapter: BaseQuickAdapter<ThemeItemDetail, BaseViewHolder>

    private lateinit var listCate: MutableList<Hotcategory>
    private lateinit var adapterCate: BaseQuickAdapter<Hotcategory, BaseViewHolder>

    private lateinit var dividerItemMallItem: DividerItemMallItem
    private var result: MallBean? = null

    private lateinit var newpeopleBs: MutableList<BannerEntity>
    private lateinit var couponBs: MutableList<BannerEntity>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_mall, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()
    }

    private fun initView() {
        menuList = mutableListOf()
        adapterMenu = object : BaseQuickAdapter<MallGoodsItem, BaseViewHolder>(R.layout.item_mall_menu, menuList) {
            override fun convert(helper: BaseViewHolder?, item: MallGoodsItem?) {
                helper!!.setText(R.id.tvText, item!!.name)
                helper.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.icon)
            }
        }
        rvMenu.adapter = adapterMenu
        rvMenu.layoutManager = GridLayoutManager(act, 4)
        rvMenu.addItemDecoration(DividerItemMallMenu(act))
        adapterMenu.setOnItemClickListener { _, _, position ->
            if (menuList[position].id == 0) {
                startActivity<GoodsClassActivity>()
            } else {
                if (position < 5) {
                    startActivity<HealthCareActivity>("fatherId" to menuList[position].pid,
                            "childId" to menuList[position].id, "title" to "健康生活")
                } else {
                    if (position == 6) {
                        startActivity<HealthCareActivity>("fatherId" to menuList[position].pid,
                                "childId" to menuList[position].id, "title" to "健康生活")
                    } else {
                        startActivity<HealthCareActivity>("fatherId" to menuList[position].pid,
                                "childId" to menuList[position].id, "title" to "健康医疗")
                    }
                }
            }
        }

        newpeopleBs = mutableListOf()
        mBannerNew.setImageLoader(FrescoBannerLoader(false))
                .start()
        mBannerNew.setOnBannerListener { setOnBannerListener(act, newpeopleBs[it]) }

        couponBs = mutableListOf()
        mBannerCoupon.setImageLoader(FrescoBannerLoader(false))
                .start()
//        mBannerCoupon.setOnBannerListener { setOnBannerListener(act,couponBs[it]) }

        listCate = mutableListOf()
        adapterCate = object : BaseQuickAdapter<Hotcategory, BaseViewHolder>(R.layout.item_mall_cate, listCate) {
            override fun convert(helper: BaseViewHolder?, item: Hotcategory?) {
                helper!!.setText(R.id.tvText, item!!.name)
                helper.getView<SimpleDraweeView>(R.id.ivImg).loadImage("http://www.hcjiankang.com/androidimg/ic_gene${helper.layoutPosition + 1}.png")
//                helper.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.pic)
                when (helper.layoutPosition) {
                    0 -> {
                        helper.getView<ConstraintLayout>(R.id.cBones).background = resources.getDrawable(R.drawable.sp_mall_1)
                    }
                    1 -> {
                        helper.getView<ConstraintLayout>(R.id.cBones).background = resources.getDrawable(R.drawable.sp_mall_2)
                    }
                    2 -> {
                        helper.getView<ConstraintLayout>(R.id.cBones).background = resources.getDrawable(R.drawable.sp_mall_3)
                    }
                    3 -> {
                        helper.getView<ConstraintLayout>(R.id.cBones).background = resources.getDrawable(R.drawable.sp_mall_4)
                    }
                }
            }
        }
        rvMallCate.adapter = adapterCate
        rvMallCate.layoutManager = GridLayoutManager(act, 2)
        rvMallCate.addItemDecoration(DividerItemMallCate(act))
        adapterCate.setOnItemClickListener { _, _, position ->
            startActivity<HealthCareActivity>(
                    "fatherId" to listCate[position].pid
                    , "childId" to listCate[position].id
                    , "title" to "细胞营养素")
        }

        list = mutableListOf()
        dividerItemMallItem = DividerItemMallItem(act)
        adapter = object : BaseQuickAdapter<ThemeItemDetail, BaseViewHolder>(R.layout.item_mall_item, list) {
            override fun convert(helper: BaseViewHolder?, item: ThemeItemDetail?) {
                helper!!.getView<SimpleDraweeView>(R.id.ivBg).loadImage(item!!.ptheme_bg_img)
                var adapterItem = object : BaseQuickAdapter<GoodsListItem, BaseViewHolder>(R.layout.item_mall_item_item, item.productlist) {
                    override fun convert(helper: BaseViewHolder?, item: GoodsListItem?) {
                        helper!!.setText(R.id.tvText, item!!.name)
                                .setText(R.id.tvPrice, "¥${item.price}")
                        helper.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.image)
                    }
                }
                helper.getView<RecyclerView>(R.id.rvView).run {
                    adapter = adapterItem
                    layoutManager = GridLayoutManager(act, 1, RecyclerView.HORIZONTAL, false)
                    if (itemDecorationCount != 0) {
                        removeItemDecoration(dividerItemMallItem)
                    }
                    addItemDecoration(dividerItemMallItem)
                }
                adapterItem.setOnItemClickListener { _, _, position ->
                    startActivity<GoodsDetailActivity>("id" to item.productlist[position].product_id)
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemFifteen(act))

        adapter.setOnItemClickListener { _, _, position ->

        }
        ivToCart.onClick {
            startActivity<ShoppingCartActivity>()
        }
        tvCellular.onClick {
            startActivity<HealthCareActivity>("fatherId" to 1, "title" to "细胞营养素")
        }
        cHealCall.onClick {
            startActivity<HealthClassActivity>()
        }

        mBannerCoupon.onClick {
            if (Hawk.get<IsRebateBean>(BaseConstant.ISREBATE_DATA).is_agent == 1) {
                startActivity<ShareHealthActivity>()  //分享赚钱
            } else {
                startActivity<RebateAuthActivity>()
            }
//            startActivity<RebateActivity>()
        }
        tvToSearch.onClick {
            startActivity<SearchActivity>()
        }
        cHealData.onClick {
            startActivity<HealthDataActivity>()
        }
        srView.setOnRefreshListener {
            initData()
        }
        srView.setOnLoadMoreListener {
            srView.setNoMoreData(true)
        }
        ivImg3.onClick {
            startActivity<SysMsgActivity>()
        }

//        adapter.setOnItemClickListener { adapter, view, position ->
//            startActivity<GoodsDetailActivity>("id" to 3)
//
//        }



    }

    private fun initData() {
        mPresenter.mallIndex()
    }
}