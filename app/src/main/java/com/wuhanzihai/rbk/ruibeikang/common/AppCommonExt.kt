package com.wuhanzihai.rbk.ruibeikang.common

import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.widgets.CircleImageView

fun CircleImageView.loadImage(url: String) {
    if (android.util.Patterns.WEB_URL.matcher(url).matches()) {
        GlideApp.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_acc1)
                .error(R.mipmap.ic_acc1)
                .into(this)
    } else {
        GlideApp.with(context)
                .load(com.hhjt.baselibrary.common.BaseConstant.BASE_URL + url)
                .placeholder(R.mipmap.ic_acc1)
                .error(R.mipmap.ic_acc1)
                .into(this)
    }
}