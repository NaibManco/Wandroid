package com.naib.wandroid.main.home.data

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

/**
 *  Created by Naib on 2020/6/15
 */
class HomePageAdapter() : PagerAdapter() {
    private var views: MutableList<View> = mutableListOf()
    var titles: List<String> = listOf()
        set(value) {
            field = value
        }

    constructor(Views: MutableList<View>) : this() {
        this.views = views
    }

    fun add(view: View) {
        views.add(view)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return views.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(views[position])
        return views[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(views[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}