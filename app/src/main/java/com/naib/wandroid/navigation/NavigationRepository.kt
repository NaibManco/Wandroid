package com.naib.wandroid.navigation

import com.naib.wandroid.base.network.HttpClient
import com.naib.wandroid.base.utils.LogUtil
import java.lang.Exception

/**
 *  Created by Naib on 2020/7/20
 */
class NavigationRepository {
    private val service = HttpClient.createService(NavigationService::class.java)

    suspend fun getNav(): List<Navigation>? {
        return try {
            val response = service.getNav()
            if (response.data == null) {
                LogUtil.e(" request navigation, errorMsg = " + response.errorMsg)
                return null
            }
            response.data
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}