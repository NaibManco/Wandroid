package com.naib.wandroid.base

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.naib.wandroid.R
import com.naib.wandroid.WanApplication
import com.naib.wandroid.base.utils.LogUtil
import com.naib.wandroid.base.utils.show
import com.naib.wandroid.global.GlobalViewModel
import com.naib.wandroid.search.SearchActivity
import kotlinx.coroutines.launch

/**
 * Created by Naib on 2020/6/15
 */
open class WebViewActivity : BaseActivity() {

    companion object {
        private const val KEY_URL: String = "url"
        private const val KEY_TITLE: String = "title"
        private const val KEY_ID: String = "title"
        private const val KEY_LIKE: String = "title"

        fun launch(url: String, title: String, id: Long, like: Boolean) {
            val context = WanApplication.instance!!.topActivity
            var intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(KEY_URL, url)
            intent.putExtra(KEY_TITLE, title)
            intent.putExtra(KEY_ID, id)
            intent.putExtra(KEY_LIKE, like)
            context.startActivity(intent)
        }

        fun launch(url: String, title: String) {
            val context = WanApplication.instance!!.topActivity
            var intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(KEY_URL, url)
            intent.putExtra(KEY_TITLE, title)
            context.startActivity(intent)
        }
    }

    private val globalViewModel = viewModels<GlobalViewModel>()

    /**
     * 标题
     */
    private var title: String? = null

    /**
     * url
     */
    private var url: String? = null

    /**
     * 是否收藏
     */
    private var like: Boolean = false

    /**
     * 文章id
     */
    private var id: Long = 0

    override fun onCreateContentView(container: ViewGroup) {
        getIntentData()
        if (TextUtils.isEmpty(url)) {
            throw RuntimeException("Url cannot be empty")
        }
        AgentWeb.with(this).setAgentWebParent(container, FrameLayout.LayoutParams(-1, -1))
            .useDefaultIndicator(getColor(R.color.colorAccent))
            .setWebChromeClient(receivedTitleCallback)
            .setWebViewClient(webViewClientCallback)
            .createAgentWeb()
            .ready()
            .go(url)
    }

    /**
     * 获取传入的数据
     */
    open fun getIntentData() {
        title = intent.getStringExtra(KEY_TITLE)
        url = intent.getStringExtra(KEY_URL)
        id = intent.getLongExtra(KEY_ID, -1)
        like = intent.getBooleanExtra(KEY_LIKE, false)
    }

    /**
     * 加载成功之后，修改标题
     */
    private val receivedTitleCallback = WebChromeClientCallback()

    inner class WebChromeClientCallback : WebChromeClient() {

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            toolbar.title = title
        }
    }

    private val webViewClientCallback = WebViewClientCallback()

    inner class WebViewClientCallback : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (handleUrlLoading(request?.url)) {
                return true
            }
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (handleUrlLoading(Uri.parse(url))) {
                return true
            }
            return super.shouldOverrideUrlLoading(view, url)
        }
    }

    fun handleUrlLoading(uri: Uri?): Boolean {
        val intent = Intent()
        intent.data = uri
        if (!uri?.scheme?.startsWith("http")!!) {
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
            return true
        }
        return false
    }

    private val menuOpenInBrowser = 1

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            menuOpenInBrowser -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
                return true
            }
            R.id.toolbar_like -> {
                if (like) unCollect(item) else collect(item)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun collect(item: MenuItem) {
        lifecycleScope.launch {
            var success = if (id > 0) {
                globalViewModel.value.collectArticle(id)
            } else {
                globalViewModel.value.collectWebsite(toolbar.title.toString(), "", url!!)
            }
            if (success) {
                like = true
                likeIcon(item)
            } else {
                show(this@WebViewActivity, R.string.common_error)
            }
        }
    }

    private fun unCollect(item: MenuItem) {
        lifecycleScope.launch {
            var success = if (id > 0) {
                globalViewModel.value.unCollectArticle(id)
            } else {
                globalViewModel.value.unCollectArticle(id)
            }
            if (success) {
                like = false
                item.icon = getDrawable(R.drawable.ic_like)
            } else {
                show(this@WebViewActivity, R.string.common_error)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val result = super.onCreateOptionsMenu(menu)
        menu?.apply {
            getItem(MENU_SEARCH).isVisible = false
            getItem(MENU_LIKE).isVisible = true
            if (like) {
                likeIcon(getItem(MENU_LIKE))
            }
            add(Menu.NONE, menuOpenInBrowser, Menu.NONE, R.string.menu_open_in_browser)
        }
        return result
    }

    private fun likeIcon(item: MenuItem) {
        val drawable = getDrawable(R.drawable.ic_like)
        drawable?.setColorFilter(
            resources.getColor(R.color.color_like),
            PorterDuff.Mode.SRC_ATOP
        )
        item.icon = drawable
    }
}