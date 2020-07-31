package com.naib.wandroid.global

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseActivity

/**
 *  Created by Naib on 2020/7/31
 */
class ImageViewActivity : BaseActivity() {
    companion object {
        private const val KEY_URL = "key_url"

        fun launch(context: Context, url: String) {
            val intent = Intent(context, ImageViewActivity::class.java)
            intent.putExtra(KEY_URL, url)
            context.startActivity(intent)
        }
    }

    private lateinit var photoView: PhotoView
    private var url: String? = null

    override fun onCreateContentView(container: ViewGroup) {
        url = intent.getStringExtra(KEY_URL)
        layoutInflater.inflate(R.layout.activity_image_view, container)
        photoView = findViewById(R.id.photo_view)

        Glide.with(this).load(url).into(photoView)
        window.navigationBarColor = Color.BLACK
    }

    override fun onCreateToolbar(toolbar: Toolbar) {
        super.onCreateToolbar(toolbar)
        toolbar.visibility = View.GONE
    }
}