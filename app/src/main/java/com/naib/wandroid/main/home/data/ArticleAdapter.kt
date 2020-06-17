package com.naib.wandroid.main.home.data

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 *  Created by Naib on 2020/6/11
 */
class BlogAdapter() : RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {
    private lateinit var articles: List<Article>

    constructor(articles: List<Article>) : this() {
        this.articles = articles
    }

    fun update(articles: List<Article>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}