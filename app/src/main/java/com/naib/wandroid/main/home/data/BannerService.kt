package com.naib.wandroid.main.home.data

import com.naib.wandroid.base.network.Response
import retrofit2.Call
import retrofit2.http.GET

/**
 *  Created by wanglongfei on 2020/5/19
 */
interface BannerService {

    @GET("/banner/json")
    fun banner(): Call<Response<List<Banner>>>
}