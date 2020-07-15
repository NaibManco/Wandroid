package com.naib.wandroid.base.network

class Response<T> {

    var data: T? = null

    var errorCode: Int = 0

    var errorMsg: String = ""

    constructor(data: T?, errorCode: Int, errorMsg: String) {
        this.data = data
        this.errorCode = errorCode
        this.errorMsg = errorMsg
    }

}