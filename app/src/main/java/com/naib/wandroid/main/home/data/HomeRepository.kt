package com.naib.wandroid.main.home.data

import com.naib.wandroid.base.network.HttpClient
import com.naib.wandroid.base.utils.LogUtil
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

    suspend fun loadArticles(page: Int): Articles? {
        return try {
            val response = homeService.articles(page).execute().body()
            if (response?.data == null) {
                LogUtil.e(" request articles, errorMsg = " + response?.errorMsg)
                return null
            }

            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            return null
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