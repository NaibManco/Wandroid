package com.naib.wandroid.user

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseActivity
import com.naib.wandroid.base.widget.WanRecyclerView
import com.naib.wandroid.base.WebViewActivity
import com.naib.wandroid.base.utils.show
import com.naib.wandroid.main.article.Article
import com.naib.wandroid.global.OnItemClickListener
import com.naib.wandroid.global.OnItemLongClickListener
import com.naib.wandroid.share.ShareActivity
import com.naib.wandroid.user.data.ProfileArticleAdapter
import com.naib.wandroid.user.data.UserInfoManager
import com.naib.wandroid.user.data.UserViewModel
import kotlinx.coroutines.launch

/**
 *  Created by Naib on 2020/6/23
 */
class ProfileActivity : BaseActivity(), WanRecyclerView.OnLoadMoreListener,
    OnItemClickListener<Article>, View.OnClickListener, OnItemLongClickListener<Article> {
    private var viewModel = viewModels<UserViewModel>()

    private lateinit var photoView: ImageView
    private lateinit var nameView: TextView
    private lateinit var coinView: TextView
    private lateinit var levelView: TextView
    private lateinit var rankView: TextView
    private lateinit var shareArticle: ImageView

    private lateinit var collectArticlesListView: WanRecyclerView
    private lateinit var collectArticleAdapter: ProfileArticleAdapter

    override fun onCreateContentView(container: ViewGroup) {
        layoutInflater.inflate(R.layout.activity_profile, container)
        photoView = findViewById(R.id.profile_photo)
        nameView = findViewById(R.id.profile_name)
        coinView = findViewById(R.id.profile_coin)
        levelView = findViewById(R.id.profile_level)
        rankView = findViewById(R.id.profile_rank)
        shareArticle = findViewById(R.id.profile_add_shared_article)
        shareArticle.setOnClickListener(this)
        photoView.setColorFilter(resources.getColor(R.color.colorAccent))

        val userInfo = UserInfoManager.getUserInfo()
        userInfo?.apply {
            nameView.text = username

            if (TextUtils.isEmpty(icon)) {
                Glide.with(this@ProfileActivity)
                    .load(R.drawable.ic_profile)
                    .into(photoView)
            } else {
                Glide.with(this@ProfileActivity)
                    .load(Uri.parse(UserInfoManager.getUserInfo()?.icon))
                    .into(photoView)
            }
        }

        collectArticlesListView = findViewById(R.id.profile_collect_article_list)
        collectArticlesListView.apply {
            this.loadMoreListener = this@ProfileActivity
            this.layoutManager = LinearLayoutManager(this@ProfileActivity).apply {
                orientation = RecyclerView.HORIZONTAL
            }
            collectArticleAdapter = ProfileArticleAdapter()
            collectArticleAdapter.onItemClickListener = this@ProfileActivity
            collectArticleAdapter.onItemLongClickListener = this@ProfileActivity
            viewModel.value.collectArticles.observe(this@ProfileActivity) {
                collectArticleAdapter.update(it)
            }
            this.adapter = collectArticleAdapter
        }

        lifecycleScope.launch {
            viewModel.value.getUserInfo()?.apply {
                coinView.text =
                    String.format(resources.getString(R.string.profile_my_coin), coinCount)
                levelView.text =
                    String.format(resources.getString(R.string.profile_my_level), level)
                rankView.text = String.format(resources.getString(R.string.profile_my_rank), rank)
            }
        }
    }

    override fun onCreateToolbar(toolbar: Toolbar) {
        super.onCreateToolbar(toolbar)
        toolbar.elevation = 0f
        toolbar.title = getString(R.string.profile_title)
    }

    override fun onLoadMore(recyclerView: WanRecyclerView) {

    }

    override fun onItemClick(article: Article, position: Int) {
        WebViewActivity.launch(article.link!!, article.title!!, article.id, article.collect)
    }

    override fun onClick(v: View?) {
        startActivity(Intent(this, ShareActivity::class.java))
    }

    override fun onItemLongClick(view: View, t: Article, position: Int) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.profile_collect_menu)
        popupMenu.setOnMenuItemClickListener {
            lifecycleScope.launch {
                val result = viewModel.value.unCollect(t.id, t.originId)
                if (TextUtils.isEmpty(result)) {
                    collectArticleAdapter.remove(position)
                    show(this@ProfileActivity, R.string.profile_uncollect_success)
                } else {
                    show(this@ProfileActivity, result)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }
}