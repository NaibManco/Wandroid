package com.naib.wandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.naib.wandroid.R

/**
 *  Created by Naib on 2020/7/16
 */
open class BaseLoadingFragment : BaseFragment() {
    private lateinit var loadingView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: FrameLayout =
            layoutInflater.inflate(R.layout.fragment_base_loading, container, false) as FrameLayout
        layoutInflater.inflate(getLayoutId(), view)
        loadingView = view.findViewById(R.id.loading_image)
        Glide.with(this).load(resources.getDrawable(R.drawable.ic_loading)).into(loadingView)
        return view
    }

    @LayoutRes
    open fun getLayoutId(): Int {
        return 0
    }

    fun finishLoading() {
        if (loadingView.visibility != View.GONE) {
            loadingView.visibility = View.GONE
        }
    }
}