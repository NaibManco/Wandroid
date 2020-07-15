package com.naib.wandroid.global

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *  Created by Naib on 2020/7/13
 */
class GlobalViewModel : ViewModel() {
    private val globalRepository: GlobalRepository = GlobalRepository()

    suspend fun collectArticle(id: Int): Boolean {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            globalRepository.collectArticle(id)
        }
    }

    suspend fun unCollectArticle(id: Int): Boolean {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            globalRepository.unCollectArticle(id)
        }
    }
}