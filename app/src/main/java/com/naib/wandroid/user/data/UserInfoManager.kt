package com.naib.wandroid.user.data

import com.google.gson.Gson
import com.naib.wandroid.base.persistence.KV
import java.lang.Exception

/**
 *  Created by wanglongfei on 2020/6/10
 */
class UserInfoManager {

    companion object Instance {
        private const val USER_INFO = "user_info"
        private var cacheUserInfo: UserInfo? = null

        @Synchronized fun getUserInfo(): UserInfo? {
            if (cacheUserInfo != null) {
                return cacheUserInfo
            }
            try {
                val userInfo = KV.get(USER_INFO)
                if (userInfo != null) {
                    cacheUserInfo = Gson().fromJson(userInfo, UserInfo::class.java)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return cacheUserInfo
        }

        @Synchronized fun cacheUserInfo(userInfo: UserInfo?) {
            userInfo?.apply {
                cacheUserInfo = userInfo
                KV.put(USER_INFO, Gson().toJson(userInfo))
            }
        }
    }
}