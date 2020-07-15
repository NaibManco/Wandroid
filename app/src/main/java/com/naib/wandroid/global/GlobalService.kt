package com.naib.wandroid.global

import com.naib.wandroid.base.network.NoData
import com.naib.wandroid.base.network.Response
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

/**
 *  Created by Naib on 2020/6/24
 */
interface GlobalService {

    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(
        @Path("id") id: Int
    ): Response<NoData>

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(
        @Path("id") id: Int
    ): Response<NoData>
}