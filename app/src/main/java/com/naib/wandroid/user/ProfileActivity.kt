package com.naib.wandroid.user

import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseActivity
import com.naib.wandroid.base.BaseRecyclerAdapter
import com.naib.wandroid.base.widget.WanRecyclerView
import com.naib.wandroid.base.WebViewActivity
import com.naib.wandroid.global.Article
import com.naib.wandroid.global.OnItemClickListener
import com.naib.wandroid.user.data.CollectArticleAdapter
import com.naib.wandroid.user.data.UserInfoManager
import com.naib.wandroid.user.data.UserViewModel
import kotlinx.coroutines.launch

/**
 *  Created by Naib on 2020/6/23
 */
class ProfileActivity : BaseActivity(), WanRecyclerView.OnLoadMoreListener,
    OnItemClickListener<Article>, View.OnClickListener {
    private var viewModel = viewModels<UserViewModel>()

    private lateinit var photoView: ImageView
    private lateinit var nameView: TextView
    private lateinit var emailView: TextView
    private lateinit var shareArticle: ImageView

    private lateinit var collectArticlesListView: WanRecyclerView
    private lateinit var collectArticleAdapter: CollectArticleAdapter

    override fun onCreateContentView(container: ViewGroup) {
        layoutInflater.inflate(R.layout.activity_profile, container)
        photoView = findViewById(R.id.profile_photo)
        nameView = findViewById(R.id.profile_name)
        emailView = findViewById(R.id.profile_email)
        shareArticle = findViewById(R.id.profile_add_shared_article)
        shareArticle.setOnClickListener(this)

        val userInfo = UserInfoManager.getUserInfo()
        userInfo?.apply {
            nameView.text = username
            emailView.text = email

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
            collectArticleAdapter = CollectArticleAdapter()
            collectArticleAdapter.onItemClickListener = this@ProfileActivity
            viewModel.value.collectArticles.observe(this@ProfileActivity) {
                collectArticleAdapter.update(it)
            }
            this.adapter = collectArticleAdapter
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

    }
}