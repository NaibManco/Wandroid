package com.naib.wandroid.base.widget

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import com.naib.wandroid.base.R
import com.naib.wandroid.base.utils.KeyBoardUtils

/**
 *  Created by Naib on 2020/7/13
 */
class WanSearchView : LinearLayoutCompat, TextWatcher, View.OnClickListener {
    private lateinit var clearView: ImageView
    private lateinit var inputView: EditText

    private var onSearchListener: OnSearchListener? = null
    var onClearListener: OnClearListener? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        LayoutInflater.from(context).inflate(R.layout.layout_search, this, true)
        clearView = findViewById(R.id.search_icon_clear)
        clearView.setOnClickListener(this)
        inputView = findViewById(R.id.search_input)
        inputView.addTextChangedListener(this)
        isFocusable = true
        requestFocus()
        inputView.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSearchListener?.apply {
                    inputView.clearFocus()
                    onSearch(inputView.text.toString())
                }
                return@OnEditorActionListener true
            }
            false
        })
    }

    fun setSearchWords(words: String) {
        inputView.setText(words)
    }

    fun setOnSearchListener(onSearchListener: OnSearchListener) {
        this.onSearchListener = onSearchListener
    }

    override fun afterTextChanged(s: Editable?) {
        if (TextUtils.isEmpty(s)) {
            clearView.visibility = View.INVISIBLE
        } else {
            clearView.visibility = View.VISIBLE
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun onClick(v: View?) {
        inputView.setText("")
        requestFocus()
        KeyBoardUtils.openKeyboard(context, inputView)
        onClearListener?.apply {
            onClear()
        }
    }

    interface OnSearchListener {
        fun onSearch(words: String)
    }

    interface OnClearListener {
        fun onClear()
    }
}