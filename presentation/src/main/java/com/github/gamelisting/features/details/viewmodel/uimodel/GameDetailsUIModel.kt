package com.github.gamelisting.features.details.viewmodel.uimodel

import androidx.annotation.ColorRes

/**
 * Authored by Mohamed Fathy on 14 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
data class GameDetailsUIModel(
  val id: Long,
  val name: String,
  val description: String,
  val backgroundImage: String,
  val releaseDate: String,
  val rating: Float,
  val genres: String,
  val screenshots: List<String>,
  val ratings: List<GameDetailsRatingsUIModel>
)

data class GameDetailsRatingsUIModel(
  val title: String,
  val percent: Double,
  @ColorRes val progressTint: Int
)
