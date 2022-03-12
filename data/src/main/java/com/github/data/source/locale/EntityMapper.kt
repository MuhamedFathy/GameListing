package com.github.data.source.locale

import com.github.data.model.Genre
import com.github.data.model.Rating
import com.github.data.model.Result
import com.github.data.model.ShortScreenshot
import com.github.data.source.locale.entity.GameDbEntity
import com.github.data.source.locale.entity.GenreDbEntity
import com.github.data.source.locale.entity.RatingDbEntity
import com.github.data.source.locale.entity.ScreenshotDbEntity

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */

fun Result.toDbEntity() = GameDbEntity(
  id = id ?: 0L,
  slug = slug,
  name = name,
  playtime = playtime,
  released = released,
  tba = tba,
  backgroundImage = backgroundImage,
  rating = rating,
  ratingTop = ratingTop,
  ratings = ratings?.map { it.toDbEntity() },
  ratingsCount = ratingsCount,
  added = added,
  metaCritic = metaCritic,
  suggestionsCount = suggestionsCount,
  updated = updated,
  reviewsCount = reviewsCount,
  saturatedColor = saturatedColor,
  dominantColor = dominantColor,
  screenshots = shortScreenshots?.map { it.toDbEntity() },
  genres = genres?.map { it.toDbEntity() }
)

fun Rating.toDbEntity() = RatingDbEntity(
  id = id,
  title = title,
  count = count,
  percent = percent
)

fun ShortScreenshot.toDbEntity() = ScreenshotDbEntity(
  id = id,
  image = image
)

fun Genre.toDbEntity() = GenreDbEntity(
  id = id,
  name = name,
  slug = slug
)