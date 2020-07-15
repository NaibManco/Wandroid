package com.naib.wandroid.global

import androidx.recyclerview.widget.RecyclerView
import com.naib.wandroid.base.BaseRecyclerAdapter

/**
 *  Created by Naib on 2020/7/13
 */
abstract class BaseArticleAdapter<VH : RecyclerView.ViewHolder> :
    BaseRecyclerAdapter<Article, VH>() {
    var onItemClickListener: OnItemClickListener<Article>? = null
    var onLikeClickListener: OnLikeClickListener<Article>? = null
}