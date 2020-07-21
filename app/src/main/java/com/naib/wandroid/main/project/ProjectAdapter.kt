package com.naib.wandroid.main.project

import android.graphics.Color
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naib.wandroid.R
import com.naib.wandroid.main.article.Article
import com.naib.wandroid.main.article.BaseArticleAdapter

/**
 *  Created by Naib on 2020/6/11
 */
class ProjectAdapter() : BaseArticleAdapter<ProjectAdapter.BlogViewHolder>() {

    constructor(articles: List<Article>) : this() {
        this.data.addAll(articles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_home_project_item_view, parent, false)
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
        holder.descView.text = article.desc
        holder.picView.let {
            Glide.with(it)
                .load(Uri.parse(article.envelopePic))
                .into(it)
        }

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
                this.setColorFilter(resources.getColor(R.color.color_like))
            } else {
                colorFilter = null
            }
        }
    }

    class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView = itemView.findViewById(R.id.home_project_item_title)
        var authorView: TextView = itemView.findViewById(R.id.home_project_item_author)
        var categoryView: TextView = itemView.findViewById(R.id.home_project_item_category)
        var descView: TextView = itemView.findViewById(R.id.home_project_item_desc)
        var picView: ImageView = itemView.findViewById(R.id.home_project_item_pic)
        var timeView: TextView = itemView.findViewById(R.id.home_project_item_time)
        var favoriteView: ImageView = itemView.findViewById(R.id.home_project_item_favorite)
    }
}