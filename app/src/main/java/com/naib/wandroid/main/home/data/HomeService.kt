package com.naib.wandroid.main.home.data

import com.naib.wandroid.base.network.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  Created by Naib on 2020/6/12
 */
interface HomeService {

    @GET("/banner/json")
    fun banner(): Call<Response<List<Banner>>>

    /**
     * 获取最新文章列表
     */
    @GET("/article/list/{page}/json")
    fun articles(
        @Path("page") page: Int
    ): Call<Response<Articles>>

    /**
     * 获取最热项目列表
     */
    @GET("/article/listproject/{page}/json")
    fun projects(
        @Path("page") page: Int
    ): Call<Response<Articles>>
}