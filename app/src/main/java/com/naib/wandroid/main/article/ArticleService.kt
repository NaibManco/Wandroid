package com.naib.wandroid.main.article

import com.naib.wandroid.base.network.Response
import com.naib.wandroid.global.Articles
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 *  Created by Naib on 2020/7/13
 */
interface ArticleService {

    @GET("/{module}/list/{page}/json")
    suspend fun loadArticles(
        @Path("module") module: String,
        @Path("page") page: Int,
        @QueryMap queries: Map<String, String>?
    ): Response<Articles>
}