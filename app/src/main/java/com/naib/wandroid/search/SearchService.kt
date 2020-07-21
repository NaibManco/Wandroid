package com.naib.wandroid.search

import com.naib.wandroid.base.network.Response
import com.naib.wandroid.main.article.Articles
import retrofit2.http.*

/**
 *  Created by Naib on 2020/7/13
 */
interface SearchService {

    @POST("/article/query/{page}/json")
    suspend fun search(
        @Path("page") page: Int,
        @Query("k") keys: String
    ): Response<Articles>

    @GET("/hotkey/json")
    suspend fun hotKey(): Response<List<HotKey>>
}