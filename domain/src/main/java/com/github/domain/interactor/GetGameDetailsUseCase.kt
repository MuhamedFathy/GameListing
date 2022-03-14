package com.github.domain.interactor

import com.github.domain.entity.GameEntity
import com.github.domain.repository.GamesRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Authored by Mohamed Fathy on 14 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
class GetGameDetailsUseCase @Inject constructor(
  private val gamesRepository: GamesRepository
) {

  fun build(gameId: Long): Single<GameEntity> = gamesRepository.getGamDetails(gameId)
}