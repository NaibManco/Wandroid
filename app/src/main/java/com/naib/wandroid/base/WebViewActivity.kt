package com.naib.wandroid.base

import android.content.Intent
import android.text.TextUtils
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.naib.wandroid.R
import com.naib.wandroid.WanApplication

/**
 * Created by Naib on 2020/6/15
 */
open class WebViewActivity : BaseActivity() {

    companion object {
        private const val KEY_URL: String = "url"
        private const val KEY_TITLE: String = "title"

        fun launch(url: String, title: String) {
            val context = WanApplication.instance!!.topActivity
            var intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(KEY_URL, url)
            intent.putExtra(KEY_TITLE, title)
            context.startActivity(intent)
        }
    }

    /**
     * 标题
     */
    private var title: String? = null

    /**
     * url
     */
    private var url: String? = null

    override fun onCreateContentView(container: ViewGroup) {
        getIntentData()
        if (TextUtils.isEmpty(url)) {
            throw RuntimeException("Url cannot be empty")
        }
        AgentWeb.with(this).setAgentWebParent(container, FrameLayout.LayoutParams(-1, -1))
            .useDefaultIndicator(getColor(R.color.colorAccent))
            .setWebChromeClient(receivedTitleCallback)
            .createAgentWeb()
            .ready()
            .go(url)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 获取传入的数据
     */
    open fun getIntentData() {
        title = intent.getStringExtra(KEY_TITLE)
        url = intent.getStringExtra(KEY_URL)
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
}