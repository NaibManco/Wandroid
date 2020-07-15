package com.naib.wandroid.search

import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseActivity
import com.naib.wandroid.base.utils.KeyBoardUtils
import com.naib.wandroid.base.widget.WanSearchView

/**
 *  Created by Naib on 2020/7/13
 */
class SearchActivity : BaseActivity(), WanSearchView.OnSearchListener,
    WanSearchView.OnClearListener , SearchCallback{

    private lateinit var searchFragment: SearchFragment
    private lateinit var resultFragment: SearchResultFragment

    private lateinit var searchContainer: ViewGroup
    private lateinit var searchView: WanSearchView

    override fun onCreateContentView(container: ViewGroup) {
        layoutInflater.inflate(R.layout.activity_search, container)
        searchContainer = container.findViewById(R.id.search_container)

        searchFragment = SearchFragment()
        searchFragment.searchCallback = this
        resultFragment = SearchResultFragment()
        supportFragmentManager.beginTransaction().replace(R.id.search_container, searchFragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val result = super.onCreateOptionsMenu(menu);
        menu?.apply {
            getItem(MENU_SEARCH).isVisible = false
        }
        menu?.clear()
        return result
    }

    override fun onCreateToolbar(toolbar: Toolbar) {
        super.onCreateToolbar(toolbar)
        toolbar.title = ""

        searchView = WanSearchView(this)
        searchView.setPadding(0, 0, resources.getDimensionPixelSize(R.dimen.dp_16), 0)
        searchView.setOnSearchListener(this)
        searchView.onClearListener = this
        toolbar.addView(searchView)
    }

    override fun onSearch(words: String) {
        if (words.isNotEmpty()) {
            if (resultFragment.isResumed) {
                resultFragment.search(words)
            } else {
                val bundle = Bundle()
                bundle.putString(SearchResultFragment.KEY_WORDS, words)
                resultFragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.search_container, resultFragment)
                    .commit()
            }
            KeyBoardUtils.closeKeyboard(this)
        }
    }

    override fun onClear() {
        if (searchFragment.isResumed) {
            return
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.search_container, searchFragment)
            .commit()
    }

    override fun onLabelSearch(label: String?) {
        label?.apply {
            if (isNotEmpty()) {
                searchView.setSearchWords(label)
                onSearch(this)
            }
        }
    }
}