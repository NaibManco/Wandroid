package com.naib.wandroid.base

import androidx.recyclerview.widget.RecyclerView
import com.naib.wandroid.global.OnItemClickListener

/**
 *  Created by Naib on 2020/6/15
 */
open abstract class BaseRecyclerAdapter<T, VH : RecyclerView.ViewHolder?> :
    RecyclerView.Adapter<VH>() {

    protected var data: MutableList<T> = ArrayList()

    fun update(list: List<T>?) {
        list?.apply {
            this@BaseRecyclerAdapter.data.clear()
            this@BaseRecyclerAdapter.data.addAll(this)
            notifyDataSetChanged()
        }
    }

    fun add(list: List<T>?) {
        list?.apply {
            this@BaseRecyclerAdapter.data.addAll(this)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return if (null == data) 0 else data.size
    }
}