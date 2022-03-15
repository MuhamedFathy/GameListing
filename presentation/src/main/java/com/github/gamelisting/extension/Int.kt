package com.github.gamelisting.extension

import android.content.Context
import android.util.TypedValue

/**
 * Authored by Mohamed Fathy on 14 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */

fun Int.toPixel(context: Context) =
  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()