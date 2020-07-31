package com.naib.wandroid.user.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.naib.wandroid.base.network.Response
import com.naib.wandroid.main.article.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    suspend fun doLogin(userName: String, password: String): Response<UserInfo>? {
        return withContext(viewModelScope.coroutineContext) {
            repository.userLogin(userName, password)
        }
    }

    suspend fun doRegister(userName: String, password: String): Response<UserInfo>? {
        return withContext(viewModelScope.coroutineContext) {
            repository.userRegister(userName, password)
        }
    }

    suspend fun getUserInfo(): UserInfo? {
        return withContext(viewModelScope.coroutineContext) {
            repository.userInfo()?.data
        }
    }

    private var collectArticlePage = 0

    var collectArticles: MutableLiveData<MutableList<Article>?> =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            val articles = repository.loadCollectArticles(collectArticlePage)
            articles?.apply {
                collectArticlePage = this.curPage
                emit(this.datas)
            }
        } as MutableLiveData<MutableList<Article>?>

    suspend fun refreshCollectArticles() {
        collectArticlePage = 0
        repository.loadCollectArticles(collectArticlePage)?.apply {
            collectArticlePage = curPage
            datas?.apply {
                collectArticles.postValue(this)
            }
        }
    }

    fun loadMoreCollectArticles() {
        viewModelScope.launch {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                repository.loadCollectArticles(collectArticlePage)
            }?.datas?.apply {
                val list = mutableListOf<Article>()
                collectArticles.value?.let { list.addAll(it) }
                list.addAll(this)
                collectArticles.value = list
            }
        }
    }

    suspend fun unCollect(id: Long, originId: Long): String {
        return withContext(viewModelScope.coroutineContext) {
            repository.unCollect(id, originId)
        }
    }

    private var sharedArticlePage = 1

    var sharedArticles: MutableLiveData<MutableList<Article>?> =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            val articles = repository.loadSharedArticles(sharedArticlePage)
            articles?.apply {
                collectArticlePage = this.curPage
                emit(this.datas)
            }
        } as MutableLiveData<MutableList<Article>?>

    suspend fun refreshSharedArticles() {
        sharedArticlePage = 1
        repository.loadSharedArticles(sharedArticlePage)?.apply {
            sharedArticlePage = curPage
            datas?.apply {
                sharedArticles.postValue(this)
            }
        }
    }

    fun loadMoreSharedArticles() {
        viewModelScope.launch {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                repository.loadSharedArticles(sharedArticlePage)
            }?.datas?.apply {
                val list = mutableListOf<Article>()
                sharedArticles.value?.let { list.addAll(it) }
                list.addAll(this)
                sharedArticles.value = list
            }
        }
    }

    suspend fun unShare(id: Long): String {
        return withContext(viewModelScope.coroutineContext) {
            repository.unShare(id)
        }
    }
}