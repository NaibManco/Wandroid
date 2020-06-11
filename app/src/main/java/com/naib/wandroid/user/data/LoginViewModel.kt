package com.naib.wandroid.user.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    private val repository = UserRepository()

    suspend fun doLogin(userName: String, password: String): UserInfo? {
        return withContext(viewModelScope.coroutineContext) {
            repository.userLogin(userName, password)
        }
    }

    suspend fun doRegister(userName: String, password: String): UserInfo? {
        return withContext(viewModelScope.coroutineContext) {
            repository.userRegister(userName, password)
        }
    }
}