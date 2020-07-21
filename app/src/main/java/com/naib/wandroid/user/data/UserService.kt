package com.naib.wandroid.user.data

import com.naib.wandroid.base.network.NoData
import com.naib.wandroid.base.network.Response
import com.naib.wandroid.main.article.Articles
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *  Created by wanglongfei on 2020/6/10
 */
interface UserService {

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

    /**
     * 获取收藏的文章列表
     */
    @GET("/lg/collect/list/{page}/json")
    suspend fun collectedArticles(
        @Path("page") page: Int
    ): Response<Articles>

    @POST("lg/uncollect/{id}/json")
    suspend fun unCollectArticle(
        @Path("id") id: String,
        @Query("originId") originId: String
    ): Response<NoData>

    @GET("/lg/coin/userinfo/json")
    suspend fun userInfo(): Response<UserInfo>
}