package com.naib.wandroid.main.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *  Created by Naib on 2020/7/13
 */
open class ArticleViewModel(module: String) : ViewModel() {
    private val repository = ArticleRepository()
    var articlePage = 0

    val module: String = module
    var queries: Map<String, String>? = mapOf()

    fun addArticleQuery(queryMap: Map<String, String>) {
        if (queryMap.isNullOrEmpty()) {
            return
        }
        queries = queryMap
    }

    open var articles: MutableLiveData<MutableList<Article>?> =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            val articles = repository.loadArticles(module, articlePage, queries)
            articles?.apply {
                articlePage = this.curPage + 1
                emit(this.datas)
            }
        } as MutableLiveData<MutableList<Article>?>

    open suspend fun refreshArticles() {
        articlePage = 0
        repository.loadArticles(module, articlePage, queries)?.apply {
            articlePage = curPage + 1
            datas?.apply {
                articles.postValue(this)
            }
        }
    }

    fun loadMoreArticles() {
        viewModelScope.launch {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                repository.loadArticles(module, articlePage, queries)?.apply {
                    articlePage = curPage + 1
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