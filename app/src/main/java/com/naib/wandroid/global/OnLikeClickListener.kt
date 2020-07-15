package com.naib.wandroid.global

import android.widget.ImageView

/**
 *  Created by Naib on 2020/7/13
 */
interface OnLikeClickListener<T> {
    fun onFavoriteClick(view: ImageView, t: T, position: Int)
}