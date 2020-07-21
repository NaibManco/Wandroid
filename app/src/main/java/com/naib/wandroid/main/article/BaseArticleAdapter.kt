package com.naib.wandroid.main.article

import androidx.recyclerview.widget.RecyclerView
import com.naib.wandroid.base.BaseRecyclerAdapter
import com.naib.wandroid.global.OnItemClickListener
import com.naib.wandroid.global.OnLikeClickListener

/**
 *  Created by Naib on 2020/7/13
 */
abstract class BaseArticleAdapter<VH : RecyclerView.ViewHolder> :
    BaseRecyclerAdapter<Article, VH>() {
    var onItemClickListener: OnItemClickListener<Article>? = null
    var onLikeClickListener: OnLikeClickListener<Article>? = null
}