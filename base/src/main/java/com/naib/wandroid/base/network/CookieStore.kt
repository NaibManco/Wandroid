package com.naib.wandroid.base.network

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.naib.wandroid.base.persistence.KV
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.lang.Exception

class CookieStore : CookieJar {
    companion object {
        const val COOKIE_ID = "wan_cookie"
    }

    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        if (url == null || cookies.isNullOrEmpty()) return
        try {
            KV.put(
                COOKIE_ID,
                url.uri().toString(),
                cookiesToString(cookies)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        return try {
            stringToCookies(
                KV.get(
                    COOKIE_ID,
                    url.uri().toString()
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    private fun cookiesToString(cookies: MutableList<Cookie>): String {
        val properties: MutableList<CookieProperty> = mutableListOf()
        for (i in 0 until cookies.size) {
            val cookie = cookies[i]
            val cookieProperty =
                CookieProperty(
                    cookie.name(),
                    cookie.value()
                )
            cookieProperty.expiresAt = cookie.expiresAt()
            cookieProperty.domain = cookie.domain();
            cookieProperty.path = cookie.path()
            cookieProperty.secure = cookie.secure()
            cookieProperty.httpOnly = cookie.httpOnly()
            cookieProperty.persistent = cookie.persistent()
            cookieProperty.hostOnly = cookie.hostOnly()
            properties.add(cookieProperty)
        }

        return Gson().toJson(properties)
    }

    private fun stringToCookies(string: String?): MutableList<Cookie> {
        if (TextUtils.isEmpty(string)) {
            return mutableListOf()
        }
        val cookies: MutableList<Cookie> = mutableListOf()
        val array = JsonParser().parse(string).asJsonArray
        for (i in 0 until array.size()) {
            val cookieProperty = Gson().fromJson(array[i], CookieProperty::class.java)
            val cookieBuilder = Cookie.Builder()
            cookieBuilder.name(cookieProperty.name)
            cookieBuilder.value(cookieProperty.value)
            cookieBuilder.expiresAt(cookieProperty.expiresAt)
            if (cookieProperty.hostOnly) {
                cookieBuilder.hostOnlyDomain(cookieProperty.domain)
            } else {
                cookieBuilder.domain(cookieProperty.domain)
            }
            cookieBuilder.path(cookieProperty.path)
            if (cookieProperty.secure) cookieBuilder.secure()
            if (cookieProperty.httpOnly) cookieBuilder.httpOnly()
            cookies.add(cookieBuilder.build())
        }
        return cookies
    }

    data class CookieProperty(val name: String, val value: String) {
        var expiresAt: Long = 0
        var domain: String = ""
        var path: String = ""
        var secure: Boolean = false
        var httpOnly: Boolean = false
        var persistent: Boolean = false
        var hostOnly: Boolean = false
    }
}