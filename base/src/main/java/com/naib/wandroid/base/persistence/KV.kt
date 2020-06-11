package com.naib.wandroid.base.persistence

import android.content.Context
import com.tencent.mmkv.MMKV

object KV {
    private const val WAN_KV = "wan_kv"

    @JvmStatic
    fun initialize(context: Context): String = MMKV.initialize(context)

    fun put(id: String, key: String, value: String) = MMKV.mmkvWithID(id).encode(key, value)

    fun get(id: String, key: String): String? = MMKV.mmkvWithID(id).decodeString(key)

    fun put(key: String, value: String) = MMKV.mmkvWithID(WAN_KV).encode(key, value)

    fun get(key: String): String? = MMKV.mmkvWithID(WAN_KV).decodeString(key)
}