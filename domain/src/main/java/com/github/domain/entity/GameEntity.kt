package com.github.domain.entity

/**
 * Authored by Mohamed Fathy on 13 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
data class GameEntity(
  val id: Long,
  val slug: String,
  val name: String,
  val description: String = "",
  val released: String,
  val backgroundImage: String,
  val rating: Double,
  val ratings: List<RatingEntity>,
  val updated: String,
  val screenshots: List<ScreenshotEntity>,
  val genres: List<GenreEntity>
)

data class RatingEntity(
  val id: Long,
  val title: String,
  val count: Long,
  val percent: Double
)

data class ScreenshotEntity(
  val id: Long,
  val image: String
)

data class GenreEntity(
  val id: Long,
  val name: String,
  val slug: String
)
