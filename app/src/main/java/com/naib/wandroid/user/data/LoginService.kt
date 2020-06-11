package com.naib.wandroid.user.data

import com.naib.wandroid.base.network.Response
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *  Created by wanglongfei on 2020/6/10
 */
interface LoginService {

    @POST("user/login")
    suspend fun login(
        @Query("username") userName: String,
        @Query("password") password: String
    ): Response<UserInfo>


    @POST("user/register")
    suspend fun register(
        @Query("username") userName: String,
        @Query("password") password: String,
        @Query("repassword") rePassword: String
    ): Response<UserInfo>
}