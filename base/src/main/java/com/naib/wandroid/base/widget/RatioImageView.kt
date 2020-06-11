package com.naib.wandroid.base.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.naib.wandroid.base.R

/**
 *  Created by wanglongfei on 2020/6/9
 */
class RatioImageView : AppCompatImageView {
    var ratio: Float = 0f

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        if (attrs != null) {
            val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.RatioImageView)
            typedArray?.let {
                ratio = it.getFloat(R.styleable.RatioImageView_ratio, 0f)
                it.recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var newHeightSpec = heightMeasureSpec
        if (!ratio.equals(0f)) {
            val height: Float = width / ratio
            newHeightSpec = MeasureSpec.makeMeasureSpec(height.toInt(), MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, newHeightSpec)
    }
}