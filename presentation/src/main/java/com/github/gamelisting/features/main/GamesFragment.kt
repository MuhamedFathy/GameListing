package com.github.gamelisting.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.paging.PagingData
import com.github.gamelisting.R
import com.github.gamelisting.databinding.FragmentGamesBinding
import com.github.gamelisting.features.details.GameDetailsFragment
import com.github.gamelisting.features.main.viewmodel.GamesViewModel
import com.github.gamelisting.features.main.viewmodel.uimodel.GameUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GamesFragment : Fragment() {

  @Inject lateinit var gamesAdapter: GamesAdapter

  private var _binding: FragmentGamesBinding? = null
  private val binding get() = _binding!!

  private val viewModel: GamesViewModel by viewModels()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentGamesBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupToolbar()
    updateUIWithLoading()
    setupClickListeners()
    setupRecyclerViewWithAdapter()
    setupRefreshLayout()
    observeData()
  }

  private fun setupToolbar() {
    (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
  }

  private fun setupClickListeners() {
    binding.gamesErrorInclude.errorViewRetryButton.setOnClickListener {
      updateUIWithLoading()
      gamesAdapter.refresh()
    }
  }

  private fun setupRecyclerViewWithAdapter() {
    checkPagingState()
    binding.gamesRecyclerView.adapter = gamesAdapter
    gamesAdapter.itemCallback = { gameId ->
      val itemDetailFragmentContainer: View? = view?.findViewById(R.id.item_detail_nav_container)
      val isTablet = itemDetailFragmentContainer != null
      if (isTablet) {
        itemDetailFragmentContainer!!.findNavController()
          .navigate(R.id.game_details_fragment, bundleOf(GameDetailsFragment.ARG_GAME_ID to gameId))
      } else {
        findNavController().navigate(R.id.show_game_details, bundleOf(GameDetailsFragment.ARG_GAME_ID to gameId))
      }
    }
  }

  private fun setupRefreshLayout() {
    binding.gamesRefreshLayout.setOnRefreshListener {
      updateUIWithLoading()
      gamesAdapter.refresh()
    }
  }

  private fun checkPagingState() {
    gamesAdapter.addLoadStateListener { state ->
      state.mediator?.let { mediator ->
        when {
          mediator.refresh is Error && gamesAdapter.itemCount < 1 -> updateUIWithError()
          (mediator.refresh is Loading && mediator.refresh.endOfPaginationReached.not() && gamesAdapter.itemCount < 1)
            .or(mediator.append is Loading && mediator.append.endOfPaginationReached.not() && gamesAdapter.itemCount > 0) -> updateUIWithLoading()
        }
      }
    }
  }

  private fun observeData() {
    viewModel.gamesLiveData.observe(viewLifecycleOwner) { data -> updateUI(data) }
  }

  private fun updateUIWithLoading() {
    binding.gamesErrorInclude.root.isVisible = false
    binding.gamesRefreshLayout.isVisible = true
    binding.gamesRefreshLayout.isRefreshing = true
  }

  private fun updateUI(data: PagingData<GameUIModel>) {
    binding.gamesRefreshLayout.isRefreshing = false
    gamesAdapter.submitData(lifecycle, data)
  }

  private fun updateUIWithError() {
    binding.gamesRefreshLayout.isRefreshing = false
    binding.gamesErrorInclude.root.isVisible = true
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
