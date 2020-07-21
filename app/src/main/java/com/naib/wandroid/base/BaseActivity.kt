package com.naib.wandroid.base

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.naib.wandroid.R
import com.naib.wandroid.navigation.NavigationActivity
import com.naib.wandroid.search.SearchActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity : AppCompatActivity() {

    lateinit var rootView: ViewGroup
    lateinit var contentContainer: FrameLayout
    lateinit var toolbar: Toolbar
    lateinit var loadingView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView = layoutInflater.inflate(R.layout.layout_root, null) as ViewGroup
        setContentView(rootView)
        createToolbar()
        createContentView()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        onCreateToolbar(toolbar)
    }

    private fun createContentView() {
        contentContainer = findViewById(R.id.content_view)
        onCreateContentView(contentContainer)
    }

    abstract fun onCreateContentView(container: ViewGroup)

    private fun createToolbar() {
        toolbar = rootView.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    open fun onCreateToolbar(toolbar: Toolbar) {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                return true
            }
            R.id.toolbar_about -> {
            }
            R.id.toolbar_like -> {

            }
            R.id.toolbar_navigation -> {
                startActivity(Intent(this,NavigationActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val MENU_SEARCH = 0
        const val MENU_LIKE = 1
        const val MENU_NAVIGATION = 2
    }
}