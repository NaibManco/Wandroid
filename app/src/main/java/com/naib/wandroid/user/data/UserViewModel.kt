package com.naib.wandroid.user.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.naib.wandroid.base.network.Response
import com.naib.wandroid.global.Article
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
}