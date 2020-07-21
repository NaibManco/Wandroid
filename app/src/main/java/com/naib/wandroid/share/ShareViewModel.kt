package com.naib.wandroid.share

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.withContext

/**
 *  Created by Naib on 2020/7/16
 */
class ShareViewModel : ViewModel() {
    private val repository = ShareRepository()

    suspend fun share(title: String, link: String): String {
        return withContext(viewModelScope.coroutineContext) {
            repository.share(title, link)
        }
    }
}