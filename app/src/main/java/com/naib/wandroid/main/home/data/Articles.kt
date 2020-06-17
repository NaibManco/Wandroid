package com.naib.wandroid.main.home.data

/**
 *  Created by Naib on 2020/6/15
 */
class Articles {
    /**当前页码，从1开始**/
    var curPage: Int = 1
    /**文章列表**/
    var datas: MutableList<Article>? = null
    var offset: Int = 0
    var over: Boolean = false
    /**页码总数**/
    var pageCount: Int = 0
    /**当前页的文章数量**/
    var size: Int = 0
    var total: Int = 0
}