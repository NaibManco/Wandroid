package com.naib.wandroid.main.project

import com.naib.wandroid.base.network.Response
import com.naib.wandroid.main.architecture.data.Architecture
import com.naib.wandroid.global.Articles
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *  Created by Naib on 2020/7/10
 */
interface ProjectService {

    @GET("/project/tree/json")
    suspend fun loadCategory(): Response<List<Architecture>>

}