package com.naib.wandroid.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.naib.wandroid.R
import com.naib.wandroid.base.utils.LogUtil
import com.naib.wandroid.main.home.data.ImageAdapter
import com.youth.banner.Banner

class HomeFragment : Fragment() {

    private var homeViewModel = viewModels<HomeViewModel>()

    private lateinit var banner: Banner<com.naib.wandroid.main.home.data.Banner, ImageAdapter>
    private lateinit var adapter: ImageAdapter;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        banner = view.findViewById(R.id.home_banner)
        adapter = ImageAdapter(emptyList()).apply {
            banner.adapter = this
        }
        homeViewModel.value.banner.observe(viewLifecycleOwner) {
            LogUtil.d("banner changed")
            adapter.setDatas(it)
            adapter.notifyDataSetChanged()
        }
    }
}
