package com.naib.wandroid.base.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.naib.wandroid.base.R
import kotlin.math.abs

/**
 * Created by Naib on 2020/6/15
 */
class WanRefreshLayout : SwipeRefreshLayout {
    private var startY = 0f
    private var startX = 0f

    private var isHorizontalDrag = false
    private var touchSlop = 0

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
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
        setColorSchemeResources(R.color.colorAccent)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                startY = ev.rawY
                startX = ev.rawX
                // 初始化标记
                isHorizontalDrag = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (isHorizontalDrag) {
                    return false
                }
                val gapX = abs(ev.rawX - startX)
                val gapY = abs(ev.rawY - startY)
                if (gapX > touchSlop && gapX > gapY) {
                    isHorizontalDrag = true
                    return false
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
                isHorizontalDrag = false
        }
        return super.onInterceptTouchEvent(ev)
    }
}