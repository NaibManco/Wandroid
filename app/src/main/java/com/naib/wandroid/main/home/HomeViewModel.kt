package com.naib.wandroid.main.home

import androidx.lifecycle.*
import com.naib.wandroid.main.home.data.Banner
import com.naib.wandroid.main.home.data.HomeRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {
    private val repository = HomeRepository()

    var banner: LiveData<List<Banner>?> =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(repository.loadBanners())
        }
}