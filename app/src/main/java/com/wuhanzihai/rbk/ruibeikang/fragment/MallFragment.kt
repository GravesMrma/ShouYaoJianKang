package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.GoodsDetailActivity
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoader
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GoodsDetailReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemFifteen
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemMallItem
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemMallMenu
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemTen
import com.wuhanzihai.rbk.ruibeikang.presenter.MallPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MallView
import kotlinx.android.synthetic.main.fragment_mall.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class MallFragment : BaseMvpFragment<MallPresenter>(), MallView {

    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onMallIndexResult(result: MallBean) {

//        mBanner.update(bannerList)

        //        mBanner.setImageLoader(FrescoBannerLoader(false))
//                .start()
        mBanner.setImageLoader(FrescoBannerLoader(true))
                .setImages(result.banner.item)
                .start()
        menuList.addAll(result.tjcategory.item)
        adapterMenu.notifyDataSetChanged()

        mBannerCoupon.setImageLoader(FrescoBannerLoader(true))
                .setImages(result.coupon.item)
                .start()

        mBannerNew.setImageLoader(FrescoBannerLoader(true))
                .setImages(result.newpeople.item)
                .start()

        list.addAll(result.theme)
        adapter.notifyDataSetChanged()
    }

    private lateinit var menuList: MutableList<tjcategoryItem>
    private lateinit var adapterMenu: BaseQuickAdapter<tjcategoryItem, BaseViewHolder>

    private lateinit var list: MutableList<themeItem>
    private lateinit var adapter: BaseQuickAdapter<themeItem, BaseViewHolder>

    private lateinit var dividerItemMallItem: DividerItemMallItem

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
        adapterMenu = object : BaseQuickAdapter<tjcategoryItem, BaseViewHolder>(R.layout.item_mall_menu, menuList) {
            override fun convert(helper: BaseViewHolder?, item: tjcategoryItem?) {
                helper!!.setText(R.id.tvText, item!!.name)
                helper.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.icon)
            }
        }
        rvMenu.adapter = adapterMenu
        rvMenu.layoutManager = GridLayoutManager(act, 4)
        rvMenu.addItemDecoration(DividerItemMallMenu(act))

        adapterMenu.setOnItemClickListener { _, _, position ->
        }

        list = mutableListOf()
        dividerItemMallItem = DividerItemMallItem(act)

        adapter = object : BaseQuickAdapter<themeItem, BaseViewHolder>(R.layout.item_mall_item, list) {
            override fun convert(helper: BaseViewHolder?, item: themeItem?) {
                helper!!.getView<SimpleDraweeView>(R.id.ivBg).loadImage(item!!.ptheme_bg_img)

                var adapterItem = object : BaseQuickAdapter<productlistItem, BaseViewHolder>(R.layout.item_mall_item_item, item.productlist) {
                    override fun convert(helper: BaseViewHolder?, item: productlistItem?) {
                        helper!!.setText(R.id.tvText, item!!.name)
                                .setText(R.id.tvPrice, item.price + "¥")
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

//        adapter.setOnItemClickListener { adapter, view, position ->
//            startActivity<GoodsDetailActivity>("id" to 3)
//
//        }
    }

    private fun initData() {
        mPresenter.mallIndex()
    }
}