package com.naib.wandroid.share

import com.naib.wandroid.base.network.NoData
import com.naib.wandroid.base.network.Response
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *  Created by Naib on 2020/7/16
 */
interface ShareService {

    @POST("/lg/user_article/add/json")
    suspend fun share(
        @Query("title") title: String,
        @Query("link") link: String
    ): Response<NoData>
}