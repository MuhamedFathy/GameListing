package com.github.data.model.mappers

import com.github.data.model.GameDetailsResponse
import com.github.domain.entity.GameEntity
import com.github.domain.entity.GenreEntity
import com.github.domain.entity.RatingEntity
import com.github.domain.entity.ScreenshotEntity

/**
 * Authored by Mohamed Fathy on 14 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */

fun GameDetailsResponse.toEntity(screenshots: List<ScreenshotEntity>) = GameEntity(
  id = id ?: 0L,
  slug = slug ?: "",
  name = name ?: "",
  description = description ?: "",
  released = released ?: "TBR",
  backgroundImage = backgroundImage ?: "",
  rating = rating ?: 0.0,
  ratings = ratings?.map { it.toEntity() } ?: emptyList(),
  updated = updated ?: "",
  screenshots = screenshots,
  genres = genres?.map { it.toEntity() } ?: emptyList()
)

fun GameDetailsResponse.Rating.toEntity() = RatingEntity(
  id = id ?: 0L,
  title = title ?: "",
  count = count ?: 0L,
  percent = percent ?: 0.0
)

fun GameDetailsResponse.Genre.toEntity() = GenreEntity(
  id = id ?: 0L,
  name = name ?: "",
  slug = slug ?: ""
)