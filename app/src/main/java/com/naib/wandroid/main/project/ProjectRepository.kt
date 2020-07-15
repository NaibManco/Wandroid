package com.naib.wandroid.main.project

import com.naib.wandroid.base.network.HttpClient
import com.naib.wandroid.main.architecture.data.Architecture
import com.naib.wandroid.global.Articles
import java.lang.Exception

/**
 *  Created by Naib on 2020/7/9
 */
class ProjectRepository {
    private val projectService = HttpClient.createService(ProjectService::class.java)

    suspend fun loadArchArchitecture(): List<Architecture>? {
        return try {
            val response = projectService.loadCategory()
            response.data
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}