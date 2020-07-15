package com.naib.wandroid.base

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import kotlinx.coroutines.MainScope

/**
 *  Created by Naib on 2020/6/15
 */
open class BaseFragment : Fragment() {
    val mainScope = MainScope()

    open fun onToolbarCreated(toolbar: Toolbar) {
    }
}