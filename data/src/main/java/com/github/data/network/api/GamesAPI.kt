package com.github.data.network.api

import com.github.data.model.GameDetailsResponse
import com.github.data.model.GamesResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
interface GamesAPI {

  @GET("games") fun getGames(
    @Query("page") pageNumber: Int,
    @Query("page_size") pageSize: Int,
    @Query("platforms") platforms: Int,
    @Query("ordering") order: String = "-released"
  ): Single<Response<GamesResponse>>

  @GET("games/{id}") fun getGameDetails(
    @Path("id") gameId: Long
  ): Single<Response<GameDetailsResponse>>
}

enum class GamesPlatforms(val value: Int) {
  PLAY_STATION_4(18), XBOX_ONE(1)
}