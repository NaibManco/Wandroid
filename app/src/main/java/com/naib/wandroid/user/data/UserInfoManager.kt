package com.naib.wandroid.user.data

import com.google.gson.Gson
import com.naib.wandroid.base.persistence.KV

/**
 *  Created by wanglongfei on 2020/6/10
 */
class UserInfoManager {

    companion object Instance {
        private const val USER_INFO = "user_info"
        private lateinit var cacheUserInfo: UserInfo

        fun getUserInfo(): UserInfo? {
            if (cacheUserInfo != null) {
                return cacheUserInfo
            }
            val userInfo = KV.get(USER_INFO)
            if (userInfo != null) {
                return Gson().fromJson(userInfo, UserInfo::class.java)
            }
            return null
        }

        fun cacheUserInfo(userInfo: UserInfo) {
            KV.put(USER_INFO, Gson().toJson(userInfo))
        }
    }
}