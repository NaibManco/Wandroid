package com.naib.wandroid.base.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 *  Created by Naib on 2020/6/16
 */
class WanRecyclerView : RecyclerView {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
    }

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