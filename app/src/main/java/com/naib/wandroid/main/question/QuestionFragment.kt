package com.naib.wandroid.main.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseFragment
import com.naib.wandroid.base.widget.WanRecyclerView
import com.naib.wandroid.base.widget.WanRefreshLayout
import com.naib.wandroid.global.Article
import com.naib.wandroid.global.ArticleAdapter
import com.naib.wandroid.global.OnItemClickListener
import com.naib.wandroid.main.BaseArticleFragment
import com.naib.wandroid.main.project.ProjectViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *  Created by Naib on 2020/7/10
 */
class QuestionFragment : BaseArticleFragment(), SwipeRefreshLayout.OnRefreshListener,
    WanRecyclerView.OnLoadMoreListener {
    private var viewModel = viewModels<QuestionViewModel>()

    private lateinit var refreshLayout: WanRefreshLayout
    private lateinit var recyclerView: WanRecyclerView
    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout = view.findViewById(R.id.question_refresh)
        refreshLayout.setOnRefreshListener(this)
        recyclerView = view.findViewById(R.id.question_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.loadMoreListener = this
        adapter = ArticleAdapter()
        adapter.onItemClickListener = this
        adapter.onLikeClickListener = this
        recyclerView.adapter = adapter

        viewModel.value.articles.observe(viewLifecycleOwner) {
            adapter.update(it)
            recyclerView.smoothScrollToPosition(0)
        }
    }

    override fun onRefresh() {
        mainScope.launch {
            withContext(mainScope.coroutineContext + Dispatchers.IO) {
                viewModel.value.refreshArticles()
            }
            refreshLayout.isRefreshing = false
        }
    }

    override fun onLoadMore(recyclerView: WanRecyclerView) {
        viewModel.value.loadMoreArticles()
    }
}