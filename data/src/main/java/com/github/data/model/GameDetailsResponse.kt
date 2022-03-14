package com.github.data.model

import com.google.gson.annotations.SerializedName

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
data class GameDetailsResponse(
  val id: Long?,
  val slug: String?,
  val name: String?,
  @SerializedName("name_original") val nameOriginal: String?,
  val description: String?,
  val metacritic: Long?,
  val released: String?,
  val tba: Boolean?,
  val updated: String?,
  @SerializedName("background_image") val backgroundImage: String?,
  @SerializedName("background_image_additional") val backgroundImageAdditional: String?,
  val website: String?,
  val rating: Double?,
  @SerializedName("rating_top") val ratingTop: Long?,
  val ratings: List<Rating>?,
  val added: Long?,
  val playtime: Long?,
  @SerializedName("screenshots_count") val screenshotsCount: Long?,
  @SerializedName("movies_count") val moviesCount: Long?,
  @SerializedName("creators_count") val creatorsCount: Long?,
  @SerializedName("achievements_count") val achievementsCount: Long?,
  @SerializedName("parent_achievements_count") val parentAchievementsCount: Long?,
  @SerializedName("reddit_url") val redditUrl: String?,
  @SerializedName("reddit_name") val redditName: String?,
  @SerializedName("reddit_description") val redditDescription: String?,
  @SerializedName("reddit_logo") val redditLogo: String?,
  @SerializedName("reddit_count") val redditCount: Long?,
  @SerializedName("twitch_count") val twitchCount: Long?,
  @SerializedName("youtube_count") val youtubeCount: Long?,
  @SerializedName("reviews_text_count") val reviewsTextCount: Long?,
  @SerializedName("ratings_count") val ratingsCount: Long?,
  @SerializedName("suggestions_count") val suggestionsCount: Long?,
  @SerializedName("alternative_names") val alternativeNames: List<String>?,
  @SerializedName("metacritic_url") val metacriticUrl: String?,
  @SerializedName("parents_count") val parentsCount: Long?,
  @SerializedName("additions_count") val additionsCount: Long?,
  @SerializedName("game_series_count") val gameSeriesCount: Long?,
  @SerializedName("user_game") val userGame: Any?,
  @SerializedName("reviews_count") val reviewsCount: Long?,
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
    val id: Long?,
    val title: String?,
    val count: Long?,
    val percent: Double?
  )

  data class Platform(
    val platform: Platform?,
    @SerializedName("released_at") val releasedAt: String?,
    val requirements: Requirements?
  ) {
    data class Platform(
      val id: Long?,
      val name: String?,
      val slug: String?,
      val image: Any?,
      @SerializedName("year_end") val yearEnd: Any?,
      @SerializedName("year_start") val yearStart: Any?,
      @SerializedName("games_count") val gamesCount: Long?,
      @SerializedName("image_background") val imageBackground: String?
    )

    data class Requirements(
      val minimum: String?,
      val recommended: String?
    )
  }

  data class Store(
    val id: Long?,
    val url: String?,
    val store: Store?
  ) {
    data class Store(
      val id: Long?,
      val name: String?,
      val slug: String?,
      val domain: String?,
      @SerializedName("games_count") val gamesCount: Long?,
      @SerializedName("image_background") val imageBackground: String?
    )
  }

  data class Developer(
    val id: Long?,
    val name: String?,
    val slug: String?,
    @SerializedName("games_count") val gamesCount: Long?,
    @SerializedName("image_background") val imageBackground: String?
  )

  data class Genre(
    val id: Long?,
    val name: String?,
    val slug: String?,
    @SerializedName("games_count") val gamesCount: Long?,
    @SerializedName("image_background") val imageBackground: String?
  )

  data class Publisher(
    val id: Long?,
    val name: String?,
    val slug: String?,
    @SerializedName("games_count") val gamesCount: Long?,
    @SerializedName("image_background") val imageBackground: String?
  )
}
