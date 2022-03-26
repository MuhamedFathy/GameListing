package com.github.data.source.locale.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@Entity(tableName = "games")
data class GameDbEntity(
  @PrimaryKey val id: Long,
  val slug: String,
  val name: String,
  val playtime: Long,
  val released: String,
  val tba: Boolean,
  val backgroundImage: String,
  val rating: Double,
  val ratingTop: Long,
  val ratings: List<RatingDbEntity>,
  val ratingsCount: Long,
  val added: Long,
  val metaCritic: Long,
  val suggestionsCount: Long,
  val updated: String,
  val reviewsCount: Long,
  val saturatedColor: String,
  val dominantColor: String,
  val screenshots: List<ScreenshotDbEntity>,
  val genres: List<GenreDbEntity>
) : Comparable<GameDbEntity> {

  override fun compareTo(other: GameDbEntity): Int {
    return released.compareTo(other.released)
  }
}

data class RatingDbEntity(
  val id: Long,
  val title: String,
  val count: Long,
  val percent: Double
)

data class ScreenshotDbEntity(
  val id: Long,
  val image: String
)

data class GenreDbEntity(
  val id: Long,
  val name: String,
  val slug: String
)
