package com.naib.wandroid.base.utils

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray

fun <T> List<T>.toJsonString(): String {
    if (isNullOrEmpty()) return ""
    val array = JSONArray()
    this.forEach { array.put(it.toString()) }
    return array.toString()
}

fun <T> subscribeOnNewObserveOnMain(): ObservableTransformer<T, T>? {
    return ObservableTransformer { observable ->
        observable.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun show(context: Context?, string: String?) {
    if (context == null || TextUtils.isEmpty(string)) {
        return
    }
    Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
}

fun show(context: Context?, stringId: Int) {
    if (context == null) {
        return
    }
    Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show()
}