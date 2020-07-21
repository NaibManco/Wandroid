package com.naib.wandroid.global

import android.view.View

/**
 *  Created by Naib on 2020/7/20
 */
interface OnItemLongClickListener<T> {
    fun onItemLongClick(view: View, t: T, position: Int)
}