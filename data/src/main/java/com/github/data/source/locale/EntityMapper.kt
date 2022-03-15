package com.github.data.source.locale

import com.github.data.model.Genre
import com.github.data.model.Rating
import com.github.data.model.Result
import com.github.data.model.ShortScreenshot
import com.github.data.source.locale.entity.GameDbEntity
import com.github.data.source.locale.entity.GenreDbEntity
import com.github.data.source.locale.entity.RatingDbEntity
import com.github.data.source.locale.entity.ScreenshotDbEntity
import com.github.domain.entity.GameEntity
import com.github.domain.entity.GenreEntity
import com.github.domain.entity.RatingEntity
import com.github.domain.entity.ScreenshotEntity

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */

fun Result.toDbEntity() = GameDbEntity(
  id = id ?: 0L,
  slug = slug ?: "",
  name = name ?: "",
  playtime = playtime ?: 0L,
  released = released ?: "",
  tba = tba ?: false,
  backgroundImage = backgroundImage ?: "",
  rating = rating ?: 0.0,
  ratingTop = ratingTop ?: 0L,
  ratings = ratings?.map { it.toDbEntity() } ?: emptyList(),
  ratingsCount = ratingsCount ?: 0L,
  added = added ?: 0L,
  metaCritic = metaCritic ?: 0L,
  suggestionsCount = suggestionsCount ?: 0L,
  updated = updated ?: "",
  reviewsCount = reviewsCount ?: 0L,
  saturatedColor = saturatedColor ?: "",
  dominantColor = dominantColor ?: "",
  screenshots = shortScreenshots?.map { it.toDbEntity() } ?: emptyList(),
  genres = genres?.map { it.toDbEntity() } ?: emptyList()
)

fun Rating.toDbEntity() = RatingDbEntity(
  id = id ?: 0L,
  title = title ?: "",
  count = count ?: 0L,
  percent = percent ?: 0.0
)

fun ShortScreenshot.toDbEntity() = ScreenshotDbEntity(
  id = id ?: 0L,
  image = image ?: ""
)

fun Genre.toDbEntity() = GenreDbEntity(
  id = id ?: 0L,
  name = name ?: "",
  slug = slug ?: ""
)

fun GameDbEntity.toEntity() = GameEntity(
  id = id,
  slug = slug,
  name = name,
  released = released,
  backgroundImage = backgroundImage,
  rating = rating,
  ratings = ratings.map { it.toEntity() },
  updated = updated,
  screenshots = screenshots.map { it.toEntity() },
  genres = genres.map { it.toEntity() }
)

fun RatingDbEntity.toEntity() = RatingEntity(
  id = id,
  title = title,
  count = count,
  percent = percent
)

fun ScreenshotDbEntity.toEntity() = ScreenshotEntity(
  id = id,
  image = image
)

fun GenreDbEntity.toEntity() = GenreEntity(
  id = id,
  name = name,
  slug = slug
)
