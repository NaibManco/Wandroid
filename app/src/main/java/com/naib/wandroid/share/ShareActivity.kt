package com.naib.wandroid.share

import android.view.ViewGroup
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseActivity

/**
 *  Created by Naib on 2020/7/14
 */
class ShareActivity : BaseActivity() {

    override fun onCreateContentView(container: ViewGroup) {
        layoutInflater.inflate(R.layout.activity_share,container)
    }
}