package com.naib.wandroid.main.architecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.naib.wandroid.R
import com.naib.wandroid.base.utils.notEmpty
import com.naib.wandroid.base.widget.LabelLayout
import com.naib.wandroid.base.widget.WanRecyclerView
import com.naib.wandroid.base.widget.WanRefreshLayout
import com.naib.wandroid.main.architecture.data.Architecture
import com.naib.wandroid.main.architecture.data.ArchitectureViewModel
import com.naib.wandroid.main.article.ArticleAdapter
import com.naib.wandroid.main.BaseArticleFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArchitectureFragment : BaseArticleFragment(), View.OnClickListener,
    WanRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private var architectureViewModel = viewModels<ArchitectureViewModel>()

    private lateinit var firstTextView: TextView
    private var firstClassMenu: PopupWindow? = null
    private var firstArchitecture = mutableListOf<Architecture>()
    private lateinit var firstLabelLayout: LabelLayout

    private lateinit var secondTextView: TextView
    private var secondClassMenu: PopupWindow? = null
    private var secondArchitecture: List<Architecture>? = null
    private lateinit var secondLabelLayout: LabelLayout

    private var architectureMap = mutableMapOf<Architecture, List<Architecture>?>()
    private var selectedArchitecture: Architecture? = null

    private lateinit var refreshLayout: WanRefreshLayout
    private lateinit var recyclerView: WanRecyclerView
    private var adapter: ArticleAdapter? = null

    private var halfDisplayHeight = 0

    override fun getLayoutId(): Int {
        return R.layout.fragment_architecture
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        halfDisplayHeight = resources.displayMetrics.heightPixels / 2
        firstTextView = view.findViewById(R.id.architecture_first_class)
        inflateLabelView().apply {
            firstLabelLayout = this.findViewById(R.id.category_label_layout)
            firstLabelLayout.apply {
                this.setOnItemClickListener { i: Int, s: String ->
                    applyMenu(i, false)
                    firstClassMenu?.dismiss()
                }
            }
            firstClassMenu = PopupWindow(
                this,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            firstClassMenu?.isOutsideTouchable = true
        }

        secondTextView = view.findViewById(R.id.architecture_second_class)
        inflateLabelView().apply {
            secondLabelLayout = this.findViewById(R.id.category_label_layout)
            secondLabelLayout.apply {
                this.setOnItemClickListener { i: Int, s: String ->
                    val architecture = secondArchitecture?.get(i)
                    secondTextView.text = architecture?.name
                    loadArticles(architecture?.id)
                    secondClassMenu?.dismiss()
                    selectedArchitecture = architecture
                }
            }
            secondClassMenu = PopupWindow(
                this, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            secondClassMenu?.isOutsideTouchable = true
        }

        recyclerView = view.findViewById(R.id.architecture_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.loadMoreListener = this
        adapter = ArticleAdapter()
        adapter?.onLikeClickListener = this
        adapter?.onItemClickListener = this
        recyclerView.adapter = adapter

        refreshLayout = view.findViewById(R.id.architecture_refresh)
        refreshLayout.setOnRefreshListener(this)

        architectureViewModel.value.architecture.observe(viewLifecycleOwner) {
            firstArchitecture.clear()
            it?.forEach { it ->
                firstArchitecture.add(it)
                val labels = mutableListOf<String>()
                firstArchitecture.forEach {
                    labels.add(it.name)
                    firstLabelLayout.setAdapter(LabelLayout.Adapter(labels))
                }
                architectureMap[it] = it.children
                firstTextView.setOnClickListener(this)
            }
            it?.notEmpty {
                applyMenu(0, true)
            }
        }

        architectureViewModel.value.articles.observe(viewLifecycleOwner) {
            refreshLayout.isRefreshing = false
            adapter?.update(it)
            recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun applyMenu(index: Int, loadArticle: Boolean) {
        val architecture = firstArchitecture[index]
        firstTextView.text = architecture.name
        secondArchitecture = architectureMap[architecture]
        secondArchitecture?.apply {
            secondTextView.text = get(0).name
            secondTextView.setOnClickListener(this@ArchitectureFragment)
            val labels = mutableListOf<String>()
            forEach {
                labels.add(it.name)
                secondLabelLayout.setAdapter(LabelLayout.Adapter(labels))
            }
            if (loadArticle) {
                loadArticles(get(0).id)
            }
        }
    }

    private fun loadArticles(cid: Int?) {
        lifecycleScope.launch {
            cid?.let { architectureViewModel.value.refreshArticles(it) }
            finishLoading()
        }
    }

    override fun onClick(v: View?) {
        if (v == firstTextView) {
            firstClassMenu?.apply {
                if (height > halfDisplayHeight) {
                    this.height = halfDisplayHeight
                }
                showAsDropDown(v)
            }
        } else {
            secondClassMenu?.apply {
                if (height > halfDisplayHeight) {
                    this.height = halfDisplayHeight
                }
                showAsDropDown(v)
            }
        }
    }

    private fun inflateLabelView(): ScrollView {
        return LayoutInflater.from(requireContext())
            .inflate(R.layout.layout_category_label, null) as ScrollView
    }

    override fun onLoadMore(recyclerView: WanRecyclerView) {
        selectedArchitecture?.apply {
            architectureViewModel.value.loadMoreArticles(this.id)
        }
    }

    override fun onRefresh() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                selectedArchitecture?.apply {
                    architectureViewModel.value.refreshArticles(this.id)
                }
            }
            refreshLayout.isRefreshing = false
        }
    }
}
