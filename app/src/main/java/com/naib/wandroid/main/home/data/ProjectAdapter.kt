package com.naib.wandroid.main.home.data

import android.app.Activity
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naib.wandroid.R
import com.naib.wandroid.base.WebViewActivity

/**
 *  Created by Naib on 2020/6/11
 */
class ProjectAdapter() : RecyclerView.Adapter<ProjectAdapter.BlogViewHolder>() {
    private var articles: MutableList<Article> = ArrayList()

    constructor(articles: List<Article>) : this() {
        this.articles.addAll(articles)
    }

    fun update(articles: List<Article>?) {
        articles?.apply {
            this@ProjectAdapter.articles.clear()
            this@ProjectAdapter.articles.addAll(this)
            notifyDataSetChanged()
        }
    }

    fun add(articles: List<Article>?) {
        articles?.apply {
            this@ProjectAdapter.articles.addAll(articles)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_home_article_item_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if (null == articles) 0 else articles.size
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val article = articles[position]
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
            WebViewActivity.launch(
                article.link!!,
                article.title!!
            )
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