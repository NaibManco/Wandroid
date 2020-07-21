package com.naib.wandroid.user.data

/**
 *  Created by wanglongfei on 2020/6/10
 */
class UserInfo {
    /**
     * collectIds : [2680,2700,1367,1352]
     * email : 1137366723@qq.com
     * icon :
     * id : 2145
     * password : 123321
     * type : 0
     * username : axeChen
     */
    var email: String? = null
    var icon: String? = null
    var id: Int = 0
    var password: String? = null
    var type: Int = 0
    var username: String? = null
    var nickname: String? = null

    /**积分**/
    var coinCount: Int = 0

    /**等级**/
    var level: Int = 0

    /**排名**/
    var rank: String? = null

    var userId: Long = 0
}