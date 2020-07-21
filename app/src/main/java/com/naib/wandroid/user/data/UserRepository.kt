package com.naib.wandroid.user.data

import android.text.TextUtils
import com.naib.wandroid.R
import com.naib.wandroid.WanApplication
import com.naib.wandroid.base.network.HttpClient
import com.naib.wandroid.base.network.Response
import com.naib.wandroid.base.utils.LogUtil
import com.naib.wandroid.main.article.Articles
import java.lang.Exception

/**
 *  Created by wanglongfei on 2020/6/10
 */
class UserRepository {
    private val userService = HttpClient.createService(UserService::class.java)

    suspend fun userLogin(userName: String, password: String): Response<UserInfo>? {
        return try {
            val response = userService.login(userName, password)
            UserInfoManager.cacheUserInfo(response.data)
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun userRegister(userName: String, password: String): Response<UserInfo>? {
        return try {
            val response = userService.register(userName, password, password)
            UserInfoManager.cacheUserInfo(response.data)
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun userInfo(): Response<UserInfo>? {
        return try {
            val response = userService.userInfo()
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun loadCollectArticles(page: Int): Articles? {
        return try {
            val response = userService.collectedArticles(page)
            if (response?.data == null) {
                LogUtil.e(" loadCollectArticles, errorMsg = " + response?.errorMsg)
                return null
            }

            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun unCollect(id: Long, originId: Long): String {
        return try {
            val response = userService.unCollectArticle(id.toString(), originId.toString())
            if (response.errorCode != 0) {
                if (TextUtils.isEmpty(response.errorMsg)) {
                    return WanApplication.instance!!.resources.getString(R.string.common_error)
                }
                return response.errorMsg
            }
            return ""
        } catch (e: Exception) {
            e.printStackTrace()
            return e.message.toString()
        }
    }
}