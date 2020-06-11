package com.naib.wandroid.main.home.data

import com.naib.wandroid.base.network.HttpClient
import com.naib.wandroid.base.utils.LogUtil
import java.lang.Exception

/**
 *  Created by wanglongfei on 2020/5/19
 */
class HomeRepository {

    suspend fun loadBanners(): List<Banner>? {
        return try {
            val response =
                HttpClient.createService(BannerService::class.java).banner().execute().body()
            if (response?.data == null) {
                LogUtil.e(" request banner, errorMsg = " + response?.errorMsg)
                return null
            }

            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
}