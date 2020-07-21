package com.naib.wandroid.share

import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseActivity
import com.naib.wandroid.base.utils.show
import kotlinx.coroutines.launch

/**
 *  Created by Naib on 2020/7/14
 */
class ShareActivity : BaseActivity(), View.OnClickListener, TextWatcher {

    private val viewModel = viewModels<ShareViewModel>()

    private lateinit var descView: TextView
    private lateinit var shareButton: Button
    private lateinit var titleView: TextView
    private lateinit var linkView: TextView
    private lateinit var errorView: TextView

    override fun onCreateContentView(container: ViewGroup) {
        layoutInflater.inflate(R.layout.activity_share, container)
        descView = findViewById(R.id.share_desc)
        descView.text = Html.fromHtml(resources.getString(R.string.share_specification))
        descView.movementMethod = LinkMovementMethod.getInstance()

        shareButton = findViewById(R.id.btn_share)
        shareButton.setOnClickListener(this)
        titleView = findViewById(R.id.share_title)
        titleView.addTextChangedListener(this)
        linkView = findViewById(R.id.share_link)
        linkView.addTextChangedListener(this)
        errorView = findViewById(R.id.share_error_tip)
    }

    override fun onCreateToolbar(toolbar: Toolbar) {
        super.onCreateToolbar(toolbar)
        toolbar.title = resources.getText(R.string.share_text)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val result = super.onCreateOptionsMenu(menu)
        menu?.apply {
            getItem(MENU_SEARCH).isVisible = false
        }
        return result
    }

    override fun onClick(v: View?) {
        if (TextUtils.isEmpty(titleView.text)) {
            errorView.visibility = View.VISIBLE
            errorView.setText(R.string.share_empty_title_error)
            return
        }
        if (TextUtils.isEmpty(linkView.text)) {
            errorView.visibility = View.VISIBLE
            errorView.setText(R.string.share_empty_link_error)
            return
        }
        lifecycleScope.launch {
            val result = viewModel.value.share(titleView.text.toString(), linkView.text.toString())
            if (TextUtils.isEmpty(result)) {
                show(this@ShareActivity, R.string.share_share_success)
            } else {
                show(this@ShareActivity, result)
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        errorView.visibility = View.INVISIBLE
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}