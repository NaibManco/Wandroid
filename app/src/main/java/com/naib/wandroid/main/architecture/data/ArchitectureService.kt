package com.naib.wandroid.main.architecture.data

import com.naib.wandroid.base.network.Response
import retrofit2.http.GET

/**
 *  Created by Naib on 2020/7/9
 */
interface ArchitectureService {

    @GET("/tree/json")
    suspend fun loadArchitectures(): Response<List<Architecture>>

}