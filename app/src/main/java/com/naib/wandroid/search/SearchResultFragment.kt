package com.naib.wandroid.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.naib.wandroid.R
import com.naib.wandroid.base.widget.WanRecyclerView
import com.naib.wandroid.base.widget.WanRefreshLayout
import com.naib.wandroid.global.ArticleAdapter
import com.naib.wandroid.main.BaseArticleFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *  Created by Naib on 2020/7/13
 */
class SearchResultFragment : BaseArticleFragment(), SwipeRefreshLayout.OnRefreshListener,
    WanRecyclerView.OnLoadMoreListener {
    private var viewModel = viewModels<SearchViewModel>()

    private lateinit var refreshLayout: WanRefreshLayout
    private lateinit var recyclerView: WanRecyclerView
    private lateinit var adapter: ArticleAdapter
    private lateinit var viewNotFound: View

    private var words: String? = null

    companion object {
        const val KEY_WORDS = "key_search_words"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        words = arguments?.getString(KEY_WORDS, "")
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewNotFound = view.findViewById(R.id.search_not_found)
        refreshLayout = view.findViewById(R.id.search_result_refresh)
        refreshLayout.setOnRefreshListener(this)
        recyclerView = view.findViewById(R.id.search_result_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.loadMoreListener = this
        adapter = ArticleAdapter()
        adapter.onItemClickListener = this
        adapter.onLikeClickListener = this
        recyclerView.adapter = adapter

        viewModel.value.articles.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                viewNotFound.visibility = View.VISIBLE
            } else {
                viewNotFound.visibility = View.GONE
            }
            adapter.update(it)
            recyclerView.smoothScrollToPosition(0)
        }
        onRefresh()
    }

    fun search(words:String) {
        this.words = words
        refreshLayout.isRefreshing = true
        onRefresh()
    }

    override fun onRefresh() {
        mainScope.launch {
            withContext(mainScope.coroutineContext + Dispatchers.IO) {
                words?.let { viewModel.value.refreshArticles(it) }
            }
            refreshLayout.isRefreshing = false
        }
    }

    override fun onLoadMore(recyclerView: WanRecyclerView) {
        words?.let { viewModel.value.loadMoreArticles(it) }
    }
}