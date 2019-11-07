package com.wuhanzihai.rbk.ruibeikang.common

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.widget.ImageView
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.BannerEntity
import com.youth.banner.loader.ImageLoader


/**
 * Created by wx on 2018/8/13
 */
class FrescoBannerLoader(var isRound: Boolean) : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView) {
//        val uri = Uri.parse(BaseConstant.IMAGE_ADDRESS + (path as BannerEntity).ad_code)
//        imageView?.setImageURI(uri)
        (imageView as SimpleDraweeView).loadImage((path as BannerEntity).url)
    }

    override fun createImageView(context: Context): ImageView {

        if (isRound){
            return LayoutInflater.from(context).inflate(R.layout.banner_img_round, null) as SimpleDraweeView
        }else{
            return LayoutInflater.from(context).inflate(R.layout.banner_img, null) as SimpleDraweeView
        }
//        return SimpleDraweeView(context).also {
//            val hierarchyBuilder = GenericDraweeHierarchyBuilder(context.resources)
////            hierarchyBuilder.failureImage = ContextCompat.getDrawable(context, R.mipmap.holder_banner)
////            hierarchyBuilder.placeholderImage = ContextCompat.getDrawable(context, R.mipmap.holder_banner)
//            if (isCenter){
//                hierarchyBuilder.actualImageScaleType = ScalingUtils.ScaleType.CENTER
//            }else{
//                hierarchyBuilder.failureImageScaleType = ScalingUtils.ScaleType.FIT_XY
//                hierarchyBuilder.placeholderImageScaleType = ScalingUtils.ScaleType.FIT_XY
//            }
//            it.hierarchy = hierarchyBuilder.build()
//        }
    }
}