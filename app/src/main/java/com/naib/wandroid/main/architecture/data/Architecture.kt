package com.naib.wandroid.main.architecture.data

/**
 *  Created by Naib on 2020/7/9
 */
class Architecture {
    var id: Int = 0
    var name: String = ""
    var order: Int = 0
    var courseId: Int = 0;
    var parentChapterId: Int = 0
    var visible: Int = 0
    var userControlSetTop = false
    var children : List<Architecture>? = null
}