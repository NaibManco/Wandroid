package com.naib.wandroid.navigation

import android.view.Menu
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseActivity
import com.naib.wandroid.base.widget.WanRecyclerView
import com.naib.wandroid.base.widget.WanRefreshLayout

/**
 *  Created by Naib on 2020/7/20
 */
class NavigationActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel = viewModels<NavigationViewModel>()

    private lateinit var refreshLayout: WanRefreshLayout
    private lateinit var recyclerView: WanRecyclerView
    private lateinit var adapter: NavigationAdapter

    override fun onCreateContentView(container: ViewGroup) {
        layoutInflater.inflate(R.layout.activity_navigation, container)
        refreshLayout = findViewById(R.id.nav_refresh)
        refreshLayout.setOnRefreshListener(this)
        recyclerView = findViewById(R.id.nav_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NavigationAdapter()
        recyclerView.adapter = adapter

        viewModel.value.navigationList.observe(this) {
            if (refreshLayout.isRefreshing) {
                refreshLayout.isRefreshing = false
            }
            adapter.update(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val result = super.onCreateOptionsMenu(menu)
        menu?.apply {
            getItem(MENU_NAVIGATION).isVisible = false
        }
        return result
    }

    override fun onRefresh() {
        viewModel.value.refreshArticles()
    }

    override fun onCreateToolbar(toolbar: Toolbar) {
        super.onCreateToolbar(toolbar)
        toolbar.title = getString(R.string.menu_navigation)
    }
}