package com.naib.wandroid.base

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Created by Naib on 2020/6/15
 */
class WanRefreshLayout : SwipeRefreshLayout {
    private var startY = 0f
    private var startX = 0f

    // 判断viewPager是否在正在拖拽
    private var isviewPagerDragger = false
    private var mTouchSlop = 0

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        //滑动最小距离
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                startY = ev.rawY
                startX = ev.rawX
                // 初始化标记
                isviewPagerDragger = false
            }
            MotionEvent.ACTION_MOVE -> {
                // 如果正在拖拽，直接返回flase，让viewPager处理
                if (isviewPagerDragger) {
                    return false
                }
                val gapX = Math.abs(ev.rawX - startX)
                val gapY = Math.abs(ev.rawY - startY)
                // 如果是滑动并且是横向滑动，返回flase让viewPager处理
                if (gapX > mTouchSlop && gapX > gapY) {
                    isviewPagerDragger = true
                    return false
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->                 // 标记复位
                isviewPagerDragger = false
        }
        return super.onInterceptTouchEvent(ev)
    }
}