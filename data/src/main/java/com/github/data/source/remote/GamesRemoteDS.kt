package com.github.data.source.remote

import com.github.data.extensions.parseResponse
import com.github.data.model.Result
import com.github.data.network.api.GamesAPI
import io.reactivex.Single
import javax.inject.Inject

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
class GamesRemoteDS @Inject constructor(
  private val gamesAPI: GamesAPI
) {

  fun getGames(pageNumber: Int, pageSize: Int = 20, platform: GamesPlatforms): Single<List<Result>> {
    return gamesAPI.getGames(pageNumber, pageSize, platform.value)
      .parseResponse()
      .map { it.results ?: emptyList() }
  }
}

enum class GamesPlatforms(val value: Int) {
  PLAY_STATION_4(18), XBOX_ONE(1)
}