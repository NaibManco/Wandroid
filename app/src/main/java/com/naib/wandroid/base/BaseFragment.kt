package com.naib.wandroid.base

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import kotlinx.coroutines.MainScope

/**
 *  Created by Naib on 2020/6/15
 */
open class BaseFragment : Fragment() {

    open fun onToolbarCreated(toolbar: Toolbar) {
    }
}