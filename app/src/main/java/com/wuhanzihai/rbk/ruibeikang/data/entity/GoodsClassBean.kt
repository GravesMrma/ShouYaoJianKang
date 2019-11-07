package com.wuhanzihai.rbk.ruibeikang.data.entity


data class GoodsClassBean(
        val child: List<Child>,
        val href: String,
        val icon: String,
        val id: Int,
        val laravel: Int,
        val name: String,
        val pic: String,
        val pid: Int,
        val sort: Int,
        var isCheck: Boolean = false
)

data class Child(
        val child: List<ChildItem>,
        val href: String,
        val icon: String,
        val id: Int,
        val laravel: Int,
        val name: String,
        val pic: String,
        val pid: Int,
        val sort: Int,
        var isCheck: Boolean = false
)

data class ChildItem(
//    val child: List<Any>,
        val href: String,
        val icon: String,
        val id: Int,
        val laravel: Int,
        val name: String,
        val pic: String,
        val pid: Int,
        val sort: Int,
        var isCheck: Boolean = false
)