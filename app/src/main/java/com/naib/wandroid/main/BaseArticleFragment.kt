package com.naib.wandroid.main

import android.graphics.Color
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.naib.wandroid.base.BaseFragment
import com.naib.wandroid.base.WebViewActivity
import com.naib.wandroid.global.Article
import com.naib.wandroid.global.GlobalViewModel
import com.naib.wandroid.global.OnItemClickListener
import com.naib.wandroid.global.OnLikeClickListener
import kotlinx.coroutines.launch

/**
 *  Created by Naib on 2020/7/13
 */
open class BaseArticleFragment : BaseFragment(), OnItemClickListener<Article>,
    OnLikeClickListener<Article> {
    protected var globalViewModel = viewModels<GlobalViewModel>()

    override fun onItemClick(article: Article, position: Int) {
        WebViewActivity.launch(article.link!!, article.title!!, article.id, article.collect)
    }

    override fun onFavoriteClick(view: ImageView, article: Article, position: Int) {
        mainScope.launch {
            val success = globalViewModel.value.collectArticle(article.id)
            if (success) {
                view.setColorFilter(Color.RED)
                article.collect = true
            }
        }
    }
}