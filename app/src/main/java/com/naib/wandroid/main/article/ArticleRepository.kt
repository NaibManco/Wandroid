package com.naib.wandroid.main.article

import com.naib.wandroid.base.network.HttpClient
import com.naib.wandroid.base.utils.LogUtil
import java.lang.Exception

/**
 *  Created by Naib on 2020/7/13
 */
open class ArticleRepository {
    private val service = HttpClient.createService(ArticleService::class.java)

    suspend fun topArticles() : MutableList<Article>? {
        return try {
            val response = service.topArticles()
            if (response.data == null) {
                LogUtil.e(" request articles, errorMsg = " + response.errorMsg)
                return null
            }

            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun loadArticles(page: Int): Articles? {
        return try {
            val response = service.loadArticles("article", page, null)
            if (response.data == null) {
                LogUtil.e(" request articles, errorMsg = " + response.errorMsg)
                return null
            }

            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun loadArticles(module: String, page: Int): Articles? {
        return try {
            val response = service.loadArticles(module, page, null)
            if (response.data == null) {
                LogUtil.e(" request articles, errorMsg = " + response.errorMsg)
                return null
            }

            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun loadArticles(module: String, page: Int, queryMap: Map<String, String>?): Articles? {
        return try {
            val response = service.loadArticles(module, page, queryMap)
            if (response.data == null) {
                LogUtil.e(" request articles, errorMsg = " + response.errorMsg)
                return null
            }

            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}