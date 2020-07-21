package com.naib.wandroid.search

import com.naib.wandroid.base.network.HttpClient
import com.naib.wandroid.base.utils.LogUtil
import com.naib.wandroid.main.article.Articles
import java.lang.Exception

/**
 *  Created by Naib on 2020/7/13
 */
class SearchRepository {
    private val service = HttpClient.createService(SearchService::class.java)

    suspend fun search(words: String, page: Int): Articles? {
        return try {
            val response = service.search(page, words)
            if (response.data == null) {
                LogUtil.e(" request articles, errorMsg = " + response.errorMsg)
                return null
            }

            response.data
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun hotKey(): List<HotKey>? {
        return try {
            val response = service.hotKey()
            if (response.data == null) {
                LogUtil.e(" request articles, errorMsg = " + response.errorMsg)
                return null
            }
            response.data
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}