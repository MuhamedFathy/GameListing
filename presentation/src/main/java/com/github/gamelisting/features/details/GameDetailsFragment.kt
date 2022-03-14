package com.github.gamelisting.features.details

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.github.gamelisting.databinding.FragmentGameDetailsBinding
import com.github.gamelisting.extension.toPixel
import com.github.gamelisting.features.details.adapter.RatingsAdapter
import com.github.gamelisting.features.details.adapter.ScreenshotsPagerAdapter
import com.github.gamelisting.features.details.callbacks.AppBarStateChangedListener
import com.github.gamelisting.features.details.callbacks.AppBarStateChangedListener.State.COLLAPSED
import com.github.gamelisting.features.details.viewmodel.GameDetailsViewModel
import com.github.gamelisting.features.details.viewmodel.uimodel.GameDetailsUIModel
import com.github.gamelisting.resource.ResourceState.ERROR
import com.github.gamelisting.resource.ResourceState.LOADING
import com.github.gamelisting.resource.ResourceState.SUCCESS
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@AndroidEntryPoint
class GameDetailsFragment : Fragment() {

  @Inject lateinit var screenshotsPagerAdapter: ScreenshotsPagerAdapter
  @Inject lateinit var ratingsAdapter: RatingsAdapter

  private var _binding: FragmentGameDetailsBinding? = null
  private val binding get() = _binding!!

  private val gameId by lazy { arguments?.getLong(ARG_GAME_ID, 0L) ?: 0L }

  private val viewModel: GameDetailsViewModel by viewModels()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentGameDetailsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if (gameId == 0L) {
      binding.gameDetailsEmptyView?.root?.isVisible = true
      return
    }
    setupToolbar()
    setupClickListeners()
    setupPager()
    setupRatingRecyclerView()
    if (savedInstanceState == null) viewModel.loadGameDetails(gameId)
    observeData()
  }

  private fun setupToolbar() {
    (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
    (requireActivity() as AppCompatActivity).supportActionBar?.apply { setDisplayHomeAsUpEnabled(true) }
    binding.toolbar?.setNavigationOnClickListener { requireActivity().onBackPressed() }
    binding.appbarLayout?.addOnOffsetChangedListener(object : AppBarStateChangedListener() {
      override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
        binding.gameDetailsToolbarContentLinear?.apply {
          animate().scaleX(if (state == COLLAPSED) 1.0F else 0.97F)
            .scaleY(if (state == COLLAPSED) 1.0F else 0.97F)
            .translationY(if (state == COLLAPSED) 0.0F else -20.0F)
            .alpha(if (state == COLLAPSED) 1.0F else 0.0F)
            .setDuration(if (state == COLLAPSED) 200L else 50L)
            .start()
        }
        binding.gameDetailsContentCardView?.apply {
          ObjectAnimator.ofFloat(this, "radius", (if (state == COLLAPSED) 0 else 30).toPixel(appBarLayout.context).toFloat())
            .setDuration(200L)
            .start()
        }
      }
    })
  }

  private fun setupClickListeners() {
    binding.gamesErrorInclude.errorViewRetryButton.setOnClickListener { viewModel.loadGameDetails(gameId) }
  }

  private fun setupPager() {
    binding.gameDetailsImagesViewPager.apply {
      adapter = screenshotsPagerAdapter
      (getChildAt(0) as? RecyclerView)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
      (getChildAt(0) as? RecyclerView)?.itemAnimator = null
    }
  }

  private fun setupRatingRecyclerView() {
    binding.gameDetailsMainInclude.gameDetailsRatingsRecycler.adapter = ratingsAdapter
  }

  private fun observeData() {
    viewModel.gameDetailsLivedata.observe(viewLifecycleOwner) { resource ->
      when (resource.state) {
        LOADING -> updateUIWithLoading()
        SUCCESS -> resource.data?.let { updateUI(it) } ?: updateUIWithError()
        ERROR -> updateUIWithError()
      }
    }
  }

  private fun updateUIWithLoading() {
    binding.gamesErrorInclude.root.isVisible = false
    binding.gameLoadingFrameLayout.isVisible = true
  }

  private fun updateUI(gameDetails: GameDetailsUIModel) {
    with(gameDetails) {
      binding.gameLoadingFrameLayout.isVisible = false
      binding.gameDetailsToolbarTitle?.text = name
      screenshotsPagerAdapter.setData(screenshots)
      binding.gameDetailsMainInclude.gameDetailsNameTextView.text = name
      if (ratings.isNotEmpty()) ratingsAdapter.setData(ratings) else binding.gameDetailsMainInclude.gameDetailsRatingsRecycler.isVisible = false
      binding.gameDetailsMainInclude.gameDetailsDescriptionTextView.text = HtmlCompat.fromHtml(description, FROM_HTML_MODE_COMPACT)
      binding.gameDetailsMainInclude.gameDetailsGenreTextView.text = genres
    }
  }

  private fun updateUIWithError() {
    binding.gamesErrorInclude.root.isVisible = true
    binding.gameLoadingFrameLayout.isVisible = false
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  companion object {

    const val ARG_GAME_ID = "game_id"
  }
}
