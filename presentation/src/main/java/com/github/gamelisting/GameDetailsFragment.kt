package com.github.gamelisting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.gamelisting.databinding.FragmentGameDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@AndroidEntryPoint
class GameDetailsFragment : Fragment() {

  private var _binding: FragmentGameDetailsBinding? = null
  private val binding get() = _binding!!

  private val gameId by lazy { arguments?.getLong(ARG_GAME_ID, 0L) ?: 0L }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentGameDetailsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  companion object {

    const val ARG_GAME_ID = "game_id"
  }
}
