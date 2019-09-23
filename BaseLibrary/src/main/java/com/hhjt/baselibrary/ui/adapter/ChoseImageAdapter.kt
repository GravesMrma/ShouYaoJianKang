package com.hhjt.baselibrary.ui.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.R
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import org.jetbrains.anko.find
import java.io.File

/**
 * Created by wx on 2018/6/26
 */
class ChoseImageAdapter<T : IChoseImageEntity>(var widthLast: Int, var list: MutableList<T>, var limit: Int = 9) :
    RecyclerView.Adapter<ChoseImageAdapter.ViewHolder>() {

    //ItemClick事件
    var mItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoseImageAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_chose_image, parent, false)
        return ViewHolder(widthLast, view)
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(holder: ChoseImageAdapter.ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            if (mItemClickListener != null)
                mItemClickListener!!.onItemClick(position)
        }

        if (position == list.size) {
            if (list.size == limit) {
                holder.mIv.visibility = View.GONE
            }
            holder.mIv.setImageResource(R.drawable.rj_tj)
            holder.mBtnDelete.visibility = View.GONE
            return
        }

        val file = File(list[position].getFilePath())
        if (file.exists()) {
            holder.mIv.setImageURI(Uri.fromFile(file))
        } else {
            holder.mIv.loadImage(list[position].getFilePath())
        }

        holder.mBtnDelete.apply {
            visibility = View.VISIBLE
            onClick {
                list.removeAt(position)
                notifyDataSetChanged()
            }
        }

    }

    class ViewHolder(widthLast: Int, view: View) : RecyclerView.ViewHolder(view) {
        val mIv = view.find<SimpleDraweeView>(R.id.mIv)
        val mBtnDelete = view.find<Button>(R.id.btn_delete)

//        init {
//            mIv.layoutParams.width = widthLast
//        }
    }

    /*
       ItemClick事件声明
    */
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mItemClickListener = listener
    }

}