package com.naib.wandroid.main.home.data

import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naib.wandroid.base.widget.RatioImageView
import com.naib.wandroid.main.home.data.ImageAdapter.BannerViewHolder
import com.youth.banner.adapter.BannerAdapter

/**
 * Created by wanglongfei on 2020/5/19
 */
class ImageAdapter(banners: List<Banner?>?) :
    BannerAdapter<Banner?, BannerViewHolder>(banners) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = RatioImageView(parent.context)
        imageView.ratio = 1.8f
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder?, data: Banner?, position: Int, size: Int) {
        holder?.imageView?.let {
            Glide.with(it)
                .load(Uri.parse(data?.imagePath))
                .into(it)
        }
    }

    inner class BannerViewHolder(var imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)
}