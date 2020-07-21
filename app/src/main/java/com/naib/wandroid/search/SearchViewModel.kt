package com.naib.wandroid.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naib.wandroid.main.article.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *  Created by Naib on 2020/7/13
 */
class SearchViewModel : ViewModel() {
    private val repository = SearchRepository()

    suspend fun loadHotKey(): List<HotKey>? {
        return repository.hotKey()
    }

    var articles: MutableLiveData<MutableList<Article>?> = MutableLiveData()

    private var articlePage = 0

    suspend fun refreshArticles(words: String) {
        articlePage = 0
        repository.search(words, articlePage)?.apply {
            articlePage = curPage
            datas?.apply {
                articles.postValue(this)
            }
        }
    }

    fun loadMoreArticles(words: String) {
        viewModelScope.launch {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                repository.search(words, articlePage)?.apply {
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