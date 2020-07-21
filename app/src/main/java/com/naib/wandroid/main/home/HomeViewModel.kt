package com.naib.wandroid.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.naib.wandroid.global.GlobalRepository
import com.naib.wandroid.main.article.Article
import com.naib.wandroid.main.article.ArticleViewModel
import com.naib.wandroid.main.home.data.Banner
import com.naib.wandroid.main.home.data.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ArticleViewModel("article") {
    private val repository = HomeRepository()
    private var projectPage = 0

    var banner: MutableLiveData<List<Banner>?> =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(repository.loadBanners())
        } as MutableLiveData<List<Banner>?>

    override var articles: MutableLiveData<MutableList<Article>?> = MutableLiveData()

    override suspend fun refreshArticles() {
        articlePage = 0
        val topArticles = repository.topArticles()
        val commonArticles = repository.loadArticles(module, articlePage, queries)
        val finalArticles = mutableListOf<Article>()
        topArticles?.apply {
            forEach {
                it.top = true
            }
            finalArticles.addAll(this)
        }
        commonArticles?.apply {
            articlePage = curPage
            datas?.apply {
                finalArticles.addAll(this)
            }
        }
        articles.postValue(finalArticles)
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

    private val globalRepository: GlobalRepository = GlobalRepository()

    suspend fun collectArticle(id: Long): Boolean {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            globalRepository.collectArticle(id)
        }
    }
}