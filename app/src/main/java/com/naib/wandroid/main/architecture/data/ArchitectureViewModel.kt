package com.naib.wandroid.main.architecture.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.naib.wandroid.global.GlobalRepository
import com.naib.wandroid.main.article.Article
import com.naib.wandroid.main.article.ArticleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArchitectureViewModel : ArticleViewModel("article") {

    private val repository =
        ArchitectureRepository()

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

    suspend fun refreshArticles(id: Int) {
        addQueries(id)
        refreshArticles()
    }

    fun loadMoreArticles(id: Int) {
        addQueries(id)
        loadMoreArticles()
    }

    private val globalRepository: GlobalRepository = GlobalRepository()

    suspend fun collectArticle(id: Long): Boolean {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            globalRepository.collectArticle(id)
        }
    }
}