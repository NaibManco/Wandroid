package com.naib.wandroid.user.data

import com.naib.wandroid.base.network.HttpClient
import com.naib.wandroid.base.utils.LogUtil
import org.json.JSONObject
import java.lang.Exception

/**
 *  Created by wanglongfei on 2020/6/10
 */
class UserRepository {

    suspend fun userLogin(userName: String, password: String): UserInfo? {
        return try {
            val response =
                HttpClient.createService(LoginService::class.java).login(userName, password)
            if (response.data == null) {
                LogUtil.e(" login failed, errorMsg = " + response.errorMsg)
                return null
            }
            UserInfoManager.cacheUserInfo(response.data!!)
            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun userRegister(userName: String, password: String): UserInfo? {
        return try {
            val response =
                HttpClient.createService(LoginService::class.java)
                    .register(userName, password, password)
            if (response.data == null) {
                LogUtil.e(" register failed, errorMsg = " + response.errorMsg)
                return null
            }
            UserInfoManager.cacheUserInfo(response.data!!)
            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}