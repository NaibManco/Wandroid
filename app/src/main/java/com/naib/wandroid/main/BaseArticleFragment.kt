package com.naib.wandroid.main

import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseLoadingFragment
import com.naib.wandroid.base.WebViewActivity
import com.naib.wandroid.main.article.Article
import com.naib.wandroid.global.GlobalViewModel
import com.naib.wandroid.global.OnItemClickListener
import com.naib.wandroid.global.OnLikeClickListener
import kotlinx.coroutines.launch

/**
 *  Created by Naib on 2020/7/13
 */
open class BaseArticleFragment : BaseLoadingFragment(), OnItemClickListener<Article>,
    OnLikeClickListener<Article> {
    protected var globalViewModel = viewModels<GlobalViewModel>()

    override fun onItemClick(article: Article, position: Int) {
        WebViewActivity.launch(article.link!!, article.title!!, article.id, article.collect)
    }

    override fun onFavoriteClick(view: ImageView, article: Article, position: Int) {
        lifecycleScope.launch {
            if (article.collect) {
                val success = globalViewModel.value.unCollectArticle(article.id)
                if (success) {
                    view.colorFilter = null
                    article.collect = false
                }
            } else {
                val success = globalViewModel.value.collectArticle(article.id)
                if (success) {
                    view.setColorFilter(resources.getColor(R.color.color_like))
                    article.collect = true
                }
            }
        }
    }
}