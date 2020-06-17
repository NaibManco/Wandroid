package com.naib.wandroid.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 *  Created by Naib on 2020/6/15
 */
open abstract class BaseRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(itemView: View, position: Int)
    }

}