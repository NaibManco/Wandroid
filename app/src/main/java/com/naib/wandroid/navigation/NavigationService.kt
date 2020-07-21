package com.naib.wandroid.navigation

import com.naib.wandroid.base.network.Response
import retrofit2.http.GET

/**
 *  Created by Naib on 2020/7/20
 */
interface NavigationService {

    @GET("/navi/json")
    suspend fun getNav(): Response<List<Navigation>>
}