package com.naib.wandroid.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.naib.wandroid.main.home.data.Article
import com.naib.wandroid.main.home.data.Banner
import com.naib.wandroid.main.home.data.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val repository = HomeRepository()
    private var articlePage = 0
    private var projectPage = 0

    var banner: MutableLiveData<List<Banner>?> =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(repository.loadBanners())
        } as MutableLiveData<List<Banner>?>

    var articles: MutableLiveData<MutableList<Article>?> =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            val articles = repository.loadArticles(articlePage)
            articles?.apply {
                articlePage = this.curPage
                emit(this.datas)
            }
        } as MutableLiveData<MutableList<Article>?>

    suspend fun refreshArticles() {
        articlePage = 0
        repository.loadArticles(articlePage)?.apply {
            articlePage = curPage
            datas?.apply {
                articles.postValue(this)
            }
        }
    }

    fun loadMoreArticles() {
        viewModelScope.launch {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                repository.loadArticles(articlePage)
            }?.datas?.apply {
                val list = mutableListOf<Article>()
                articles.value?.let { list.addAll(it) }
                list.addAll(this)
                articles.value = list
            }
        }
    }

    var projects: MutableLiveData<MutableList<Article>?> =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            val articles = repository.loadProjects(projectPage)
            articles?.apply {
                projectPage = this.curPage
                emit(this.datas)
            }
        } as MutableLiveData<MutableList<Article>?>

    suspend fun refreshProjects() {
        projectPage = 0
        repository.loadProjects(projectPage)?.apply {
            projectPage = curPage
            datas?.apply {
                projects.postValue(this)
            }
        }
    }

    fun loadMoreProjects() {
        viewModelScope.launch {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                repository.loadProjects(projectPage)
            }?.datas?.apply {
                val list = mutableListOf<Article>()
                projects.value?.let { list.addAll(it) }
                list.addAll(this)
                projects.value = list
            }
        }
    }
}