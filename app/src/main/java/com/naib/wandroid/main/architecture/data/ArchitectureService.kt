package com.naib.wandroid.main.architecture.data

import com.naib.wandroid.base.network.Response
import com.naib.wandroid.global.Articles
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *  Created by Naib on 2020/7/9
 */
interface ArchitectureService {

    @GET("/tree/json")
    suspend fun loadArchitectures(): Response<List<Architecture>>

}