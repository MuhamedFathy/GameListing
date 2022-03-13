package com.github.gamelisting.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Authored by Mohamed Fathy on 13 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
fun ImageView.load(url: String) = Glide.with(context).load(url).into(this)

