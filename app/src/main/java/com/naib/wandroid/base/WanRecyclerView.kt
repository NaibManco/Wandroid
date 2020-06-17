package com.naib.wandroid.base

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *  Created by Naib on 2020/6/16
 */
class WanRecyclerView(context: Context) : RecyclerView(context) {

    interface OnLoadMoreListener {
        fun onLoadMore(recyclerView: WanRecyclerView)
    }

    var loadMoreListener: OnLoadMoreListener? = null

    init {
        addOnScrollListener(ScrollListenerImpl())
    }

    inner class ScrollListenerImpl : OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (!recyclerView.canScrollVertically(1)) {
                loadMoreListener?.apply {
                    this.onLoadMore(this@WanRecyclerView)
                }
            }
        }
    }
}