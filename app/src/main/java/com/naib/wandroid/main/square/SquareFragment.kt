package com.naib.wandroid.main.square

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.naib.wandroid.R
import com.naib.wandroid.base.widget.WanRecyclerView
import com.naib.wandroid.base.widget.WanRefreshLayout
import com.naib.wandroid.main.article.ArticleAdapter
import com.naib.wandroid.main.BaseArticleFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *  Created by Naib on 2020/7/10
 */
class SquareFragment : BaseArticleFragment(), SwipeRefreshLayout.OnRefreshListener,
    WanRecyclerView.OnLoadMoreListener {
    private var viewModel = viewModels<SquareViewModel>()

    private lateinit var refreshLayout: WanRefreshLayout
    private lateinit var recyclerView: WanRecyclerView
    private lateinit var adapter: ArticleAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_square
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout = view.findViewById(R.id.square_refresh)
        refreshLayout.setOnRefreshListener(this)
        recyclerView = view.findViewById(R.id.square_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.loadMoreListener = this
        adapter = ArticleAdapter()
        adapter.onItemClickListener = this
        adapter.onLikeClickListener = this
        recyclerView.adapter = adapter

        viewModel.value.articles.observe(viewLifecycleOwner) {
            adapter.update(it)
            if (refreshLayout.isRefreshing) {
                refreshLayout.isRefreshing = false
                recyclerView.smoothScrollToPosition(0)
            }
            finishLoading()
        }
    }

    override fun onRefresh() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                viewModel.value.refreshArticles()
            }
        }
    }

    override fun onLoadMore(recyclerView: WanRecyclerView) {
        viewModel.value.loadMoreArticles()
    }
}