package com.github.data.model

import com.google.gson.annotations.SerializedName

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
data class GamesResponse(
  val count: Long?,
  val next: String?,
  val previous: String?,
  val results: List<Result>?,
  @SerializedName("user_platforms") val userPlatforms: Boolean?
)

data class Result(
  val id: Long?,
  val slug: String?,
  val name: String?,
  val playtime: Long?,
  val released: String?,
  val tba: Boolean?,
  @SerializedName("background_image") val backgroundImage: String?,
  val rating: Double?,
  @SerializedName("rating_top") val ratingTop: Long?,
  val ratings: List<Rating>?,
  @SerializedName("ratings_count") val ratingsCount: Long?,
  @SerializedName("reviews_text_count") val reviewsTextCount: Long?,
  val added: Long?,
  val metaCritic: Long?,
  @SerializedName("suggestions_count") val suggestionsCount: Long?,
  val updated: String?,
  @SerializedName("reviews_count") val reviewsCount: Long?,
  @SerializedName("saturated_color") val saturatedColor: String?,
  @SerializedName("dominant_color") val dominantColor: String?,
  @SerializedName("short_screenshots") val shortScreenshots: List<ShortScreenshot>?,
  val genres: List<Genre>?
)

data class Rating(
  val id: Long?,
  val title: String?,
  val count: Long?,
  val percent: Double?
)

data class ShortScreenshot(
  val id: Long?,
  val image: String?
)

data class Genre(
  val id: Long?,
  val name: String?,
  val slug: String?
)