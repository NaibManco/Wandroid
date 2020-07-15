package com.naib.wandroid.main.project

import androidx.lifecycle.*
import com.naib.wandroid.global.GlobalRepository
import com.naib.wandroid.main.architecture.data.Architecture
import com.naib.wandroid.global.Article
import com.naib.wandroid.main.article.ArticleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectViewModel : ArticleViewModel("project") {
    private val repository = ProjectRepository()

    var architecture: MutableLiveData<List<Architecture>?> =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(repository.loadArchArchitecture())
        } as MutableLiveData<List<Architecture>?>

    suspend fun refreshArchitecture() {
        repository.loadArchArchitecture()?.apply {
            architecture.postValue(this)
        }
    }

    override var articles: MutableLiveData<MutableList<Article>?> = MutableLiveData()

    private fun addQueries(id: Int) {
        val queries = mutableMapOf(
            "cid" to id.toString()
        )
        addArticleQuery(queries)
    }

    suspend fun refreshProjects(id: Int) {
        addQueries(id)
        refreshArticles()
    }

    fun loadMoreProjects(id: Int) {
        addQueries(id)
        loadMoreArticles()
    }

    private val globalRepository: GlobalRepository = GlobalRepository()

    suspend fun collectArticle(id: Int): Boolean {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            globalRepository.collectArticle(id)
        }
    }
}