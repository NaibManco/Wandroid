package com.naib.wandroid.base.utils

import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
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

fun Any?.ifNull(block: () -> Unit) {
    if (this == null) block()
}

fun <E> Collection<E>?.notEmpty(block: () -> Unit) {
    if (this != null && !this.isEmpty()) block()
}

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(values: List<T>) {
    val value = this.value ?: arrayListOf()
    value.addAll(values)
    this.value = value
}

fun Drawable.colorFilter(color: Int) {
    this.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}