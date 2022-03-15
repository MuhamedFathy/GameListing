package com.github.domain.interactor

import androidx.paging.PagingData
import com.github.domain.entity.GameEntity
import com.github.domain.repository.GamesRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Authored by Mohamed Fathy on 13 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
class GetGamesUseCase @Inject constructor(
  private val gamesRepository: GamesRepository
) {

  fun build(): Flowable<PagingData<GameEntity>> {
    return gamesRepository.getGames()
  }
}