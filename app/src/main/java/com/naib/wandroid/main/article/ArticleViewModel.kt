package com.naib.wandroid.main.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.naib.wandroid.global.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.QueryMap

/**
 *  Created by Naib on 2020/7/13
 */
open class ArticleViewModel(module: String) : ViewModel() {
    private val articleRepository = ArticleRepository()
    private var articlePage = 0

    private val module: String = module
    private var queries: Map<String, String>? = mapOf()

    fun addArticleQuery(queryMap: Map<String, String>) {
        if (queryMap.isNullOrEmpty()) {
            return
        }
        queries = queryMap
    }

    open var articles: MutableLiveData<MutableList<Article>?> =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            val articles = articleRepository.loadArticles(module, articlePage, queries)
            articles?.apply {
                articlePage = this.curPage
                emit(this.datas)
            }
        } as MutableLiveData<MutableList<Article>?>

    suspend fun refreshArticles() {
        articlePage = 0
        articleRepository.loadArticles(module, articlePage, queries)?.apply {
            articlePage = curPage
            datas?.apply {
                articles.postValue(this)
            }
        }
    }

    fun loadMoreArticles() {
        viewModelScope.launch {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                articleRepository.loadArticles(module, articlePage, queries)?.apply {
                    articlePage = curPage
                }
            }?.datas?.apply {
                val list = mutableListOf<Article>()
                articles.value?.let { list.addAll(it) }
                list.addAll(this)
                articles.value = list
            }
        }
    }
}