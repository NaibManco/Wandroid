package com.naib.wandroid.global

import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naib.wandroid.R

/**
 *  Created by Naib on 2020/6/11
 */
class ArticleAdapter() : BaseArticleAdapter<ArticleAdapter.BlogViewHolder>() {

    constructor(articles: List<Article>) : this() {
        this.data.addAll(articles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_home_article_item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val article = data[position]
        holder.titleView.text = article.title
        var resources = holder.itemView.resources
        if (TextUtils.isEmpty(article.shareUser)) {
            holder.authorView.text =
                String.format(resources.getString(R.string.article_author), article.author)
        } else {
            holder.authorView.text =
                String.format(resources.getString(R.string.article_share_user), article.shareUser)
        }

        holder.timeView.text = article.niceDate
        holder.itemView.setOnClickListener {
            onItemClickListener?.apply {
                onItemClick(article, position)
            }
        }
        holder.favoriteView.apply {
            setOnClickListener {
                onLikeClickListener?.apply {
                    onFavoriteClick(it as ImageView, article, position)
                }
            }
            if (article.collect) {
                this.setColorFilter(Color.RED)
            } else {
                colorFilter = null
            }
        }
    }

    class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView = itemView.findViewById(R.id.home_article_item_title)
        var authorView: TextView = itemView.findViewById(R.id.home_article_item_author)
        var categoryView: TextView = itemView.findViewById(R.id.home_article_item_category)
        var timeView: TextView = itemView.findViewById(R.id.home_article_item_time)
        var favoriteView: ImageView = itemView.findViewById(R.id.home_article_item_favorite)
    }
}