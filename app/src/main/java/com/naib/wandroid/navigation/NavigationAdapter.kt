package com.naib.wandroid.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naib.wandroid.R
import com.naib.wandroid.WanApplication
import com.naib.wandroid.base.BaseRecyclerAdapter
import com.naib.wandroid.base.WebViewActivity
import com.naib.wandroid.base.widget.LabelLayout
import com.naib.wandroid.main.article.Article

/**
 *  Created by Naib on 2020/7/20
 */
class NavigationAdapter :
    BaseRecyclerAdapter<Navigation, NavigationAdapter.NavigationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationViewHolder {
        return NavigationViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_navigation_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NavigationViewHolder, position: Int) {
        val navigation = data[position]
        holder.titleView.text = navigation.name
        holder.titleView.setOnClickListener {
            if (holder.labelLayout.visibility == View.GONE) {
                holder.labelLayout.visibility = View.VISIBLE
            } else {
                holder.labelLayout.visibility = View.GONE
            }
        }
        holder.labelLayout.visibility = View.GONE
        holder.labelLayout.setAdapter(LabelLayout.Adapter(createLabelArray(navigation.articles)))
        holder.labelLayout.setOnItemClickListener { position, label ->
            val article = navigation.articles?.get(position)
            article?.link?.let { WebViewActivity.launch(it, label) }
        }
    }

    private fun createLabelArray(articles: List<Article>?): List<String> {
        val array = mutableListOf<String>()
        articles?.forEach {
            it.title?.let { it1 -> array.add(it1) }
        }
        return array
    }

    class NavigationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView = itemView.findViewById(R.id.nav_title)
        var expandView: ImageView = itemView.findViewById(R.id.nav_expand)
        var labelLayout: LabelLayout = itemView.findViewById(R.id.nav_label_layout)
    }
}