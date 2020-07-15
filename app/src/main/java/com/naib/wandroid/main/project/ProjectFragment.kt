package com.naib.wandroid.main.project

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseFragment
import com.naib.wandroid.base.WebViewActivity
import com.naib.wandroid.base.widget.LabelLayout
import com.naib.wandroid.base.widget.WanRecyclerView
import com.naib.wandroid.base.widget.WanRefreshLayout
import com.naib.wandroid.main.architecture.data.Architecture
import com.naib.wandroid.global.Article
import com.naib.wandroid.global.OnItemClickListener
import com.naib.wandroid.main.BaseArticleFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectFragment : BaseArticleFragment(), SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener, WanRecyclerView.OnLoadMoreListener {

    private var projectViewModel = viewModels<ProjectViewModel>()

    private lateinit var refreshLayout: WanRefreshLayout
    private lateinit var recyclerView: WanRecyclerView
    private lateinit var adapter: ProjectAdapter

    private lateinit var categoryView: TextView
    private var currentArchitecture: Architecture? = null

    private var categoryMenu: PopupWindow? = null
    private var categories = mutableListOf<Architecture>()
    private lateinit var categoryLayout: LabelLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout = view.findViewById(R.id.project_refresh)
        refreshLayout.setOnRefreshListener(this)
        recyclerView = view.findViewById(R.id.project_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ProjectAdapter()
        adapter.onItemClickListener = this
        recyclerView.adapter = adapter
        recyclerView.loadMoreListener = this
        categoryView = view.findViewById(R.id.project_category)
        val categoryContainer = view.findViewById<View>(R.id.project_category_container)
        categoryContainer.setOnClickListener(this)

        projectViewModel.value.articles.observe(viewLifecycleOwner) {
            adapter.update(it)
            recyclerView.smoothScrollToPosition(0)
        }

        LayoutInflater.from(requireContext())
            .inflate(R.layout.layout_category_label, null).apply {
                categoryLayout = this.findViewById(R.id.category_label_layout)
                categoryLayout.apply {
                    this.setOnItemClickListener { i: Int, s: String ->
                        val architecture = categories?.get(i)
                        categoryView.text = architecture?.name
                        loadProjects(architecture?.id)
                        categoryMenu?.dismiss()
                        currentArchitecture = architecture
                    }
                }
                categoryMenu = PopupWindow(
                    this, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                categoryMenu?.isOutsideTouchable = true
            }

        projectViewModel.value.architecture.observe(viewLifecycleOwner) {
            it?.apply {
                categories.clear()
                categories.addAll(this)
                val labels = mutableListOf<String>()
                categories.forEach {
                    labels.add(it.name)
                    categoryLayout.setAdapter(LabelLayout.Adapter(labels))
                }
                if (currentArchitecture == null) {
                    currentArchitecture = categories[0]
                    categoryView.text = currentArchitecture?.name
                    loadProjects(currentArchitecture?.id)
                }
            }
        }
    }

    private fun loadProjects(cid: Int?) {
        mainScope.launch {
            cid?.let { projectViewModel.value.refreshProjects(it) }
        }
    }

    override fun onRefresh() {
        mainScope.launch {
            withContext(mainScope.coroutineContext + Dispatchers.IO) {
                currentArchitecture?.apply {
                    projectViewModel.value.refreshProjects(this.id)
                }
            }
            refreshLayout.isRefreshing = false
        }
    }

    override fun onClick(v: View?) {
        categoryMenu?.apply {
            showAsDropDown(v)
        }
    }

    override fun onLoadMore(recyclerView: WanRecyclerView) {
        currentArchitecture?.apply {
            projectViewModel.value.loadMoreProjects(this.id)
        }
    }
}
