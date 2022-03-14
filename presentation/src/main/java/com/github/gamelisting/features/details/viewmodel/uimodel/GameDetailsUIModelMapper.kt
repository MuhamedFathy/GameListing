package com.github.gamelisting.features.details.viewmodel.uimodel

import com.github.domain.entity.GameEntity
import com.github.domain.entity.RatingEntity
import com.github.gamelisting.R

/**
 * Authored by Mohamed Fathy on 14 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */

fun GameEntity.toGameDetailsUIModel() = GameDetailsUIModel(
  id = id,
  name = name,
  description = description,
  backgroundImage = backgroundImage,
  releaseDate = released.ifBlank { "TBR" },
  rating = rating.toFloat(),
  genres = genres.joinToString(", ") { it.name },
  screenshots = screenshots.map { it.image },
  ratings = ratings.map { it.toUIModel() }
)

fun RatingEntity.toUIModel() = GameDetailsRatingsUIModel(
  title = when (title) {
    "skip" -> "⛔ ${title.replaceFirstChar { it.uppercase() }} ($count)"
    "meh" -> "\uD83D\uDE10 ${title.replaceFirstChar { it.uppercase() }} ($count)"
    "recommended" -> "\uD83D\uDC4D ${title.replaceFirstChar { it.uppercase() }} ($count)"
    else -> "\uD83C\uDFAF ${title.replaceFirstChar { it.uppercase() }} ($count)"
  },
  percent = percent,
  progressTint = when (title) {
    "skip" -> R.color.rating_skip
    "meh" -> R.color.rating_meh
    "recommended" -> R.color.rating_recommended
    else -> R.color.rating_exceptional
  }
)