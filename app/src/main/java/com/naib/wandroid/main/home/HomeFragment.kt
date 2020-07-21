package com.naib.wandroid.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.naib.wandroid.R
import com.naib.wandroid.base.widget.WanRecyclerView
import com.naib.wandroid.base.widget.WanRefreshLayout
import com.naib.wandroid.main.article.ArticleAdapter
import com.naib.wandroid.main.BaseArticleFragment
import com.naib.wandroid.main.home.data.*
import com.naib.wandroid.main.project.ProjectAdapter
import com.youth.banner.Banner
import com.youth.banner.config.BannerConfig
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.AlphaPageTransformer
import com.youth.banner.util.BannerUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : BaseArticleFragment(), SwipeRefreshLayout.OnRefreshListener,
    WanRecyclerView.OnLoadMoreListener {

    private var homeViewModel = viewModels<HomeViewModel>()

    private lateinit var refreshLayout: WanRefreshLayout

    private lateinit var banner: Banner<com.naib.wandroid.main.home.data.Banner, HomeBannerAdapter>
    private lateinit var adapter: HomeBannerAdapter

    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: HomePageAdapter
    private lateinit var tabLayout: TabLayout

    private lateinit var articleListView: WanRecyclerView
    private lateinit var articleAdapter: ArticleAdapter

    private lateinit var projectListView: WanRecyclerView
    private lateinit var projectAdapter: ProjectAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout = view.findViewById(R.id.home_refresh_layout)
        refreshLayout.setOnRefreshListener(this)
        banner = view.findViewById(R.id.home_banner)
        banner.addBannerLifecycleObserver(this)
        banner.indicator = CircleIndicator(requireContext())
        banner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
        banner.setIndicatorMargins(
            IndicatorConfig.Margins(
                0, 0,
                BannerConfig.INDICATOR_MARGIN, BannerUtils.dp2px(12f).toInt()
            )
        )
        banner.setBannerGalleryEffect(30, 15)
        banner.addPageTransformer(AlphaPageTransformer())

        adapter = HomeBannerAdapter(emptyList()).apply {
            banner.adapter = this
        }
        homeViewModel.value.banner.observe(viewLifecycleOwner) {
            adapter.setDatas(it)
            adapter.notifyDataSetChanged()
        }

        /**
         * Article RecyclerView
         */
        articleListView = WanRecyclerView(
            requireContext()
        ).apply {
            this.loadMoreListener = this@HomeFragment
            this.layoutManager = LinearLayoutManager(requireContext())
            articleAdapter = ArticleAdapter()
            articleAdapter.onItemClickListener = this@HomeFragment
            articleAdapter.onLikeClickListener = this@HomeFragment
            homeViewModel.value.articles.observe(viewLifecycleOwner) {
                articleAdapter.update(it)
            }
            this.adapter = articleAdapter
        }

        /**
         * Project RecyclerView
         */
        projectListView = WanRecyclerView(
            requireContext()
        ).apply {
            this.loadMoreListener = this@HomeFragment
            this.layoutManager = LinearLayoutManager(requireContext())
            projectAdapter = ProjectAdapter()
            projectAdapter.onItemClickListener = this@HomeFragment
            projectAdapter.onLikeClickListener = this@HomeFragment
            homeViewModel.value.projects.observe(viewLifecycleOwner) {
                projectAdapter.update(it)
            }
            this.adapter = projectAdapter
        }

        /**
         * ViewPager
         */
        pagerAdapter = HomePageAdapter().apply {
            this.titles = resources.getStringArray(R.array.home_tab_array).toList()
            this.add(articleListView)
            this.add(projectListView)

            viewPager = view.findViewById(R.id.home_view_pager)
            viewPager.adapter = this
            tabLayout = view.findViewById(R.id.home_tab_layout)
            tabLayout.setupWithViewPager(viewPager)
        }
        onRefresh()
    }

    override fun onRefresh() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (viewPager.currentItem == 0) {
                    homeViewModel.value.refreshArticles()
                } else {
                    homeViewModel.value.refreshProjects()
                }
            }
            refreshLayout.isRefreshing = false
            finishLoading()
        }
    }

    override fun onLoadMore(recyclerView: WanRecyclerView) {
        if (recyclerView == articleListView) {
            homeViewModel.value.loadMoreArticles()
        } else {
            homeViewModel.value.loadMoreProjects()
        }
    }
}
