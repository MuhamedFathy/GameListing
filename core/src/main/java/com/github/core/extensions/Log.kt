package com.github.core.extensions

import timber.log.Timber

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */

fun Throwable.log() = Timber.e(this)