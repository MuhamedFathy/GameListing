package com.github.gamelisting.features.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.gamelisting.databinding.ItemGameRatingBinding
import com.github.gamelisting.features.details.adapter.RatingsAdapter.RatingHolder
import com.github.gamelisting.features.details.viewmodel.uimodel.GameDetailsRatingsUIModel
import javax.inject.Inject
import kotlin.math.roundToInt

/**
 * Authored by Mohamed Fathy on 14 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
class RatingsAdapter @Inject constructor() : RecyclerView.Adapter<RatingHolder>() {

  private val items = mutableListOf<GameDetailsRatingsUIModel>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingHolder {
    val binding = ItemGameRatingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return RatingHolder(binding)
  }

  override fun onBindViewHolder(holder: RatingHolder, position: Int) {
    holder.bind(items[position])
  }

  override fun getItemCount(): Int = items.count()

  fun setData(newItems: List<GameDetailsRatingsUIModel>) {
    items.clear()
    items.addAll(newItems)
    notifyItemRangeChanged(0, newItems.size)
  }

  inner class RatingHolder(
    private val binding: ItemGameRatingBinding
  ) : ViewHolder(binding.root) {

    fun bind(rating: GameDetailsRatingsUIModel) {
      with(rating) {
        binding.gameRatingTitle.text = title
        binding.gameRatingProgress.progress = percent.roundToInt()
        binding.gameRatingProgress.setIndicatorColor(ContextCompat.getColor(itemView.context, progressTint))
      }
    }
  }
}