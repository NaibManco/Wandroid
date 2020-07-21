package com.naib.wandroid.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseFragment
import com.naib.wandroid.base.widget.LabelLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *  Created by Naib on 2020/7/13
 */
class SearchFragment : BaseFragment(), LabelLayout.OnItemClickListener {
    private val viewModel = viewModels<SearchViewModel>()

    private lateinit var hotKeyLayout: LabelLayout
    private lateinit var clearHistory: TextView
    private lateinit var historyLayout: LabelLayout

    var searchCallback: SearchCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hotKeyLayout = view.findViewById(R.id.layout_hot_keys)
        hotKeyLayout.setOnItemClickListener(this)
        clearHistory = view.findViewById(R.id.text_clear_history)
        historyLayout = view.findViewById(R.id.layout_search_history)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                viewModel.value.loadHotKey()
            }?.apply {
                val keys = mutableListOf<String>()
                forEach {
                    keys.add(it.name)
                }
                hotKeyLayout.setAdapter(LabelLayout.Adapter(keys))
            }
        }
    }

    override fun onItemClick(position: Int, label: String?) {
        searchCallback?.apply {
            onLabelSearch(label)
        }
    }
}