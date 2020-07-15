package com.naib.wandroid.main.home.data

import com.naib.wandroid.base.network.HttpClient
import com.naib.wandroid.base.utils.LogUtil
import com.naib.wandroid.global.Articles
import java.lang.Exception

/**
 *  Created by wanglongfei on 2020/5/19
 */
class HomeRepository {
    private val homeService = HttpClient.createService(HomeService::class.java)

    suspend fun loadBanners(): List<Banner>? {
        return try {
            val response = homeService.banner().execute().body()
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

    suspend fun loadProjects(page: Int): Articles? {
        return try {
            val response = homeService.projects(page).execute().body()
            if (response?.data == null) {
                LogUtil.e(" request projects, errorMsg = " + response?.errorMsg)
                return null
            }

            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}