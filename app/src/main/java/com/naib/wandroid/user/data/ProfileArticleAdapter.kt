package com.naib.wandroid.user.data

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naib.wandroid.R
import com.naib.wandroid.global.OnItemLongClickListener
import com.naib.wandroid.main.article.Article
import com.naib.wandroid.main.article.BaseArticleAdapter

/**
 *  Created by Naib on 2020/6/11
 */
class ProfileArticleAdapter() :
    BaseArticleAdapter<ProfileArticleAdapter.CollectArticleViewHolder>() {
    var onItemLongClickListener: OnItemLongClickListener<Article>? = null
    var showAuthor = true

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
        if (showAuthor) {
            holder.descView.text = String.format(
                resources.getString(R.string.profile_article_item_desc),
                author, article.chapterName, article.niceDate
            )
        } else {
            holder.descView.text = String.format(
                resources.getString(R.string.profile_article_item_desc_without_author),
                article.chapterName, article.niceDate
            )
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.apply {
                onItemClick(article, position)
            }
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.apply {
                onItemLongClick(it, article, position)
            } != null
        }
        if (position == 0) {
            (holder.itemView.layoutParams as ViewGroup.MarginLayoutParams).leftMargin =
                resources.getDimensionPixelSize(R.dimen.dp_20)
        } else {
            (holder.itemView.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = 0
        }
    }

    class CollectArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView = itemView.findViewById(R.id.collect_article_title)
        var descView: TextView = itemView.findViewById(R.id.collect_article_desc)
    }
}