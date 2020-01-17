package com.wuhanzihai.rbk.ruibeikang.common

import android.content.Context
import android.os.Handler
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.hhjt.baselibrary.common.BaseConstant
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.data.entity.BannerEntity
import com.wuhanzihai.rbk.ruibeikang.data.entity.IsRebateBean
import com.wuhanzihai.rbk.ruibeikang.widgets.CircleImageView
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import per.goweii.anylayer.AnyLayer
import java.text.SimpleDateFormat
import java.util.*

fun CircleImageView.loadImage(url: String) {
    if (Patterns.WEB_URL.matcher(url).matches()) {
        GlideApp.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_empty_item1)
                .error(R.mipmap.ic_empty_item1)
                .into(this)
    } else {
        GlideApp.with(context)
                .load(BaseConstant.BASE_URL + url)
                .placeholder(R.mipmap.ic_empty_item1)
                .error(R.mipmap.ic_empty_item1)
                .into(this)
    }
}

fun ImageView.loadImage(url: String) {
    if (Patterns.WEB_URL.matcher(url).matches()) {
        GlideApp.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_empty_item1)
                .error(R.mipmap.ic_empty_item1)
                .into(this)
    } else {
        GlideApp.with(context)
                .load(BaseConstant.BASE_URL + url)
                .placeholder(R.mipmap.ic_empty_item1)
                .error(R.mipmap.ic_empty_item1)
                .into(this)
    }
}

fun getNowDataString(): String {
    return SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis()))
}

fun getNowTimeString(): String {
    return SimpleDateFormat("HH:mm").format(Date(System.currentTimeMillis()))
}

fun getEmptyView(context: Context, placeHolder: Int, desc: String = ""): View {
    val view = LayoutInflater.from(context).inflate(R.layout.empty_view, null)
    view.find<ImageView>(R.id.ivImg).setImageResource(placeHolder)
    view.find<TextView>(R.id.tvText).text = desc
    return view
}

fun getEmptyView(context: Context, desc: String = ""): View {
    val view = LayoutInflater.from(context).inflate(R.layout.empty_view, null)
    view.find<TextView>(R.id.tvText).text = desc
    return view
}

fun toDoubleInt(a: Int): String {
    if (a < 10) {
        return "0$a"
    }
    return a.toString()
}

fun setOnBannerListener(context: Context, bannerEntity: BannerEntity) {
    when (bannerEntity.linktype) {
        1 -> context.startActivity<MusicTherapyActivity>("id" to bannerEntity.link)
        2 -> context.startActivity<UnifiedWebActivity>("id" to bannerEntity.link)
        3 -> context.startActivity<HealthCallActivity>("id" to bannerEntity.link)
        4 -> context.startActivity<GoodsDetailActivity>("id" to bannerEntity.link)
        5 -> context.startActivity<HealthClassActivity>()
        6 -> context.startActivity<MusicTherapyActivity>()
        7 -> context.startActivity<HealthInfoActivity>()
        8 -> context.startActivity<SingleTravelActivity>()
        9 -> context.startActivity<HealthFoodActivity>("id" to bannerEntity.link)
        10 -> {
            var result = Hawk.get<IsRebateBean>(BaseConstant.ISREBATE_DATA)
            if (result != null) {
                if (result.is_agent == 2) {
                    context.startActivity<RebateAuthActivity>()
                } else {
                    context.startActivity<RebateActivity>()
                }
            }
        }
        11 -> context.startActivity<InterrogationActivity>()
        12 -> context.startActivity<ExchangeCouponActivity>()
        13 -> {
            var result = Hawk.get<IsRebateBean>(BaseConstant.ISREBATE_DATA)
            if (result != null) {
                if (result.is_agent == 2) {
                    context.startActivity<RebateAuthActivity>()
                } else {
                    context.startActivity<ShareHealthActivity>()
                }
            }
        }
//        12 -> context.startActivity<StandardWebActivity>("title" to "专家问诊"
//                , "data" to "http://www.hcjiankang.com/androidimg/wenzheng.html")
    }
}

fun showTextDesc(context: Context, text: String) {
    val anyLayer = AnyLayer.with(context)
            .contentView(R.layout.layout_text_desc)
            .backgroundColorRes(R.color.clarity_50)
            .gravity(Gravity.CENTER)
            .cancelableOnTouchOutside(true)
            .cancelableOnClickKeyBack(true)
    anyLayer.getView<TextView>(R.id.tvText).text = text
    anyLayer.show()
    Handler().postDelayed({
        anyLayer.dismiss()
    }, 1000)
}

fun showChoseText(context: Context, title: String, content: String, sure: String, onDone: () -> Unit) {
    val anyLayer = AnyLayer.with(context)
            .contentView(R.layout.layout_chose_text)
            .backgroundColorRes(R.color.clarity_50)
            .gravity(Gravity.CENTER)
            .cancelableOnTouchOutside(true)
            .cancelableOnClickKeyBack(true)
            .onClick(R.id.tvCancel) { anyLayer, v ->
                anyLayer.dismiss()
            }
            .onClick(R.id.tvSure) { anyLayer, v ->
                onDone()
                anyLayer.dismiss()
            }
    anyLayer.getView<TextView>(R.id.tvTitle).text = title
    anyLayer.getView<TextView>(R.id.tvContent).text = content
    anyLayer.getView<TextView>(R.id.tvSure).text = sure
    anyLayer.show()
}

fun showChoseText(context: Context, content: String, onDone: () -> Unit) {
    val anyLayer = AnyLayer.with(context)
            .contentView(R.layout.layout_chose_text)
            .backgroundColorRes(R.color.clarity_50)
            .gravity(Gravity.CENTER)
            .cancelableOnTouchOutside(true)
            .cancelableOnClickKeyBack(true)
            .onClick(R.id.tvCancel) { anyLayer, v ->
                anyLayer.dismiss()
            }
            .onClick(R.id.tvSure) { anyLayer, v ->
                onDone()
                anyLayer.dismiss()
            }
    anyLayer.getView<TextView>(R.id.tvTitle).text = "提示"
    anyLayer.getView<TextView>(R.id.tvContent).text = content
    anyLayer.getView<TextView>(R.id.tvSure).text = "确定"
    anyLayer.show()
}

fun showChoseText(context: Context, content: String, sure: String, onDone: () -> Unit) {
    val anyLayer = AnyLayer.with(context)
            .contentView(R.layout.layout_chose_text)
            .backgroundColorRes(R.color.clarity_50)
            .gravity(Gravity.CENTER)
            .cancelableOnTouchOutside(true)
            .cancelableOnClickKeyBack(true)
            .onClick(R.id.tvCancel) { anyLayer, v ->
                anyLayer.dismiss()
            }
            .onClick(R.id.tvSure) { anyLayer, v ->
                onDone()
                anyLayer.dismiss()
            }
    anyLayer.getView<TextView>(R.id.tvTitle).text = "提示"
    anyLayer.getView<TextView>(R.id.tvContent).text = content
    anyLayer.getView<TextView>(R.id.tvSure).text = sure
    anyLayer.show()
}

fun showTextDesc(context: Context, text: String, doMore: () -> Unit) {
    val anyLayer = AnyLayer.with(context)
            .contentView(R.layout.layout_text_desc)
            .backgroundColorRes(R.color.clarity_50)
            .gravity(Gravity.CENTER)
            .cancelableOnTouchOutside(true)
            .cancelableOnClickKeyBack(true)
    anyLayer.getView<TextView>(R.id.tvText).text = text
    anyLayer.show()
    Handler().postDelayed({
        doMore()
        anyLayer.dismiss()
    }, 1000)
}