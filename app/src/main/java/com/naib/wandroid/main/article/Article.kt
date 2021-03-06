package com.naib.wandroid.main.article

/**
 *  Created by Naib on 2020/6/11
 */
class Article {
    var apkLink: String? = null

    /**原创作者**/
    var author: String? = null

    /**如果是分享的文章，则该字段代表分享人，author为空**/
    var shareUser: String? = null
    var chapterId: Int = 0

    /**二级章节分类的名称**/
    var chapterName: String? = null

    /**是否已收藏**/
    var collect: Boolean = false
    var courseId: Int = 0

    /**文章描述**/
    var desc: String? = null

    /**封面图**/
    var envelopePic: String? = null

    /**是否最新文章**/
    var fresh: Boolean = false
    var id: Long = 0

    /**文章地址**/
    var link: String? = ""

    /**格式化的时间**/
    var niceDate: String? = null
    var origin: String? = null

    /**项目链接**/
    var projectLink: String? = null
    var publishTime: Long = 0
    var superChapterId: Int = 0

    /**一级章节分类的名称**/
    var superChapterName: String? = null

    /**标题**/
    var title: String? = ""
    var type: Int = 0
    var visible: Int = 0
    var zan: Int = 0
    var tags: List<Tag>? = null
    /**originId 代表的是你收藏之前的那篇文章本身的id； 但是收藏支持主动添加，这种情况下，没有originId则为-1**/
    var originId: Long = -1

    /**是否置顶**/
    var top: Boolean = false

    class Tag {
        var name: String? = null
        var url: String? = null
    }
}