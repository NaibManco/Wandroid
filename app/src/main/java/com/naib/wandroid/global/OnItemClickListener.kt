package com.naib.wandroid.global

/**
 *  Created by Naib on 2020/6/23
 */
interface OnItemClickListener<T> {

    fun onItemClick(t: T, position: Int)
}