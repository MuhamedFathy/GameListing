package com.github.gamelisting.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.gamelisting.databinding.ItemGameBinding
import com.github.gamelisting.extension.clear
import com.github.gamelisting.extension.load
import com.github.gamelisting.features.main.GamesAdapter.GameViewHolder
import com.github.gamelisting.features.main.viewmodel.uimodel.GameUIModel
import javax.inject.Inject

/**
 * Authored by Mohamed Fathy on 13 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
class GamesAdapter @Inject constructor() : PagingDataAdapter<GameUIModel, GameViewHolder>(COMPARATOR) {

  var itemCallback: ((Long) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
    return GameViewHolder(ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun onViewRecycled(holder: GameViewHolder) {
    holder.binding.itemImagePosterImageView.clear()
    super.onViewRecycled(holder)
  }

  override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
    getItem(position)?.let {
      holder.bind(it)
    }
  }

  companion object {

    private val COMPARATOR = object : DiffUtil.ItemCallback<GameUIModel>() {
      override fun areItemsTheSame(oldItem: GameUIModel, newItem: GameUIModel): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: GameUIModel, newItem: GameUIModel): Boolean {
        return oldItem == newItem
      }
    }
  }

  inner class GameViewHolder(val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(game: GameUIModel) {
      with(game) {
        binding.itemImageNameTextView.text = name
        binding.itemImageRatingTextView.text = rating
        binding.itemImageDateTextView.text = releaseDate
        binding.itemImagePosterImageView.load(poster)
        binding.root.setOnClickListener { itemCallback?.invoke(id) }
      }
    }
  }
}
