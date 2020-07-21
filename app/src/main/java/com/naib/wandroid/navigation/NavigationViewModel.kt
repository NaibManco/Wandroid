package com.naib.wandroid.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *  Created by Naib on 2020/7/20
 */
class NavigationViewModel : ViewModel() {
    private val repository = NavigationRepository()

    var navigationList: MutableLiveData<MutableList<Navigation>?> =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(repository.getNav())
        } as MutableLiveData<MutableList<Navigation>?>

    fun refreshArticles() {
        viewModelScope.launch {
            repository.getNav()?.apply {
                val data = mutableListOf<Navigation>()
                data.addAll(this)
                navigationList.postValue(data)
            }
        }
    }
}