package com.github.gamelisting.features.details.callbacks

import com.github.gamelisting.features.details.callbacks.AppBarStateChangedListener.State.COLLAPSED
import com.github.gamelisting.features.details.callbacks.AppBarStateChangedListener.State.EXPANDED
import com.github.gamelisting.features.details.callbacks.AppBarStateChangedListener.State.IDLE
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlin.math.abs

/**
 * Authored by Mohamed Fathy on 14 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
abstract class AppBarStateChangedListener : OnOffsetChangedListener {

  enum class State {
    EXPANDED, COLLAPSED, IDLE
  }

  private var mCurrentState = IDLE

  override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
    when {
      verticalOffset == 0 -> setCurrentStateAndNotify(appBarLayout, EXPANDED)
      abs(verticalOffset) >= appBarLayout.totalScrollRange -> setCurrentStateAndNotify(appBarLayout, COLLAPSED)
      else -> setCurrentStateAndNotify(appBarLayout, IDLE)
    }
  }

  private fun setCurrentStateAndNotify(appBarLayout: AppBarLayout, state: State) {
    if (mCurrentState != state) onStateChanged(appBarLayout, state)
    mCurrentState = state
  }

  abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State)
}