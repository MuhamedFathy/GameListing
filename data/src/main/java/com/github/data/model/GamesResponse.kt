package com.github.data.model

import com.google.gson.annotations.SerializedName

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
data class GamesResponse(
  val count: Int?,
  val next: String?,
  val previous: String?,
  val results: List<Result>?,
  @SerializedName("user_platforms") val userPlatforms: Boolean?
)

data class Result(
  val slug: String?,
  val name: String?,
  val playtime: Int?,
  val platforms: List<Platform?>?,
  val released: String?,
  val tba: Boolean?,
  @SerializedName("background_image") val backgroundImage: String?,
  val rating: Double?,
  @SerializedName("rating_top") val ratingTop: Int?,
  val ratings: List<Rating>?,
  @SerializedName("ratings_count") val ratingsCount: Int?,
  @SerializedName("reviews_text_count") val reviewsTextCount: Int?,
  val added: Int?,
  val metaCritic: Int?,
  @SerializedName("suggestions_count") val suggestionsCount: Int?,
  val updated: String?,
  val id: Int?,
  val score: Any?,
  val clip: Any?,
  @SerializedName("user_game") val userGame: Any?,
  @SerializedName("reviews_count") val reviewsCount: Int?,
  @SerializedName("saturated_color") val saturatedColor: String?,
  @SerializedName("dominant_color") val dominantColor: String?,
  @SerializedName("short_screenshots") val shortScreenshots: List<ShortScreenshot>?,
  val genres: List<Genre>?
)

data class Platform(
  val platform: Platform?
) {
  data class Platform(
    val id: Int?,
    val name: String?,
    val slug: String?
  )
}

data class Rating(
  val id: Int?,
  val title: String?,
  val count: Int?,
  val percent: Double?
)

data class ShortScreenshot(
  val id: Int?,
  val image: String?
)

data class Genre(
  val id: Int?,
  val name: String?,
  val slug: String?
)