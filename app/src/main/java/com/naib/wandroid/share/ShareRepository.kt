package com.naib.wandroid.share

import android.text.TextUtils
import com.naib.wandroid.R
import com.naib.wandroid.WanApplication
import com.naib.wandroid.base.network.HttpClient
import java.lang.Exception

/**
 *  Created by Naib on 2020/7/16
 */
class ShareRepository {
    private val service = HttpClient.createService(ShareService::class.java)

    suspend fun share(title: String, link: String): String {
        return try {
            val response = service.share(title, link)
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