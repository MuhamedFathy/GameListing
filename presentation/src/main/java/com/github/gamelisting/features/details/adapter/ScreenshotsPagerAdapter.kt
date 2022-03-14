package com.github.gamelisting.features.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.gamelisting.databinding.ItemGameDetailsPagerBinding
import com.github.gamelisting.extension.load
import com.github.gamelisting.features.details.adapter.ScreenshotsPagerAdapter.ScreenshotPagerHolder
import javax.inject.Inject

/**
 * Authored by Mohamed Fathy on 14 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
class ScreenshotsPagerAdapter @Inject constructor() : RecyclerView.Adapter<ScreenshotPagerHolder>() {

  private val items = mutableListOf<String>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotPagerHolder {
    val binding = ItemGameDetailsPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ScreenshotPagerHolder(binding)
  }

  override fun onBindViewHolder(holder: ScreenshotPagerHolder, position: Int) {
    holder.bind(items[position])
  }

  override fun getItemCount(): Int = items.count()

  fun setData(newItems: List<String>) {
    items.clear()
    items.addAll(newItems)
    notifyItemRangeChanged(0, newItems.size)
  }

  inner class ScreenshotPagerHolder(
    private val binding: ItemGameDetailsPagerBinding
  ) : ViewHolder(binding.root) {

    fun bind(images: String) {
      binding.root.load(images)
    }
  }
}