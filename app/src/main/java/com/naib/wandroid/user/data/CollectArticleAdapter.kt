package com.naib.wandroid.user.data

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseRecyclerAdapter
import com.naib.wandroid.global.Article
import com.naib.wandroid.global.BaseArticleAdapter

/**
 *  Created by Naib on 2020/6/11
 */
class CollectArticleAdapter() :
    BaseArticleAdapter<CollectArticleAdapter.CollectArticleViewHolder>() {

    constructor(articles: List<Article>) : this() {
        this.data.addAll(articles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectArticleViewHolder {
        return CollectArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_collect_article_item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CollectArticleViewHolder, position: Int) {
        val article = data[position]
        holder.titleView.text = article.title
        var resources = holder.itemView.resources
        var author = article.author
        if (TextUtils.isEmpty(author)) {
            author = article.shareUser
        }
        holder.descView.text = String.format(
            resources.getString(R.string.profile_collect_article_desc),
            author, article.chapterName, article.niceDate
        )
        holder.itemView.setOnClickListener {
            onItemClickListener?.apply {
                onItemClick(article, position)
            }
        }
        if (position == 0) {
            (holder.itemView.layoutParams as ViewGroup.MarginLayoutParams).leftMargin =
                resources.getDimensionPixelSize(R.dimen.dp_20)
        }
    }

    class CollectArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView = itemView.findViewById(R.id.collect_article_title)
        var descView: TextView = itemView.findViewById(R.id.collect_article_desc)
    }
}