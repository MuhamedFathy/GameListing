package com.github.data.model

import com.google.gson.annotations.SerializedName

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
data class GameDetailsResponse(
  val id: Int?,
  val slug: String?,
  val name: String?,
  @SerializedName("name_original") val nameOriginal: String?,
  val description: String?,
  val metacritic: Int?,
  val released: String?,
  val tba: Boolean?,
  val updated: String?,
  @SerializedName("background_image") val backgroundImage: String?,
  @SerializedName("background_image_additional") val backgroundImageAdditional: String?,
  val website: String?,
  val rating: Double?,
  @SerializedName("rating_top") val ratingTop: Int?,
  val ratings: List<Rating>?,
  val added: Int?,
  val playtime: Int?,
  @SerializedName("screenshots_count") val screenshotsCount: Int?,
  @SerializedName("movies_count") val moviesCount: Int?,
  @SerializedName("creators_count") val creatorsCount: Int?,
  @SerializedName("achievements_count") val achievementsCount: Int?,
  @SerializedName("parent_achievements_count") val parentAchievementsCount: Int?,
  @SerializedName("reddit_url") val redditUrl: String?,
  @SerializedName("reddit_name") val redditName: String?,
  @SerializedName("reddit_description") val redditDescription: String?,
  @SerializedName("reddit_logo") val redditLogo: String?,
  @SerializedName("reddit_count") val redditCount: Int?,
  @SerializedName("twitch_count") val twitchCount: Int?,
  @SerializedName("youtube_count") val youtubeCount: Int?,
  @SerializedName("reviews_text_count") val reviewsTextCount: Int?,
  @SerializedName("ratings_count") val ratingsCount: Int?,
  @SerializedName("suggestions_count") val suggestionsCount: Int?,
  @SerializedName("alternative_names") val alternativeNames: List<String>?,
  @SerializedName("metacritic_url") val metacriticUrl: String?,
  @SerializedName("parents_count") val parentsCount: Int?,
  @SerializedName("additions_count") val additionsCount: Int?,
  @SerializedName("game_series_count") val gameSeriesCount: Int?,
  @SerializedName("user_game") val userGame: Any?,
  @SerializedName("reviews_count") val reviewsCount: Int?,
  @SerializedName("saturated_color") val saturatedColor: String?,
  @SerializedName("dominant_color") val dominantColor: String?,
  val platforms: List<Platform>?,
  val stores: List<Store>?,
  val developers: List<Developer>?,
  val genres: List<Genre>?,
  val publishers: List<Publisher>?,
  val clip: Any?,
  @SerializedName("description_raw") val descriptionRaw: String?
) {

  data class Rating(
    val id: Int?,
    val title: String?,
    val count: Int?,
    val percent: Double?
  )

  data class Platform(
    val platform: Platform?,
    @SerializedName("released_at") val releasedAt: String?,
    val requirements: Requirements?
  ) {
    data class Platform(
      val id: Int?,
      val name: String?,
      val slug: String?,
      val image: Any?,
      @SerializedName("year_end") val yearEnd: Any?,
      @SerializedName("year_start") val yearStart: Any?,
      @SerializedName("games_count") val gamesCount: Int?,
      @SerializedName("image_background") val imageBackground: String?
    )

    data class Requirements(
      val minimum: String?,
      val recommended: String?
    )
  }

  data class Store(
    val id: Int?,
    val url: String?,
    val store: Store?
  ) {
    data class Store(
      val id: Int?,
      val name: String?,
      val slug: String?,
      val domain: String?,
      @SerializedName("games_count") val gamesCount: Int?,
      @SerializedName("image_background") val imageBackground: String?
    )
  }

  data class Developer(
    val id: Int?,
    val name: String?,
    val slug: String?,
    @SerializedName("games_count") val gamesCount: Int?,
    @SerializedName("image_background") val imageBackground: String?
  )

  data class Genre(
    val id: Int?,
    val name: String?,
    val slug: String?,
    @SerializedName("games_count") val gamesCount: Int?,
    @SerializedName("image_background") val imageBackground: String?
  )

  data class Publisher(
    val id: Int?,
    val name: String?,
    val slug: String?,
    @SerializedName("games_count") val gamesCount: Int?,
    @SerializedName("image_background") val imageBackground: String?
  )
}
