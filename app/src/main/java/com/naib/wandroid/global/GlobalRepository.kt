package com.naib.wandroid.global

import com.naib.wandroid.base.network.HttpClient
import com.naib.wandroid.base.utils.LogUtil
import java.lang.Exception

/**
 *  Created by Naib on 2020/7/13
 */
class GlobalRepository {
    private var globalService: GlobalService? = null

    suspend fun collectArticle(id: Long): Boolean {
        return try {
            if (globalService == null) {
                globalService = HttpClient.createService(GlobalService::class.java)
            }
            val response = globalService!!.collectArticle(id)
            if (response.errorCode != 0) {
                LogUtil.e(" request projects, errorMsg = " + response.errorMsg)
                return false
            }

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    suspend fun unCollectArticle(id: Long): Boolean {
        return try {
            if (globalService == null) {
                globalService = HttpClient.createService(GlobalService::class.java)
            }
            val response = globalService!!.unCollectArticle(id)
            if (response.errorCode != 0) {
                LogUtil.e(" request projects, errorMsg = " + response.errorMsg)
                return false
            }

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    suspend fun collectWebsite(title: String, author: String, link: String): Boolean {
        return try {
            if (globalService == null) {
                globalService = HttpClient.createService(GlobalService::class.java)
            }
            val response = globalService!!.collectWebsite(title, author, link)
            if (response.errorCode != 0) {
                LogUtil.e(" request projects, errorMsg = " + response.errorMsg)
                return false
            }

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}