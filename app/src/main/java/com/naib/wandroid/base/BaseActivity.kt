package com.naib.wandroid.base

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.naib.wandroid.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity : AppCompatActivity() {
    val mainScope = MainScope()

    lateinit var rootView: ViewGroup
    lateinit var contentContainer: FrameLayout
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView = layoutInflater.inflate(R.layout.layout_root, null) as ViewGroup
        setContentView(rootView)
        createToolbar()
        createContentView()
    }

    private fun createContentView() {
        contentContainer = findViewById(R.id.content_view)
        onCreateContentView(contentContainer)
    }

    abstract fun onCreateContentView(container: ViewGroup)

    private fun createToolbar() {
        toolbar = rootView.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        onCreateToolbar(toolbar)
    }

    open fun onCreateToolbar(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}