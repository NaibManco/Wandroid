package com.naib.wandroid.base.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * 左右2个view的布局。
 * 右侧view，宽度wrap_content，剩余宽度全部给左侧view。
 *
 * Created by Naib on 2020/5/29
 */
class SideLinearLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount != 2) {
            return
        }
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        if (widthMode == MeasureSpec.EXACTLY) {
            measureChildren(widthMeasureSpec, heightMeasureSpec)
            val child = getChildAt(0)
            val params = param(child)
            setMeasuredDimension(
                MeasureSpec.getSize(widthMeasureSpec),
                paddingTop + child.measuredHeight +
                        params.topMargin + params.bottomMargin + paddingBottom
            )
        }
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        val childCount = childCount
        if (childCount != 2) {
            return
        }
        val left = paddingLeft
        val top = paddingTop
        val right = measuredWidth - paddingRight
        val bottom = measuredHeight - paddingBottom
        val secondChild = getChildAt(1)
        var params = param(secondChild)
        val measureWidth = secondChild.measuredWidth
        val secondLeft = right - params.rightMargin - measureWidth
        secondChild.layout(
            secondLeft,
            top + params.topMargin,
            right - params.rightMargin,
            bottom - params.bottomMargin
        )
        val firstChild = getChildAt(0)
        params = param(firstChild)
        firstChild.layout(
            left + params.leftMargin,
            top + params.topMargin,
            secondLeft - params.rightMargin,
            bottom - params.bottomMargin
        )
    }

    private fun param(view: View): MarginLayoutParams {
        val params = view.layoutParams
            ?: return MarginLayoutParams(view.measuredWidth, view.measuredHeight)
        return if (params is MarginLayoutParams) {
            params
        } else {
            MarginLayoutParams(params)
        }
    }
}