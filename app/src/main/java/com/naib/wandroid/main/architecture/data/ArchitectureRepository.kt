package com.naib.wandroid.main.architecture.data

import com.naib.wandroid.base.network.HttpClient
import java.lang.Exception

/**
 *  Created by Naib on 2020/7/9
 */
class ArchitectureRepository {
    private val architectureService = HttpClient.createService(ArchitectureService::class.java)

    suspend fun loadArchArchitecture(): List<Architecture>? {
        return try {
            val response = architectureService.loadArchitectures()
            response.data
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}