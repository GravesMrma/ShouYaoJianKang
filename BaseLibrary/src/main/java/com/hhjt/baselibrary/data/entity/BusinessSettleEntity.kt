package com.hhjt.baselibrary.data.entity

import com.hhjt.baselibrary.ui.adapter.IChoseImageEntity

/**
 *
 */
data class BusinessSettleEntity(var storage_path: String) : IChoseImageEntity {
    override fun getFilePath(): String {
        return storage_path
    }
}