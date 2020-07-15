package com.naib.wandroid.base

import android.widget.Toast
import com.google.gson.Gson
import com.naib.wandroid.WanApplication
import com.naib.wandroid.user.LoginActivity
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody

/**
 *  Created by Naib on 2020/6/29
 */
class LoginInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        return if (response.body() != null && response.body()!!.contentType() != null) {
            val mediaType: MediaType? = response.body()!!.contentType()
            val string = response.body()!!.string()
            val rawResponse =
                Gson().fromJson(string, com.naib.wandroid.base.network.Response::class.java)
            if (rawResponse.errorCode == -1001) {
                LoginActivity.start(WanApplication.instance!!.topActivity)
            }
            val responseBody = ResponseBody.create(mediaType, string)
            response.newBuilder().body(responseBody).build()
        } else {
            response
        }
    }
}