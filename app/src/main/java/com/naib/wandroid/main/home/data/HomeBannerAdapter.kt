package com.naib.wandroid.main.home.data

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naib.wandroid.R
import com.naib.wandroid.base.WebViewActivity
import com.naib.wandroid.base.widget.RatioImageView
import com.naib.wandroid.main.home.data.HomeBannerAdapter.BannerViewHolder
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.listener.OnBannerListener

/**
 * Created by wanglongfei on 2020/5/19
 */
class HomeBannerAdapter(banners: List<Banner?>?) :
    BannerAdapter<Banner?, BannerViewHolder>(banners), OnBannerListener<Banner> {

    init {
        setOnBannerListener(this)
    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_banner, parent, false)
        )
    }

    override fun onBindView(holder: BannerViewHolder?, data: Banner?, position: Int, size: Int) {
        holder?.imageView?.let {
            Glide.with(it)
                .load(Uri.parse(data?.imagePath))
                .into(it)
        }
        holder?.titleView?.text = data?.title
    }

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.banner_image)
        val titleView: TextView = itemView.findViewById(R.id.banner_title)
    }

    override fun OnBannerClick(data: Banner?, position: Int) {
        data?.apply {
            WebViewActivity.launch(url, title)
        }
    }
}