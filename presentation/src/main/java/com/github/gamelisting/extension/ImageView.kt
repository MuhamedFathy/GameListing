package com.github.gamelisting.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Authored by Mohamed Fathy on 13 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
fun ImageView.load(url: String) = post {
  Glide.with(context)
    .load(url)
    .apply(RequestOptions().override(measuredWidth, measuredHeight).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE).centerCrop())
    .thumbnail(0.1F)
    .into(this)
}

fun ImageView.clear() = Glide.with(context).clear(this)
