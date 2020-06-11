package com.naib.wandroid.base.network

import com.naib.wandroid.base.BuildConfig
import com.naib.wandroid.base.utils.SingletonHolder0
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpClient private constructor(){

    val retrofit : Retrofit by lazy<Retrofit> {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        val okHttpClient = OkHttpClient.Builder()
            .cookieJar(CookieStore())
            .connectTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    companion object : SingletonHolder0<HttpClient>(::HttpClient){
        const val HTTP_TIME_OUT = 30L
        const val BASE_URL = "https://www.wanandroid.com"

        fun <T> createService(service: Class<T>): T {
            return getInstance().retrofit.create(service)
        }
    }
}