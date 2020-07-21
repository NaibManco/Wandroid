package com.naib.wandroid.main.question

import com.naib.wandroid.base.network.Response
import com.naib.wandroid.main.article.Articles
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  Created by Naib on 2020/7/13
 */
interface QuestionService {
    /**
     * 获取最新文章列表
     */
    @GET("/wenda/list/{page}/json")
    fun articles(
        @Path("page") page: Int
    ): Response<Articles>
}